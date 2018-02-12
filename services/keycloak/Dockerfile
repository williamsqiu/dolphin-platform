FROM dolphin-services/base
MAINTAINER Hendrik Ebbers, guigarage.com

ADD ansible .
RUN ansible-playbook site.yml -c local

EXPOSE 8080 9990

ENTRYPOINT /opt/keycloak-3.4.0.Final/bin/standalone.sh -b 0.0.0.0
