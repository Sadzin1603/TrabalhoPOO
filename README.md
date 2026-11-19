# TrabalhoPOO

Trabalho da disciplina de Programação Orientada a Objetos, em Java com Gradle.

Nome do projeto = 
Nome integrantes = Kauan Leonardo Felipe Rossi; Pedro Henrique da Silva Santos; Hugo Sales Silva.
Tema = Gestão de Mercado
Descrição do problema resolvido = 
Lista de entidades implementadas =
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

## Como rodar

No Windows use `gradlew.bat`; no Linux/Mac use `./gradlew`.

| Ação | Windows | Linux / Mac |
|---|---|---|
| Executar o programa | `gradlew.bat run` | `./gradlew run` |
| Rodar os testes | `gradlew.bat test` | `./gradlew test` |
| Compilar tudo | `gradlew.bat build` | `./gradlew build` |

> No Linux/Mac, se aparecer `permission denied`, rode `chmod +x gradlew` uma vez.

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
