#!/bin/bash

# Set permission to handling off the container
echo $'while [ true ]; do find /salve-minha-carteira-back -user root -exec chown -R ${us_id}:${gr_id} \'{}\' +; sleep 1; done &' >> /root/.bashrc
echo "alias debug='./gradlew bootRun --debug-jvm'" >> /root/.bashrc
echo "alias run='./gradlew bootRun'" >> /root/.bashrc
echo "alias run-tests='./gradlew test'" >> /root/.bashrc

sleep infinity
