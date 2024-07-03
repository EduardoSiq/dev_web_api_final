
# API para Dev Web Avançado - A1 - 2024.1

API para a matéria de Dev Web Avançado - Turma A1 - 2024.1

A Sociedade Brasileira de Computação deseja adotar um novo sistema para apoiar os organizadores dos eventos que realiza anualmente na criação dos websites destes eventos. O novo sistema seria um CMS com o objetivo de facilitar o registro pelos organizadores das informações sobre o evento que devem constar do site, em especial, a lista de atividades que compõem o evento, ou seja, a programação.



## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/EduardoSiq/dev_web_api_final/
```

Entre no diretório do projeto

```bash
  cd my-project
```

Rodar migrations
```bash
  mvn clean flyway:migrate -Dflyway.configFiles=myFlywayConfig.conf install
```

Rodar aplicação
```bash
mvn spring-boot:run
```
## Documentação
Para visualizar a documentação da API, baixe o arquivo [swagger.yaml](https://github.com/EduardoSiq/dev_web_api_final/blob/main/swagger_api.yaml) e utilize o [Swagger Editor](https://editor.swagger.io/) para executar o arquivo corretamente.

Requests testes usados no vídeo no arquivo [http_requests.http](https://github.com/EduardoSiq/dev_web_api_final/blob/main/http/generated-requests.http).


## Autores

- [@Eduardo Siqueira](https://www.github.com/EduardoSiq)
- [@Marcia Yumi Murata](https://www.github.com/YumiMurata)
- [@Carolina Arêas](https://www.github.com/carolareas)
