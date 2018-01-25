FROM openjdk:8u151-jdk
MAINTAINER Hendrik Ebbers, guigarage.com

RUN apt-get update
RUN apt-get install -y ansible
RUN echo '[local]\nlocalhost' > /etc/ansible/hosts
ENTRYPOINT ["ansible-playbook"]
