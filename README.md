
# API para Dev Web Avançado - A1 - 2024.1

API para a matéria de Dev Web Avançado - Turma A1 - 2024.1

A Sociedade Brasileira de Computação deseja adotar um novo sistema para apoiar os organizadores dos eventos que realiza anualmente na criação dos websites destes eventos. O novo sistema seria um CMS com o objetivo de facilitar o registro pelos organizadores das informações sobre o evento que devem constar do site, em especial, a lista de atividades que compõem o evento, ou seja, a programação. Além disso, o sistema tem o objetivo de permitir que os participantes planejem de quais atividades querem participar, recebendo informações sobre estas.



## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/EduardoSiq/dev_web_api_final/
```

Entre no diretório do projeto

```bash
  cd my-project
```

Instale as dependências

```bash
  npm install
```

Inicie o servidor

```bash
  npm run start
```

Transfira o banco de dados
```bash
  mvn clean flyway:migrate -Dflyway.configFiles=myFlywayConfig.conf install
```

Rode a aplicação Springboot.
## Autores

- [@Eduardo Siqueira](https://www.github.com/EduardoSiq)
- [@Marcia Yumi Murata](https://www.github.com/YumiMurata)
- [@Carolina Arêas](https://www.github.com/carolareas)
