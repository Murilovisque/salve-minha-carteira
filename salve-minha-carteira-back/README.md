# Salve Minha Carteira Back

## Ferramentas necessárias
* Docker -> https://docs.docker.com/install/

Este projeto foi gerado com o angular-cli [Angular CLI](https://github.com/angular/angular-cli) na versão 7.3.4.

## Criar o ambiente via docker

Executar os comandos abaixo para iniciar o container em background e acessá-lo
```
./extras/docker/run-docker.sh
```

## Criando/atualizando script para popular a tabela de empresas na bolsa
- Acessar o site da b3 e baixar o arquivo com o histórico anual:
    - http://www.b3.com.br/en_us/market-data-and-indices/data-services/market-data/historical-data/equities/historical-quotes/
- Executar os comandos abaixo
```
cat COTAHIST_A2019.TXT | sed '1d;$d' > COTAHIST_A2019-limpo.TXT
while read line; do COD_NEG_PAPEL="${line:13:9}"; NOM_EMPRE_RES="${line:28:9}"; echo "${COD_NEG_PAPEL} - ${NOM_EMPRE_RES}"; done < COTAHIST_A2019-limpo.TXT
```


## Banco de dados
Abaixo comandos para acessar o container que está executando o banco de dados
```
docker-compose exec salve-minha-carteira-back-bd bash
```
Executando queries
```
mysql -u salve-minha-carteira-app -p123 -D salve-minha-carteira-db
mysql -u salve-minha-carteira-app -p123 -D salve-minha-carteira-db < create-tables.sql # informando a ser executado
```
