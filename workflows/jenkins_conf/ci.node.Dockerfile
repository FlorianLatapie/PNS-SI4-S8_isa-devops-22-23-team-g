FROM node:18.13.0

RUN apt-get update && yes | apt-get install curl
RUN curl -fL https://install-cli.jfrog.io | sh
RUN chown 1000:1000 /usr/local/bin/jf
RUN mkdir /.jfrog
RUN chmod 775 /.jfrog
RUN chown 1000:1000 /.jfrog

RUN apt-get install zip -y

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["node"]