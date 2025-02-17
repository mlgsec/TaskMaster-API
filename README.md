---

# TaskMaster API

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green)
![Swagger](https://img.shields.io/badge/Swagger-2.8.4-lightgreen)
![GitHub](https://img.shields.io/badge/GitHub-Open%20Source-brightgreen)

TaskMaster API é uma aplicação backend desenvolvida em **Java** com **Spring Boot** para gerenciamento de tarefas. Esta API permite criar, ler, atualizar e excluir tarefas de forma eficiente, seguindo boas práticas de desenvolvimento e documentação.

---

## ✨ Funcionalidades

- **Criação de tarefas**: Crie novas tarefas com título, descrição e status.
- **Listagem de tarefas**: Obtenha uma lista de todas as tarefas cadastradas.
- **Atualização de tarefas**: Atualize os detalhes de uma tarefa existente.
- **Exclusão de tarefas**: Remova tarefas que não são mais necessárias.
- **Documentação completa**: API documentada com **Swagger** para facilitar o uso e integração.

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.4.2
- **Banco de Dados**: H2 (para testes)
- **Documentação**: Swagger (OpenAPI 2.8.4)
- **Ferramentas de Build**: Maven

### Testes
- **JUnit 5**
- **Mockito**
- **Spring Boot Test**
- **H2 Database**

### Ferramentas Adicionais
- **Lombok**
- **Validation** (`spring-boot-starter-validation`)

---

## 📚 Documentação da API

A documentação completa da API está disponível via **Swagger UI**. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

### Exemplo de Endpoints:

- **GET** `/api/tasks` - Lista todas as tarefas.
- **POST** `/api/tasks` - Cria uma nova tarefa.
- **PUT** `/api/tasks/{id}` - Atualiza uma tarefa existente.
- **DELETE** `/api/tasks/{id}` - Exclui uma tarefa.

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para rodar a aplicação localmente:

### Pré-requisitos

- Java 21 instalado.
- Maven/Gradle instalado.
- (Adicione outros pré-requisitos, se necessário).

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/taskmaster-api.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd taskmaster-api
   ```

3. Compile o projeto:
   ```bash
   mvn clean install
   ```

4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse a API em:
   ```
   http://localhost:8080/api/tasks
   ```

---

## 🤝 Como Contribuir

Contribuições são bem-vindas! Siga os passos abaixo:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b feature/nova-feature
   ```
3. Commit suas alterações:
   ```bash
   git commit -m "feat: add nova feature"
   ```
4. Envie as alterações:
   ```bash
   git push origin feature/nova-feature
   ```
5. Abra um Pull Request.

## 👨‍💻 Autor

Feito com por **Mateus**!
---
