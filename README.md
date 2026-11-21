# TrabalhoPOO

Trabalho da disciplina de Programação Orientada a Objetos, em Java com Gradle.

## Nome do Projeto

Sistema de Gestão de Mercadinho

## Nome Integrantes

- Kauan Leonardo Felipe Rossi
- Pedro Henrique da Silva Santos
- Hugo Sales Silva

## Tema

Gestão de Mercado

## Descrição do Problema Resolvido

Pequenos mercados costumam controlar vendas, estoque e compras de
fornecedores de forma manual, em cadernos ou planilhas soltas. Isso gera
erros de contagem, perda de produtos por validade vencida, falta de
histórico de vendas e dificuldade para saber quanto foi gasto e
arrecadado.

Este sistema centraliza essas informações em uma aplicação com operações
CRUD (criar, ler, atualizar e remover), permitindo:

- Cadastrar e consultar clientes, atendentes de caixa e fornecedores.
- Controlar o estoque de produtos, incluindo preço, marca e validade.
- Registrar os pedidos de venda e os produtos de cada pedido.
- Registrar as compras feitas aos fornecedores e os produtos de cada compra.
- Manter o histórico de valores, formas de pagamento e datas das operações.

## Lista de Entidades Implementadas

### Pessoa

- nome
- telefone
- email
- dataNascimento
- endereco

### PessoaFisica

- cpf

### PessoaJuridica

- cnpj

### Cliente

- id

### Caixa

- id
- salario
- turno

### Produto

- id
- nome
- valorUnitario
- valorAtacado
- qtdAtacado
- marca
- validade

### Fornecedor

- id
- nome
- razaoSocial
- telefone
- email
- cnpj
- endereco

### Pedido

- id
- cliente
- caixa
- status
- formaPagamento
- dataPedido
- valorTotal

### Compra

- id
- fornecedor
- produto
- status
- dataCompra
- previsaoEntrega
- volumeTotal
- valorFrete
- valorTotal



Instruções para execução = 
Divisão de responsabilidades por integrante =
Link para o vídeo =


## Requisitos

- **JDK 17 ou mais novo** instalado (é o mínimo para o Gradle rodar).
- O projeto compila com **Java 25**. Se você não tiver o JDK 25, o Gradle baixa sozinho na primeira compilação (pode demorar um pouco).
- Não precisa instalar o Gradle: o projeto usa o Gradle Wrapper (`gradlew`).

## Como baixar

```bash
git clone https://github.com/Sadzin1603/TrabalhoPOO.git
cd TrabalhoPOO
```

## Estrutura

```
app/
├── build.gradle.kts              # configuração do módulo (dependências, versão do Java)
└── src/
    ├── main/java/org/example/    # código do programa
    └── test/java/org/example/    # testes (JUnit 4)
gradle/libs.versions.toml         # versões das bibliotecas
settings.gradle.kts               # configuração do projeto
```

A classe principal é `org.example.App`.

## Abrindo na IDE

- **IntelliJ IDEA:** *File → Open* e escolha a pasta do projeto. Ele reconhece o Gradle automaticamente.
- **VS Code:** instale o *Extension Pack for Java* e abra a pasta.
