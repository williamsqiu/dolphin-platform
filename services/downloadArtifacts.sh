#!/bin/bash
mkdir -p keycloak/ansible
cd keycloak/ansible
curl https://www.dropbox.com/s/qtp7b0622j6a5i1/keycloak-3.4.0.Final.tar -L --output keycloak-3.4.0.Final.tar
cd $OLDPWD
