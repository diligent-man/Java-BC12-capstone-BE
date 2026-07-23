ARG MYSQL_VERSION=${MYSQL_VERSION:-9.5.0}
ARG DEBIAN_FRONTEND=noninteractive

FROM mysql:${MYSQL_VERSION}

COPY init-user-db.sh /docker-entrypoint-initdb.d
COPY uniclub.sql /

EXPOSE 3306
