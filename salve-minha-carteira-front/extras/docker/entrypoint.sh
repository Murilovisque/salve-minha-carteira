#!/bin/bash

npm install
# Set permission to handling off the container
echo $'while [ true ]; do find /salve-minha-carteira-front -user root -exec chown -R ${us_id}:${gr_id} \'{}\' +; sleep 1; done &' >> /root/.bashrc

# Nginx config
rm -rf /etc/nginx/nginx.conf
ln -s /salve-minha-carteira-front/extras/nginx/nginx.conf /etc/nginx/nginx.conf
ln -s /salve-minha-carteira-front/extras/nginx/salve-minha-carteira /etc/nginx/sites-available/salve-minha-carteira
rm -rf /etc/nginx/sites-enabled/*
ln -s /etc/nginx/sites-available/salve-minha-carteira /etc/nginx/sites-enabled/salve-minha-carteira
service nginx start

sleep infinity
