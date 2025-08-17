# Gerenciador de Comércio 

Este sistema foi desenvolvido para otimizar o controle de ordens de serviço e relatórios da loja onde trabalho, aplicando e praticando boas práticas de backend e APIs REST com Spring Boot, PostgreSQL e Spring Data JPA.

## Funcionalidades Principais

- Cadastro e gerenciamento de ordens de serviço
- Relatórios de vendas mensais e anuais
- Relatórios de produtos com estoque baixo
- API documentada via Swagger/OpenAPI
- Testes unitários e de integração com JUnit e Testcontainers

## Quick Start 

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL

### Configuração

1. Clone o repositório
```shell
git clone https://github.com/nxizy/ERP-gerenciador-de-comercio-backend.git
cd gerenciador-de-comercio
```

2. Crie o banco de dados `gerenciador-de-comercio` no PostgreSQL

3. Configure o application.properties
```shell
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_de_comercio
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```

### Execução

Para compilar e rodar o projeto, utilize os comandos:

```shell
mvn clean install
mvn spring-boot:run
```

### Consulte os Endpoints para utilização

Acesse no seu navegador:
```shell
http://localhost:8080/swagger-ui.html
```



## Tecnologias utilizadas

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Spring Security Crypto
- Springdoc OpenAPI 2.8 (Swagger)
- Lombok
- Maven


