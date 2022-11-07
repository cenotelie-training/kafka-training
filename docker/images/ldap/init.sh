#!/bin/bash

SCRIPT="$(readlink -f "$0")"
DIR="$(dirname "$SCRIPT")"

# wait for some time
sleep 8

ldapadd -x -f "$DIR/users.ldif" -w cgos -D cn=admin,dc=cenotelie,dc=fr
ldapadd -x -f "$DIR/groups.ldif" -w cgos -D cn=admin,dc=cenotelie,dc=fr
