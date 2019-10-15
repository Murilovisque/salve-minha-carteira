FROM node:10.16.3-stretch
RUN apt update
RUN apt install nginx -y

RUN echo 'export PATH=$PATH:/salve-minha-carteira-front/node_modules/.bin/' >> /root/.bashrc

EXPOSE 4200
EXPOSE 80
WORKDIR /salve-minha-carteira-front
ENTRYPOINT /bin/bash -c "chmod +x /salve-minha-carteira-front/extras/docker/entrypoint.sh && /salve-minha-carteira-front/extras/docker/entrypoint.sh"
