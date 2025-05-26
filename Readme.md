# SHRUL

- [Introdução](#introdução)
- [Como usar](#como-usar)
  - [Entidades](#entidades)
    - [User](#user)
    - [Prefix](#prefix)
    - [Link](#link)
    - [UrlAccess](#urlaccess)
  - [Controllers](#controllers)
    - [UserController](#usercontroller)
    - [PrefixController](#prefixcontroller)
    - [LinkController](#linkcontroller)
    - [UrlAccessController](#urlaccesscontroller)
    - [RedirectController](#redirectcontroller)
  - [Segurança](#segurança)
  - [Tratamento de Erros](#tratamento-de-erros)
    - [BadRequestExceptionsHandler](#badrequestexceptionshandler)
    - [ApplicationExceptionHandler](#applicationexceptionhandler)
    - [LowExceptionHandler](#lowexceptionhandler)
  - [Swagger](#swagger)
  - [Java](#java)
  - [Docker](#docker)
- [Créditos](#créditos)
- [Sobre o Desenvolvedor](#sobre-o-desenvolvedor)
- [Licença](#licença)

## Introdução

Esta é uma API REST para encurtamento de links. Seu uso se dá pelos endpoints que serão descritos em [Controllers](#controllers). Dentre os principais recursos estão a criação de [usuários](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/User.java), criação de [links](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Link.java) encurtados com código único que podem ter um [prefixo](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Prefix.java), se assim desejar, além de registro de [acessos](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/UrlAccess.java).

## Como usar

### Entidades

#### [User](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/User.java)

Representa o usuário do sistema e serve como base para autenticação e acesso às demais entidades. A classe implementa UserDetails, sendo usada para autenticação via Spring Security.

#### [Prefix](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Prefix.java)

Entidade que representa o prefixo de links encurtados. O seu uso é opcional, e provavelmente é preferível não usá-lo.

#### [Link](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Link.java)

Entidade que representa o link encurtado. A criação do seu código é delegada para [LinkService](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/service/LinkService.java) que o atribui manualmente.

### [UrlAccess](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/UrlAccess.java)

Entidade que representa os acessos aos links encurtados. Sua criação é feita de forma assíncrona durante o redirecionamento.

## Controllers

A maioria dos controllers carrega os endpoints relacionados à uma única entidade.

### [UserController](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/controller/UserController.java)

Controller relacionado à entidade [User](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/User.java). O prefixo é `api/user`. Seus endpoints são:

| Método | Endpoints     | Body                | Retorno        | Permissão | Autenticação | Resumo |
|--------|---------------|---------------------|----------------|-----------|--------------|--------|
| POST   | /login        | LoginDTO            | JwtTokenDTO    | NÃO       | NÃO          | Realiza o login do usuário |
| POST   | /signup       | SignupDTO           | UserDTO        | NÃO       | NÃO          | Cria o usuário com validação por email |
| POST   | /send_code    | EmailDTO            | CodeHashDTO    | NÃO       | NÃO          | Envia um código de confirmação do email |
| PUT    | /             | UserUpdateDTO       | UserDTO        | NÃO       | SIM          | Atualiza dados menos sensíveis do usuário |
| DELETE | /             | DeleteAccountDTO    | NONE           | NÃO       | SIM          | Deleta o usuário, usando a senha para validação |
| PUT    | /password     | PasswordUpdateDTO   | UserDTO        | NÃO       | SIM          | Atualiza a senha com validação por código enviado |
| PUT    | /lock?id&locked | NÃO               | NONE           | SIM       | SIM          | Bloqueia o usuário no sistema, ou o inverso |
| GET    | /             | NÃO                 | UserDTO        | NÃO       | SIM          | Retorna o usuário autenticado |

*NOTAS*:

- O endpoint /send_code envia um código de verificação para o email informado.
- O corpo do endpoint /password exige um código válido para permitir a troca de senha.

*Classes em destaque*:
- [LoginDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/register/LoginDTO.java)
- [SignupDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/register/SignupDTO.java)
- [EmailDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/auth/EmailDTO.java)
- [JwtTokenDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/auth/JwtTokenDTO.java)
- [CodeHashDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/auth/CodeHashDTO.java)
- [UserUpdateDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/update/UserUpdateDTO.java)
- [PasswordUpdateDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/update/PasswordUpdateDTO.java)
- [DeleteAccountDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/register/DeleteAccountDTO.java)
- [UserDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/UserDTO.java)

### [PrefixController](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/controller/PrefixController.java)

Controller relacionado à entidade [Prefix](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Prefix.java). O prefixo é `api/prefix`. Seus endpoints são:

| Método | Endpoints | Body                | Retorno         | Permissão | Autenticação | Resumo |
|--------|-----------|---------------------|-----------------|-----------|--------------|--------|
| POST   | /         | RegisterPrefixDTO   | PrefixDTO       | NÃO       | SIM          | Registra um novo prefix associado ao usuário logado |
| PUT    | /         | PrefixUpdateDTO     | PrefixDTO       | SIM       | SIM          | Atualiza um prefix existente do usuário autenticado |
| DELETE | /?id      | NÃO                 | NONE            | SIM       | SIM          | Deleta um prefix por ID, se pertencer ao usuário    |
| GET    | /empty    | NÃO                 | PrefixDTO       | NÃO       | SIM          | Retorna o prefix "vazio" do usuário autenticado     |
| GET    | /         | NÃO                 | Page<PrefixDTO> | NÃO       | SIM          | Lista todos os prefixos do usuário autenticado      |

*NOTAS*: 
- O parâmetro id no DELETE é obrigatório e representa o identificador do prefix a ser removido.
- O retorno paginado (Page<PrefixDTO>) na listagem contém todos os prefixos do usuário autenticado.
- O endpoint /empty retorna o prefixo reservado para links sem categoria.

*Classes em destaque*:
- [RegisterPrefixDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/register/RegisterPrefixDTO.java)
- [PrefixUpdateDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/update/PrefixUpdateDTO.java)
- [PrefixDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/PrefixDTO.java)

### [LinkController](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/controller/LinkController.java)
Controller relacionado à entidade [Link](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/Link.java). O prefix é `api/link`. Seus endpoints são:

| Método | Endpoints       | Body               | Retorno         | Permissão | Autenticação | Resumo |
|--------|------------------|--------------------|-----------------|-----------|--------------|--------|
| POST   | /                | RegisterLinkDTO    | LinkDTO         | SIM       | SIM          | Registra um novo link associado a um prefix do usuário |
| PUT    | /                | LinkUpdateDTO      | LinkDTO         | SIM       | SIM          | Atualiza um link de um prefix do usuário |
| DELETE | /?id=            | NÃO                | NONE            | SIM       | SIM          | Deleta um link por ID, se pertencer ao usuário |
| GET    | /                | NÃO                | Page<LinkDTO>   | NÃO       | SIM          | Lista todos os links do usuário autenticado |
| GET    | /?prefix=        | NÃO                | Page<LinkDTO>   | SIM       | SIM          | Lista os links associados a um prefix do usuário |

*NOTAS*:

- O parâmetro id no DELETE é obrigatório e representa o identificador do link.
- O parâmetro prefix no GET filtra os links vinculados a um prefix específico.
- O retorno paginado (Page<LinkDTO>) contém os links conforme o contexto da requisição.

*Classes em destaque*:
- [RegisterLinkDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/register/RegisterLinkDTO.java)
- [LinkUpdateDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/update/LinkUpdateDTO.java)
- [LinkDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/LinkDTO.java)

### [UrlAccessController](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/controller/UrlAccessController.java)
Controller relacionado ao [acesso a URLs](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/model/UrlAccess.java). O prefix BASE é `api/url_access`. Seus endpoints são:

| Método | Endpoints  | Body | Retorno            | Permissão | Autenticação | Resumo                                      |
|--------|------------|------|--------------------|-----------|--------------|---------------------------------------------|
| GET    | /week      | NÃO  | Page<UrlAccessDTO> | NÃO       | SIM          | Retorna acessos às URLs da última semana    |
| GET    | /month     | NÃO  | Page<UrlAccessDTO> | NÃO       | SIM          | Retorna acessos às URLs do último mês       |
| GET    | /          | NÃO  | Page<UrlAccessDTO> | NÃO       | SIM          | Retorna todos os acessos às URLs do usuário |

*Classes em destaque*:
- [UrlAccessDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/UrlAccessDTO.java)

### [RedirectController](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/controller/RedirectController.java)
Controller responsável pelo redirecionamento de URLs encurtadas. Seus endpoints são:

| Método | Endpoints       | Body | Retorno      | Permissão | Autenticação | Resumo                                       |
|--------|------------------|------|--------------|-----------|--------------|----------------------------------------------|
| GET    | /{id}            | NÃO  | RedirectView | NÃO       | NÃO          | Redireciona uma URL a partir de seu ID       |
| GET    | /{prefix}/{id}   | NÃO  | RedirectView | NÃO       | NÃO          | Redireciona uma URL com base em prefixo e ID |

### Segurança
A autenticação da API é baseada em JWT (JSON Web Token). Após o login via o endpoint /login do UserController, um token JWT é gerado e retornado ao cliente. Esse token deve ser incluído no cabeçalho Authorization das requisições autenticadas, no formato:

```makefile
Authorization: Bearer <token>
```
A verificação do token é feita automaticamente pelo filtro [JwtAuthenticationFilter](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/infra/security/filter/JwtAuthenticationFilter.java), que valida a assinatura, a expiração e identifica o usuário correspondente. Endpoints que exigem autenticação estão claramente marcados com `Autenticação: SIM` nas tabelas deste README.

### Tratamento de Erros
A aplicação utiliza três classes com a anotação @RestControllerAdvice, responsáveis por interceptar exceções e retornar respostas padronizadas. Cada handler tem um foco específico:

#### [`BadRequestExceptionsHandler`](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/infra/exception/handler/BadRequestExceptionsHandler.java)
Trata especificamente exceções que resultam em 400 Bad Request.
Usado para validar erros do cliente relacionados a dados inválidos ou requisições mal formadas.

#### [`ApplicationExceptionHandler`](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/infra/exception/handler/ApplicationExceptionHandler.java)
Trata exceções gerais e conhecidas da aplicação que não se enquadram no caso do BadRequestExceptionHandler.
Pode retornar outros códigos HTTP conforme a necessidade.

##### [`LowExceptionHandler`](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/infra/exception/handler/LowExceptionHandler.java)
Captura exceções inesperadas, retornando 500 Internal Server Error para garantir a segurança e não expor detalhes sensíveis.

#### Estrutura de Resposta de Erro
As respostas de erro são baseadas nas classes [ExceptionDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/exception/ExceptionDTO.java) e [ErrorMessageDTO](https://github.com/Glayon-Oliveira/shrul/blob/main/src/main/java/com/lmlasmo/shrul/dto/exception/ExceptionDTO.java). O campo messages só é incluído quando não for nulo.

Exemplo de resposta para erro 400:

```json
{
  "status": 400,
  "error": "Bad Request",
  "timestamp": "2025-07-20T15:32:45.123",
  "path": "/api/exemplo",
  "messages": [
    {
      "message": "Campo 'email' é obrigatório"
    }
  ]
}
```

### Swagger
Este projeto possui documentação automática da API gerada com Swagger através da biblioteca *Springdoc OpenAPI*.

A interface do Swagger permite:

- Visualizar todos os endpoints disponíveis com detalhes.
- Ver os parâmetros esperados e exemplos de resposta.
- Testar os endpoints diretamente do navegador, facilitando o desenvolvimento e depuração.

Após executar o projeto, a documentação interativa pode ser acessada em:

```bash
http://localhost:8080/swagger-ui.html
```
Ou:

```bash
http://localhost:8080/swagger-ui/index.html
```

### Java

Este projeto utiliza **Java 17** com o framework **Spring Boot**. Para executar localmente:

1. Certifique-se de ter o Java instalado (versão 17).
2. Configure um banco de dados MySQL.
3. Defina as variáveis de ambiente necessárias (Veja application.properties).
4. Rode a aplicação com:

```bash
./mvnw spring-boot:run
``` 

### Docker

Este projeto inclui suporte para Docker, permitindo subir a aplicação e o banco de dados com facilidade.

Para rodar com Docker Compose:  
Certifique-se de ter o Docker e o Docker Compose instalados.

Execute:

```bash
docker-compose --profile all up --build
```

Se tiver o Maven instalado no sistema, pode usar o comando abaixo para evitar baixar todas as dependências a cada docker-compose up:

```bash
docker-compose BUILD_TARGET=dFinal --profile all up --build
```

A aplicação provavelmente resetará várias vezes até que o serviço do banco de dados esteja pronto.

Se já tiver um banco de dados pronto e/ou pretende usar seu próprio serviço de email, basta ajustar o profile e as variáveis no arquivo .env.
Veja o docker-compose.yml para saber quais variáveis podem ser configuradas. Ajuste o comando de acordo com cada caso.

## Créditos

Desenvolvido sobre a robusta plataforma Spring Boot, este projeto também se apoia em diversas bibliotecas auxiliares, cujos mantenedores são igualmente reconhecidos e agradecidos. Todas as dependências estão listadas no pom.xml.

## Sobre o Desenvolvedor

Projeto desenvolvido por Glayon Oliveira de Santana.

- GitHub: [github.com/Glayon-Oliveira](https://github.com/Glayon-Oliveira)
- LinkedIn: [linkedin.com/in/Glayon](https://linkedin.com/in/Glayon)
- E-mail: [glayonoliv@gmail.com](mailto:glayonoliv@gmail.com)

### Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](https://github.com/Glayon-Oliveira/shrul/edit/main/LICENSE) para detalhes.
