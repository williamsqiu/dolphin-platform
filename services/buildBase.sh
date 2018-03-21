#!/bin/bash
if [ -f "keycloak/ansible/keycloak-3.4.0.Final.tar" ]
then
	docker build -t dolphin-services/base base/
else
	echo "Please download artifacts first"
fi

