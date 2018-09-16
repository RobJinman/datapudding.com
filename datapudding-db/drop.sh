#!/bin/bash

if [ "$EUID" -ne 0 ]
then
  printf "Please run as root\n" >&2
  exit 1
fi

su - postgres -c "dropdb dpdb"
su - postgres -c "dropdb dptestdb"
su - postgres -c "dropuser dpapp"
su - postgres -c "dropuser dpmaster"
