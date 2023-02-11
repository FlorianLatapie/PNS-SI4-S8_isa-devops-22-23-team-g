FROM jenkins/ssh-agent:jdk11
USER root
RUN apt-get update && yes | apt-get install curl
RUN curl -sSL https://get.docker.com/ | sh
COPY entrypoint.sh /usr/local/bin/entrypoint
RUN chmod +x /usr/local/bin/entrypoint

USER jenkins
# ENTRYPOINT ["setup-sshd"]
ENTRYPOINT ["entrypoint"]