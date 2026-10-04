# Build stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom first for better caching
COPY mvnw pom.xml ./
COPY .mvn/ .mvn/

# Download dependencies (cached)
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

# Copy source code
COPY src/ src/
COPY lombok.config ./

# Build the application
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B clean package -DskipTests

# Extract the application dependencies
RUN jar xf target/be.jar

# Analyze the dependencies contained in the fat jar
RUN jdeps --ignore-missing-deps -q \
  --recursive \
  --multi-release 21 \
  --print-module-deps \
  --class-path 'BOOT-INF/lib/*' \
  target/be.jar > deps.info

# Create the custom JRE
RUN jlink \
  --verbose \
  --add-modules $(cat deps.info),jdk.crypto.ec \
  --compress zip-9 \
  --no-header-files \
  --no-man-pages \
  --output /custom_jre

# Healthcheck stage
FROM busybox:1.36.0-musl AS healthcheck

# Runtime stage
FROM gcr.io/distroless/base-debian12
ARG APP_VERSION

ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"
COPY --from=build /custom_jre $JAVA_HOME

COPY --from=healthcheck /bin/wget /usr/bin/wget

WORKDIR /app

#COPY lib/applicationinsights.json ./

COPY --from=build /app/target/be.jar /app.jar
COPY --from=build /app/lombok.config ./

LABEL org.opencontainers.image.source="https://github.com/diligent-man/Java-BC12-capstone-BE" \
  org.opencontainers.image.version="${APP_VERSION}" \
  org.opencontainers.image.licenses="MIT"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD ["/usr/bin/wget", "--quiet", "--output-document=/dev/null", "http://0.0.0.0:8080/actuator/health"]

CMD ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
