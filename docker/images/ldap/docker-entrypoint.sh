#!/bin/bash

SCRIPT="$(readlink -f "$0")"
DIR="$(dirname "$SCRIPT")"

# initialize the server with data after some time
nohup sh "$DIR/init.sh" &

# start LDAP server
exec /container/tool/run
