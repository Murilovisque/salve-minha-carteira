use salve-minha-carteira-db;

CREATE TABLE IF NOT EXISTS empresa(
    id BIGINT NOT NULL AUTO_INCREMENT,
    razao_social VARCHAR(70) NOT NULL UNIQUE,
    nome_pregao VARCHAR(70) NOT NULL UNIQUE,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS setor(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(70) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS empresa_setor(
    id_empresa BIGINT NOT NULL,
    id_setor BIGINT NOT NULL,
    PRIMARY KEY(id_empresa, id_setor),
    FOREIGN KEY(id_empresa) REFERENCES empresa(id),
    FOREIGN KEY(id_setor) REFERENCES setor(id)
);

CREATE TABLE IF NOT EXISTS acao(
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_empresa BIGINT NOT NULL,
    cod_negociacao VARCHAR(15) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_empresa) REFERENCES empresa(id)
);

CREATE TABLE IF NOT EXISTS usuario(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(70) NOT NULL UNIQUE,
    senha VARCHAR(70) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS boleta(
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_acao BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    data DATE NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_acao) REFERENCES acao(id),
    FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

ALTER TABLE boleta ADD UNIQUE unique_boleta_acao_tipo_data_valor(id_acao, tipo, data, valor);