# Salve Minha Carteira Back

## Ferramentas necessárias
* Docker -> https://docs.docker.com/install/

Este projeto foi gerado com o angular-cli [Angular CLI](https://github.com/angular/angular-cli) na versão 7.3.4.

## Criar o ambiente via docker

Executar os comandos abaixo na raiz do projeto para iniciar o container em background e acessá-lo
```
./extras/docker/run-docker.sh
```

## Executando o projeto
Dentro da instância docker no qual foi gerado e acesso no comando anterior, execute um dos comandos abaixo para executar a aplicação (quando os containers são executados, o container do banco de dados demora mais para ser inicializado, causando problemas quando for executar o projeto, mas basta soment aguardar a inicialiação completa do container do banco de dados)
```
run
debug #aguarda uma conexão remota para debug na porta 5005
```

## Executando o testes
```
run-tests
```

## Gerador de dados das empresas da B3

### Ferramentas necessárias

* Python versão 3
  * Dependência selenium
* Pip (gerenciador de pacotes do python) versão 3
* Navegador Firefox

### Gerar o arquivo de dados das empresas da b3

### Detalhes do script

O script gen-dados-empresas-b3.py lê a página das empresas da b3 e gera um arquivo chamado 'gen-dados-empresas-b3-output.json' que possui os dados das empresas da B3,
caso o script não complete a execução poderá ser executado novamente e continuar de onde parou
* --local True : parâmetro adicional é usado para processar o arquivo de dados existentes sem consultar o site da b3, usado para corrigir ou formatar o json de saída

### Configuração necessário para executar o script

Ter o Python e o PIP instalados. Executar os comandos abaixo

```
pip3 install selenium
```

Baixar o geckodriver em uma pasta de preferência e adicionar o caminho da pasta onde estará o executável na variável de ambiente PATH.

* Link da página raiz para download: https://github.com/mozilla/geckodriver/releases
* Exemplo de download e configuração

```
wget https://github.com/mozilla/geckodriver/releases/download/v0.26.0/geckodriver-v0.26.0-linux64.tar.gz
mkdir geckodriver-v0.26.0
tar -xzvf geckodriver-v0.26.0-linux64.tar.gz -C geckodriver-v0.26.0
cd geckodriver-v0.26.0
export PATH="${PATH}:$(pwd)"
cd .. && rm geckodriver-v0.26.0-linux64.tar.gz
```

### Gerar o arquivo de dados das empresas da b3

Executar os comandos abaixo na raiz do projeto

```
cd extras/scripts
python3 gen-dados-empresas-b3.py
rm geckodriver.log
cd ../..
```

### Gerando/Atualizando o script para popular os dados das tabelas Setor, Empresa e Acao

Executar os comandos abaixo na raiz do projeto

```
cd extras/scripts
python3 gen-bd-inserts-partir-dados-b3.py
cd ../..
```

## Banco de dados

Abaixo comandos para acessar o container que está executando o banco de dados. Deve-se executar na raiz do projeto
```
docker-compose exec salve-minha-carteira-back-bd bash
```
Executando queries
```
mysql -u root -p123 -D salve-minha-carteira-db
mysql -u root -p123 -D salve-minha-carteira-db < create-tables.sql # informando a ser executado
```
