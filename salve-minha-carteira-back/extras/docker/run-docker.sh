#!/bin/bash

docker stop --time=1 salve-minha-carteira-back
docker build -t salve-minha-carteira-back extras/docker/
docker run -d -v $(pwd):/salve-minha-carteira-back -e us_id=`id -u` -e gr_id=`id -g` --rm --net=host --name salve-minha-carteira-back salve-minha-carteira-back
docker exec -it salve-minha-carteira-back bash
