ARG MYSQL_VERSION=${MYSQL_VERSION:-9.5.0}
ARG DEBIAN_FRONTEND=noninteractive

FROM mysql:${MYSQL_VERSION}

COPY init-user-db.sh /docker-entrypoint-initdb.d
COPY uniclub_schema.sql /
COPY uniclub_mock_data.sql /

EXPOSE 3306
