#!/bin/bash
set -e

export MYSQL_PWD="$MYSQL_ROOT_PASSWORD"

mysql --user=root < /uniclub_schema.sql
mysql --user=root < /uniclub_mock_data.sql

mysql --user=root -e "
CREATE USER IF NOT EXISTS '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';

ALTER USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';

GRANT ALL PRIVILEGES ON uniclub.* TO '$MYSQL_USER'@'%';

FLUSH PRIVILEGES;
"

echo "Init complete"
