-- Script de criação do banco do trabalho (Gestão de Mercado)
CREATE DATABASE IF NOT EXISTS gestao_mercado;
USE gestao_mercado;

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14),
    telefone VARCHAR(20),
    email VARCHAR(100),
    data_nascimento DATE,
    endereco VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS caixas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14),
    telefone VARCHAR(20),
    email VARCHAR(100),
    data_nascimento DATE,
    salario DECIMAL(10,2),
    turno VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS fornecedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    razao_social VARCHAR(100),
    cnpj VARCHAR(18),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    marca VARCHAR(100),
    valor_unitario DECIMAL(10,2),
    valor_atacado DECIMAL(10,2),
    qtd_atacado INT,
    validade DATE
);

CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(100),
    forma_pagamento VARCHAR(100),
    data_pedido DATE,
    valor_total DECIMAL(10,2),
    id_cliente BIGINT NOT NULL,
    id_caixa BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_caixa) REFERENCES caixas(id)
);

CREATE TABLE IF NOT EXISTS itens_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    valor_unitario DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

CREATE TABLE IF NOT EXISTS compras (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(100),
    data_compra DATE,
    previsao_entrega DATE,
    volume_total INT,
    valor_frete DECIMAL(10,2),
    valor_total DECIMAL(10,2),
    id_fornecedor BIGINT NOT NULL,
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id)
);

CREATE TABLE IF NOT EXISTS itens_compra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_compra BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade INT NOT NULL,
    valor_unitario DECIMAL(10,2),
    FOREIGN KEY (id_compra) REFERENCES compras(id) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

