use salve-minha-carteira-db;

CREATE TABLE IF NOT EXISTS empresa(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS usuario(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(70) NOT NULL UNIQUE,
    senha VARCHAR(70) NOT NULL,
    PRIMARY KEY(id)
);