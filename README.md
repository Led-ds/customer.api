# Customer Api
API responsável por manter dados do Cliente. 
Cadastrar, alterar, listar por paginação ou buscar por dado(s) específico(s) são funções provisionadas pela api.

# Technology
- Java8
- SpringBoot
- Swagger 
- JUnit
- Mockito
- PostgreSQL
- Rest
- SpringData
- Docker
- Maven
- Cloud AWS
    - EC2
    - VPC
    - RDS (to postgresql)
    - S3 (Bucket)
    - IAM (Create user access) 


# Providing Cloud AWS - IaC (Infrastructure as Code)
- Terraforme
- Ansible (Playbook)


# Init project
- Primeiramente vamos baixar o projeto no repositório do Github 
    - Abra o seu terminal num diretório específico e digite: git clone https://github.com/Led-ds/customer.api.git
    - Logo em seguida digite a tecla Enter e aguarde o download do projeto


- Importe o projeto (como um projeto maven) para IDE da sua preferência.

- Quero levar em consideração alguns pontos. Que já tenha o Maven e o Docker devidamente instalado na sua maquina local.

- Logo, após importar e configurar o projeto na sua IDE, basta rodar o Docker-compose para quer as imagens possam ser baixadas e containerizadas.

- Para teste de acesso e verificação do funcionamento, basta acessar a url: http://localhost:8080/swagger-ui.html

- Pagina do Swagger no ar, Aplicação funcionando;

- Para testar a API foi disponibilizado uma collection dentro do diretório /documents/postman/ no projeto. 
