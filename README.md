# Sistema de Gestão de Mercadinho

Trabalho da disciplina de Programação Orientada a Objetos (FATEC), feito em Java com JavaFX, JDBC e MariaDB.

## Nome do Projeto

Sistema de Gestão de Mercadinho

## Integrantes

- Kauan Leonardo Felipe Rossi
- Pedro Henrique da Silva Santos
- Hugo Sales Silva

## Tema

Gestão de Mercado

## Descrição do Problema Resolvido

Pequenos mercados costumam controlar vendas, estoque e compras de fornecedores em cadernos ou planilhas soltas. Isso causa erros de contagem, perda de produtos com validade vencida e falta de histórico de vendas e de gastos.

Este sistema reúne essas informações em uma única aplicação desktop, com CRUD completo (criar, ler, atualizar e excluir) de cada cadastro:

- Cadastro de clientes, atendentes de caixa e fornecedores.
- Controle dos produtos, com preço de varejo e de atacado, marca e validade.
- Registro dos pedidos de venda, com os produtos e as quantidades de cada pedido.
- Registro das compras feitas aos fornecedores, com os produtos de cada compra.
- Histórico de valores, formas de pagamento e datas.

**Objetivo de Desenvolvimento Sustentável relacionado:** ODS 12 – Consumo e Produção Responsáveis. O controle de estoque e de validade ajuda o mercado a comprar na medida certa e a reduzir o desperdício de alimentos.

## Lista de Entidades Implementadas

Ao todo são 6 CRUDs (2 por integrante) e 2 entidades de apoio para os itens.
Os campos de `Pessoa` (nome, telefone, email, endereço) e de `PessoaFisica` (cpf, dataNascimento) são herdados.

| Entidade | Campos |
|---|---|
| Cliente (herda PessoaFisica) | id, nome, cpf, telefone, email, dataNascimento, endereco |
| Caixa (herda PessoaFisica) | id, nome, cpf, telefone, email, dataNascimento, salario, turno |
| Produto | id, nome, marca, valorUnitario, valorAtacado, qtdAtacado, validade |
| Fornecedor (herda PessoaJuridica) | id, nome, razaoSocial, cnpj, telefone, email, endereco |
| Pedido | id, status, formaPagamento, dataPedido, valorTotal, cliente, caixa, itens |
| Compra | id, status, dataCompra, previsaoEntrega, volumeTotal, valorFrete, valorTotal, fornecedor, itens |
| ItemPedido (apoio) | id, pedido, produto, quantidade, valorUnitario |
| ItemCompra (apoio) | id, compra, produto, quantidade, valorUnitario |

## Arquitetura (camadas)

Código em `app/src/main/java/edu/curso/`:

| Pacote | Camada | O que tem |
|---|---|---|
| `fronteira` | Fronteira (Boundary) | Telas JavaFX feitas só com código (sem FXML): formulário + `TableView` com coluna de ações. `Tela` guarda métodos usados por todas as telas. |
| `controle` | Controle (Control) | Propriedades do JavaFX ligadas aos campos (`bindBidirectional`), validação de cada campo e uma mensagem de erro para cada um. `Validador` tem as regras (CPF, CNPJ, e-mail, datas, valores). |
| `entidade` | Entidade | Classes do domínio (`Pessoa`, `PessoaFisica`, `PessoaJuridica`, `Cliente`, `Caixa`, ...). |
| `dao` | DAO | Uma interface e uma implementação (`...DAO` e `...DAOImpl`) por entidade, usando JDBC. `ConexaoFactory` abre a conexão e `PersistenciaException` leva os erros do banco até a tela. |

## Instruções para Execução

**Pré-requisitos**

- JDK 25 **com JavaFX incluído** (por exemplo, o BellSoft Liberica JDK 25 "Full": https://bell-sw.com/pages/downloads/). Um JDK sem JavaFX não compila o projeto.
- MariaDB rodando em `localhost:3306`.
- Internet na primeira execução, para o Gradle baixar o driver do MariaDB.

**Passo a passo**

1. Clone o projeto:
   ```bash
   git clone https://github.com/Sadzin1603/TrabalhoPOO.git
   cd TrabalhoPOO
   ```
2. Crie o banco executando o script `banco/mercado.sql` no MariaDB:
   ```bash
   mariadb -u root -p < banco/mercado.sql
   ```
3. Confira o usuário e a senha do banco em `app/src/main/java/edu/curso/dao/ConexaoFactory.java`. Estão configurados `root` e `123456`; altere se o seu MariaDB usar outros.
4. Execute o sistema:

| Sistema | Comando |
|---|---|
| Windows | `.\executar.bat` (ou `gradlew.bat run`) |
| Linux / Mac | `./gradlew run` |

No Linux/Mac, se aparecer `permission denied`, rode `chmod +x gradlew` uma vez.

Se o banco estiver desligado ou não existir, o sistema mostra uma mensagem de erro ao abrir.

## Divisão de Responsabilidades por Integrante

### Kauan

- Entidade, Controle, Fronteira e DAO de **Caixa** e **Cliente** (e as classes base `Pessoa` e `PessoaFisica`).
- Gravação do vídeo.

### Pedro

- Entidade, Controle, Fronteira e DAO de **Fornecedor** e **Compra** (e `ItemCompra`).
- Elaboração do README.
- Junção de todos os módulos em uma tela única (`MenuPrincipalView`).

### Hugo

- Entidade, Controle, Fronteira e DAO de **Pedido** e **Produto** (e `ItemPedido`).
- Comunicação entre os CRUDs (Pedido e Compra usam Cliente, Caixa, Fornecedor e Produto).
- Banco de dados (`banco/mercado.sql`).

## Link para o Vídeo

https://youtu.be/I2j9LsgC93U