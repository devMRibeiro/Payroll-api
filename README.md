# API RESTful com Spring Boot

## Descrição
Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciamento de **Employees** e **Orders**, seguindo as boas práticas recomendadas por Roy Fielding. A API utiliza **Spring HATEOAS**, permitindo que os clientes descubram dinamicamente as ações disponíveis por meio dos links embutidos nas respostas.

## Funcionalidades
- **Employees**:
  - Criar, ler, atualizar e deletar registros de funcionários.
- **Orders**:
  - Registrar um pedido.
  - Consultar pedidos.
  - Completar ou cancelar pedidos.
- **Spring HATEOAS**:
  - Links dinâmicos embutidos nas respostas.
- **Tratamento centralizado de exceções** via Controller Advice.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring HATEOAS
- Banco de Dados H2 (em memória)
- Maven

## Como Rodar o Projeto Localmente

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Configurar o Banco de Dados
No arquivo `src/main/resources/application.properties`, adicione as configurações necessárias para o banco H2:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### 3. Construir e Executar a Aplicação
```bash
mvn clean install
mvn spring-boot:run
```
A API estará rodando em: `http://localhost:8080`

### 4. Testar a API
Você pode testar os endpoints utilizando **Postman**, **cURL** ou diretamente pelo navegador. Algumas rotas disponíveis:
- `GET /employees` - Retorna todos os empregados.
- `POST /employees/register` - Registra um novo empregado.
- `GET /orders` - Retorna todos os pedidos.
- `POST /orders/register` - Registra um novo pedido.
- `PUT /orders/{id}/complete` - Marca um pedido como concluído.
- `DELETE /orders/{id}/cancel` - Cancela um pedido.

O console do H2 pode ser acessado em: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

## Contribuição
Fiquem à vontade para abrir **issues** ou enviar **pull requests**. Críticas construtivas são bem-vindas!

## Autor
Desenvolvido por Michael Ribeiro https://github.com/devMRibeiro

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).
