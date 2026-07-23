#!/bin/bash
set -e

mysql --user=root --password="$MYSQL_ROOT_PASSWORD" < uniclub.sql

mysql --user=root --password="$MYSQL_ROOT_PASSWORD" -e "
CREATE USER IF NOT EXISTS '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';

ALTER USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';

GRANT ALL PRIVILEGES ON uniclub.* TO '$MYSQL_USER'@'%';

FLUSH PRIVILEGES;
"

echo "Init complete"
