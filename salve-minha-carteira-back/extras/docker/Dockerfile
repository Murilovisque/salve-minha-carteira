FROM debian:stretch

RUN apt update
RUN apt install wget -y

RUN wget https://download.java.net/openjdk/jdk12/ri/openjdk-12+32_linux-x64_bin.tar.gz
RUN tar xvf openjdk-12+32_linux-x64_bin.tar.gz
RUN mkdir -p /usr/lib/jvm
RUN mv jdk-12 /usr/lib/jvm
RUN for i in $(ls /usr/lib/jvm/jdk-12/bin/); do update-alternatives --install "/usr/bin/${i}" "${i}" "/usr/lib/jvm/jdk-12/bin/${i}" 1; done
RUN for i in $(ls /usr/lib/jvm/jdk-12/bin/); do chmod a+x /usr/bin/${i}; done
RUN rm -rf jdk-12 openjdk-12+32_linux-x64_bin.tar.gz

RUN apt install telnet -y
RUN apt install procps -y

EXPOSE 8080
EXPOSE 5005
WORKDIR /salve-minha-carteira-back
ENTRYPOINT /bin/bash -c "chmod +x /salve-minha-carteira-back/extras/docker/entrypoint.sh && /salve-minha-carteira-back/extras/docker/entrypoint.sh"