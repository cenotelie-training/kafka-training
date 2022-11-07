#! /bin/bash

if [[ -d ssl ]]
then
    rm -fr ssl
    sudo keytool -delete -alias ${1}cert -keystore ${JAVA_HOME}/lib/security/cacerts
fi

mkdir ssl
mkdir ssl/private
mkdir ssl/certs

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ssl/private/${1}-selfsigned.key -out ssl/certs/${1}-selfsigned.crt

openssl dhparam -out ssl/certs/dhparam.pem 2048

# mot de passe: changeit
sudo keytool -import -noprompt -trustcacerts -alias ${1}cert -file ssl/certs/${1}-selfsigned.crt -keystore ${JAVA_HOME}/lib/security/cacerts

