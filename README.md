### RUN ENVIRONMENT:

- Instalar e configurar ambiente para Java (ver. 21) e Maven.
- Instalar as dependências necessárias para o Python.
- rodar o ```npm install``` na pasta frontend para baixar as dependências.

Configurar o :
 - .env (usar o .env-example como referência)
 - application.properties no manager-API (conforme exemplo abaixo)

 ```
 spring.application.name=demo

# configurações de DB
spring.datasource.url=jdbc:mysql://localhost:3306/aplicacao?useSSL=false&createDatabaseIfNotExist=true
spring.datasource.username= ""
spring.datasource.password= ""
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8081
 ```
 