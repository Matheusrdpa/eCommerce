
# Ecommerce API

Este projeto é uma API de E-commerce desenvolvida com Java + Spring Boot, utilizando PostgreSQL como banco principal (via Docker), H2 para testes em memória, e testes unitários/integração com JUnit e Mockito.

O objetivo é fornecer uma base sólida para um sistema de vendas online, incluindo gestão de produtos, usuários e pedidos.


## Sumário

 - Tecnologias Utilizadas

 - Como usar o projeto

    - Rodando com Docker
    - Rodando localmente

 - Testes

 - Endpoints Principais

 - Contribuindo


## Tecnologias Utilizadas

- Java 17+
- Spring Boot 3+
- PostgreSQL (produção)
- H2 Database (testes)
- JUnit 5 e Mockito (testes unitários e integração)
- Docker & Docker Compose
- Swagger (documentação dos endpoints)

## Como usar o projeto
**Requisitos:**

! Antes de rodar a aplicação, certifique-se de ter instalado no seu ambiente:

Java 17+

Maven 3.8+

Docker e Docker Compose (para rodar os containers)

PostgreSQL (se quiser rodar localmente sem Docker)

Git (para clonar o repositório)

Opcional:

IDE como IntelliJ IDEA ou Eclipse para desenvolvimento.

Postman / Insomnia para testar os endpoints.

---

-  **Rodando com Docker**

A primeira coisa a se fazer é copiar este repositório com o comando:

	> git clone https://github.com/Matheusrdpa/eCommerce.git


Após clonado, gere a pasta target com o comando:

	> mvn clean package

Então suba os containers com:

	> docker-compose up

Agora é só testar o projeto e quando quiser acabar com os containers use: 

	> docker-compose down

---

- **Rodando localmente sem o docker**

Clone o repositório:

	> git clone https://github.com/Matheusrdpa/eCommerce.git

Pode usar o projeto usando:

 	> mvn spring-boot:run
Para acessar o banco de dados em memoria copie a url: localhost:8080/h2-console






## Executando testes
O projeto contém testes unitários e de integração e são executados em um banco em memória.

Unitários: focam nas regras de negócio com Mockito.

Integração: utilizam o banco H2 em memória, simulando o ambiente real.

O projeto usa JUnit 5 + Mockito + H2.

	> mvn test
    
## Endpoints Principais

| Método | Endpoint         | Descrição                     |
|--------|-----------------|-------------------------------|
| GET    | /products       | Lista todos os produtos       |
| GET    | /products/{id}  | Busca produto por ID          |
| POST   | /products       | Cadastra novo produto         |
| PUT    | /products/{id}  | Atualiza produto existente    |
| DELETE | /products/{id}  | Remove produto                |
| POST   | /orders         | Cria um novo pedido           |
| GET    | /orders/{id}    | Consulta pedido por ID        |
| POST   | /users          | Cadastra um novo usuário      |

Documentação Swagger mais detalhada disponível em:

	> http://localhost:8080/swagger-ui.html



## Contribuindo

Faça um fork do projeto

Crie uma branch: git checkout -b nova-feature

Commit suas mudanças: git commit -m 'nova feature'

Envie para o repositório: git push origin nova-feature

Abra um Pull Request
