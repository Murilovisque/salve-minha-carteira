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
ARQUIVOACOES="COTAHIST_A2019.TXT" #colocar o nome do arquivo baixado
sed '1d;$d' ${ARQUIVOACOES} -i ${ARQUIVOACOES}
echo "use salve-minha-carteira-db;" > populate-empresa.sql
awk '{ nomeempr=substr($0, 28, 12); codbgi=substr($0, 11, 2); gsub(/[ \t]+$/, "", nomeempr); queryins="INSERT INTO empresa (nome) VALUES (\x27"nomeempr"\x27);"; if (codbgi == "96" || codbgi == "02") print queryins }' ${ARQUIVOACOES} | sort -u >> populate-empresa.sql
rm ${ARQUIVOACOES}
```
Atualizar o arquivo gerado substituindo o que está localizado na pasta extras/database/

## Criando/atualizando script para popular a tabela de acoes na bolsa
- Acessar o site da b3 e baixar o arquivo com o histórico anual:
    - http://www.b3.com.br/en_us/market-data-and-indices/data-services/market-data/historical-data/equities/historical-quotes/
- Executar os comandos abaixo
```
ARQUIVOACOES="COTAHIST_A2019.TXT" #colocar o nome do arquivo baixado
sed '1d;$d' ${ARQUIVOACOES} -i ${ARQUIVOACOES}
echo "use salve-minha-carteira-db;" > populate-acoes.sql
awk '{ nomeempr=substr($0, 28, 12); codbdi=substr($0, 11, 2); codneg=substr($0, 13, 12); gsub(/[ \t]+$/, "", nomeempr); gsub(/[ \t]+$/, "", codneg); queryins="insert into acao (cod_negociacao, id_empresa) VALUES (\x27"codneg"\x27, (select id from empresa where nome = \x27"nomeempr"\x27));"; if (codbdi == "96" || codbdi == "02") print queryins }' ${ARQUIVOACOES} | sort -u > populate-acoes.sql
rm ${ARQUIVOACOES}
```
Atualizar o arquivo gerado substituindo o que está localizado na pasta extras/database/

## Banco de dados
Abaixo comandos para acessar o container que está executando o banco de dados
```
docker-compose exec salve-minha-carteira-back-bd bash
```
Executando queries
```
mysql -u root -p123 -D salve-minha-carteira-db
mysql -u root -p123 -D salve-minha-carteira-db < create-tables.sql # informando a ser executado
```