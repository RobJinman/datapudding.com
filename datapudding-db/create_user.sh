#!/bin/bash

if [ "$EUID" -ne 0 ]
then
  printf "Please run as root\n" >&2
  exit 1
fi

if [ "$#" -lt 2 ]
then
  printf "Usage: $0 user pw\n" >&2
  exit 1
fi

USER="$1"
PW="$2"

printf "Creating user '$USER' with password '$PW' ...\n"
su - postgres -c "psql -c \"CREATE USER $USER WITH PASSWORD '$PW';\""
