Ultimate Bank
=============

Este é o Ultimate Bank, um banco desenvolvido em Java. Com o Ultimate Bank, os usuários podem realizar operações CRUD (Criar, Ler, Atualizar, Deletar) de usuários, com a criação automática de contas bancárias. Além disso, os usuários podem efetuar depósitos, saques e transferências para outros usuários.

Tecnologias Utilizadas
----------------------

-   Java: A linguagem principal de programação.
-   Lombok: Uma biblioteca Java que ajuda a reduzir o código boilerplate.
-   Spring Security: Um framework de autenticação e autorização para aplicativos Java.
-   JPA (Java Persistence API): Uma API Java para gerenciar dados relacionais.
-   Google Guava: Uma biblioteca Java que fornece funcionalidades auxiliares, incluindo ferramentas para criar hashes.

Funcionalidades
---------------

-   CRUD de Usuários: Os usuários podem ser criados, lidos, atualizados e deletados do banco de dados.
-   Criação Automática de Conta Bancária: Ao criar um usuário, uma conta bancária é automaticamente gerada para esse usuário.
-   Depósitos: Os usuários podem fazer depósitos em suas contas bancárias.
-   Saques: Os usuários podem efetuar saques de suas contas bancárias.
-   Transferências: Os usuários podem transferir fundos entre suas contas bancárias e as de outros usuários.

Configuração do Projeto
-----------------------

### Clonar o Repositório

1.  Clone o Repositório: Clone este repositório para sua máquina local.

    `git clone https://github.com/Cleverton-Rocha/ultimate-bank-java`

### Configuração do Ambiente com Docker Compose

1.  Docker e Docker Compose: Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina.

2.  Execute o Docker Compose: Navegue até a raiz do projeto clonado e execute o seguinte comando para construir e iniciar os contêineres Docker:

    `docker-compose up`

### Execução do Projeto

1.  Importe o Projeto: Importe o projeto em sua IDE preferida.

2.  Configuração do Banco de Dados: As configurações de conexão com o banco de dados já estarão definidas no arquivo `application.properties` ou `application.yml`, uma vez que o banco de dados estará em execução em um contêiner Docker.

3.  Execução: Execute o projeto em sua IDE ou através do Maven.

Com essas etapas, você deverá ter o Ultimate Bank configurado e em execução em seu ambiente local.
