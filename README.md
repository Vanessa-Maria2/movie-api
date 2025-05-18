# movie-api

O projeto **Movie-api** consiste em uma API própria que consome a API pública The Movie Database ([TMDB](https://developer.themoviedb.org/)), com o objetivo de extrair dados de filmes para popular o banco de dados da Movie-api. Assim, provendo um serviço que permite acesso a informações, cadastro e exclusão deles.

## Funcionalidades
- **Cadastro de filmes**

    Permite cadastrar filmes na Movie-api através de uma busca intermediária na API TMDB pelo título do filme, assim, extraindo os dados e recuperando o primeiro filme encontrado para cadastrar na Movie-api.

- **Buscar todos os filmes**
    Recuperar todos os filmes cadastrados no banco de dados da aplicação Movie-api.

- **Buscar filme por id**
    Realiza uma busca por filme no banco de dados de Movie-api pelo id do filme.

- **Deleção de fime por id**
    Permite deletar um filme do banco de dados de Movie-api ao informar o id do filme.

# Estrutura projeto

	Para estruturação e organização do projeto utilizei a seguinte estrutura:
- model
- dto
- mapper
- controller
- repository
- service
- exception


    Com o objetivo de tratar as exceções lançadas utilizei um controlador de exceções a partir do @RestControllerAdvice, permitindo assim capturar as exceções lançadas nas classes de serviços. Além disso, criei uma mensagem padrão de erro para ser reutilizada.


# Acesso ao swagger da aplicação
Disponível em: 
https://movie-api-65ca.onrender.com/swagger-ui/index.html#/ 
 
⚠️ **Atenção:** Ao acessar a aplicação, aguarde alguns instantes, pois o servidor está hospedado em um serviço gratuito e pode demorar um pouco para iniciar.

# Como executar o projeto
⚠️ **Atenção:** Necessário arquivo de variáveis de ambiente .env

1. Clone o projeto para sua máquina local.
2. Navegue até o diretório do projeto e compile o projeto:
    ```
    ./mvnw clean install ou use o maven na IDE
    ```
3. Para rodar a aplicação, execute:
    ```
    ./mvnw spring-boot:run ou use o run da IDE
    ```
4. Abra o navegador e acesse [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) para visualizar o Swagger da aplicação.


# Tecnologias

- Spring Boot
- Banco de dados: Postgres
- Docker
- TMDB: API pública que fornece dados de filmes
- Swagger
- Servidor aplicação: [Render](https://render.com/ )
- Servidor banco de dados: [Neon](https://neon.tech/)

