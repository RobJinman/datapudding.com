#!/bin/bash

if [ "$EUID" -ne 0 ]
then
  printf "Please run as root\n" >&2
  exit 1
fi

if [ "$#" -lt 2 ]
then
  printf "Usage: $0 dbname user\n" >&2
  exit 1
fi

DB_NAME="$1"
USER="$2"

printf "Creating database '$DB_NAME' ...\n"
su - postgres -c "createdb --owner=\"$USER\" \"$DB_NAME\""

printf "Creating extensions ...\n"
su - postgres -c "psql --dbname=\"$DB_NAME\" -c \"CREATE EXTENSION IF NOT EXISTS \\\"pgcrypto\\\"; CREATE EXTENSION IF NOT EXISTS \\\"uuid-ossp\\\";\""
