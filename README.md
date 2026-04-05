# ANS TISS Scraper

Uma ferramenta de automação (Web Crawler/Scraper) desenvolvida em Groovy para a extração eficiente de dados e documentos relacionados ao Padrão TISS (Troca de Informação de Saúde Suplementar) diretamente do portal público da Agência Nacional de Saúde (ANS).

A aplicação opera via Interface de Linha de Comando (CLI) e orquestra um fluxo completo de ETL (Extract, Transform, Load), culminando na distribuição automatizada dos artefatos processados via e-mail.

## Funcionalidades

* **Extração de Documentos:** Navegação automatizada no DOM do portal da ANS para localizar e fazer o download da versão mais recente do "Componente de Comunicação" (arquivo .zip).
* **Processamento de Dados Tabulares:** Coleta do histórico de versões dos componentes TISS, aplicando regras de negócio para filtragem de datas (competências a partir de Janeiro de 2016) e persistência dos dados em formato `.csv`.
* **Download de Planilhas Auxiliares:** Mapeamento e download da Tabela de Erros de Envio para a ANS (arquivo .xlsx).
* **Gestão de Destinatários:** Módulo de persistência local (CRUD em .csv) para gerenciamento de e-mails interessados em receber as atualizações.
* **Notificação Automatizada:** Serviço de mensageria SMTP integrado para empacotar os três arquivos gerados e enviá-los como anexo para a lista de destinatários cadastrados.

## Tecnologias e Bibliotecas

* **Groovy 3:** Linguagem principal do projeto.
* **Gradle:** Gerenciador de dependências e automação de build.
* **Jsoup:** Motor principal para parsing de HTML e navegação por seletores CSS/Textuais.
* **HTTPBuilder-NG:** Cliente HTTP para simulação de requisições web.
* **Jakarta Mail:** Biblioteca para construção de mensagens MIME e envio de e-mails via SMTP.

## Pré-requisitos

Para executar este projeto localmente, é necessário ter instalado em sua máquina:
* Java Development Kit (JDK) 11 ou superior.
* Gradle (opcional caso utilize a wrapper do Gradle, mas recomendado para o escopo atual).
* Uma conta de e-mail com permissão para uso de "Senha de Aplicativo" (App Password) para testes do módulo de notificação.

## Como Executar

1. Clone este repositório para o seu ambiente local:
```bash
git clone [https://github.com/SEU_USUARIO/ans-tiss-scraper.git](https://github.com/SEU_USUARIO/ans-tiss-scraper.git)
cd ans-tiss-scraper
```

2. Execute o projeto utilizando o Gradle. A aplicação irá compilar as dependências e iniciar o menu interativo diretamente no terminal:
```bash
gradle run
```

3. Navegue pelo menu interativo:
    * **Opção 1:** Inicia o motor de web scraping. Os arquivos resultantes serão salvos na estrutura local de diretórios (`./Downloads/Arquivos_padrao_TISS/`).
    * **Opção 2:** Acesse para gerenciar os e-mails que receberão os relatórios.
    * **Opção 3:** Inicia o disparo de e-mails. Será solicitado o e-mail remetente e a senha de autenticação via *standard input*.

## Estrutura do Projeto

A arquitetura do projeto foi desenhada visando a separação de responsabilidades (SoC) e a organização em camadas:

* `model`: Classes de representação de dados estruturados e de domínio.
* `repository`: Camada de persistência de dados (leitura e escrita em disco).
* `service`: Regras de negócio, execução de tarefas de I/O, integração HTTP, rotinas de scraping e envio de notificações.
* `util`: Utilitários e formatadores para processamento de dados (ex: manipulação de datas).
* `view`: Camada de apresentação, responsável pelos menus e pela interação com o usuário no terminal.

## Desenvolvido por

Henrique Oliveira dos Santos  
[LinkedIn](https://www.linkedin.com/in/dev-henriqueo-santos/)