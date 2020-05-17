#!/bin/bash

# Set permission to handling off the container
echo $'while [ true ]; do sleep 2; find /salve-minha-carteira-front -user root -exec chown -R ${us_id}:${gr_id} \'{}\' +; done &' >> /root/.bashrc
echo $'while [ true ]; do if [[ $(ps aux | grep npm | grep -v grep) ]]; then echo "npm install executando ainda..."; else echo "npm install executado. Clique ENTER para continuar"; break; fi; sleep 2; done &' >> /root/.bashrc

npm install

# Nginx config
rm -rf /etc/nginx/nginx.conf
ln -s /salve-minha-carteira-front/extras/nginx/nginx.conf /etc/nginx/nginx.conf
ln -s /salve-minha-carteira-front/extras/nginx/salve-minha-carteira /etc/nginx/sites-available/salve-minha-carteira
rm -rf /etc/nginx/sites-enabled/*
ln -s /etc/nginx/sites-available/salve-minha-carteira /etc/nginx/sites-enabled/salve-minha-carteira
service nginx start

sleep infinity
