#!/bin/bash

# Set permission to handling off the container
while [ true ]; do for i in $(find /salve-minha-carteira-back -user root); do chown $us_id:$gr_id $i; done; sleep 1; done &

sleep infinity
