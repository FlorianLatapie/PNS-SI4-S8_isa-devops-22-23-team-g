FROM maven:3.8.7-eclipse-temurin-17
RUN apt-get update && yes | apt-get install curl
RUN curl -fL https://install-cli.jfrog.io | sh
RUN chown 1000:1000 /usr/local/bin/jf
RUN mkdir /.jfrog
RUN chmod 775 /.jfrog
RUN chown 1000:1000 /.jfrog

USER root
RUN apt-get update && yes | apt-get install curl
RUN curl -sSL https://get.docker.com/ | sh
COPY ci.entrypoint.sh /usr/local/bin/entrypoint
RUN chmod +x /usr/local/bin/entrypoint
RUN mkdir /.docker
RUN chmod 775 /.docker
RUN chown 1000:1000 /.docker

RUN apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
RUN chmod +x /usr/local/bin/docker-compose

RUN apt-get install jq -y
RUN apt-get install socat -y

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]