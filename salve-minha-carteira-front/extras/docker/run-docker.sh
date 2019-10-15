#!/bin/bash

docker stop --time=1 salve-minha-carteira-front
docker build -t salve-minha-carteira-front extras/docker/
docker run -d -v $(pwd):/salve-minha-carteira-front -e us_id=`id -u` -e gr_id=`id -g` --rm --net=host --name salve-minha-carteira-front salve-minha-carteira-front
docker exec -it salve-minha-carteira-front bash
