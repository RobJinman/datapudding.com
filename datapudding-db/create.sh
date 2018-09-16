#!/bin/bash

output_file=./dbsetup.log

if [ "$EUID" -ne 0 ]
then
  printf "Please run as root\n" >&2
  exit 1
fi

source ./database.sh

case $# in
  0)
    pw1="$(gen_pw)"
    pw2="$(gen_pw)"

    ;;
  2)
    pw1="$1"
    pw2="$2"

    ;;

  *)
    printf "Usage: $0 pw1 pw2\n" >&2
    exit 1

  ;;
esac

date >> $output_file

create_user dpmaster "$pw1" >> $output_file
create_user dpapp "$pw2" >> $output_file

setup_db() {
  db_name="$1"

  create_db $db_name dpmaster >> $output_file

  run_sql_file $db_name dpmaster "$pw1" sql/tables.sql >> $output_file
  run_sql_file $db_name dpmaster "$pw1" sql/functions.sql >> $output_file
  run_sql_file $db_name dpmaster "$pw1" sql/permissions.sql >> $output_file
  run_sql_file $db_name dpmaster "$pw1" sql/populate.sql >> $output_file
}

setup_db dpdb
setup_db dptestdb
