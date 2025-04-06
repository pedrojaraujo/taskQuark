# TaskQuark

TaskQuark é uma API para gerenciamento de tarefas domésticas, desenvolvida com **Quarkus**, **Hibernate ORM** e **H2 Database**.

---

## 🚀 Tecnologias Utilizadas

- **Java 23**
- **Quarkus (REST, Hibernate, Panache)**
- **H2 Database (Banco em Memória)**
- **Swagger/OpenAPI**
- **Maven**

---

## 📌 Funcionalidades

- CRUD completo para **tarefas, categorias e usuários**.
- **Cinco rotas** expostas para interação com os dados.
- **Relacionamentos entre tabelas** garantindo integridade dos dados.
- **DTOs** para transporte de dados e padronização de respostas.
- Documentação automática da API via **Swagger**.
- Banco de dados embutido **H2**, sem necessidade de configuração extra.

---

## 🛠 Como Rodar o Projeto

1️⃣ **Clone o repositório**
```sh
git clone https://github.com/seuusuario/taskquark.git
cd taskquark
```

2️⃣ **Compile e inicie a API**
```sh
./mvnw compile quarkus:dev
```

3️⃣ **Acesse a documentação Swagger**
- Navegue para: [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

---

## 📌 Endpoints da API

### **1️⃣ Tarefas** (`/tasks`)
- `GET /tasks` → Lista todas as tarefas.
- `POST /tasks` → Cria uma nova tarefa.
- `PUT /tasks/{id}` → Atualiza uma tarefa.
- `DELETE /tasks/{id}` → Deleta uma tarefa.

### **2️⃣ Categorias** (`/categories`)
- `GET /categories` → Lista todas as categorias.
- `POST /categories` → Cria uma nova categoria.
- `PUT /categories/{id}` → Atualiza uma categoria.
- `DELETE /categories/{id}` → Deleta uma categoria.

### **3️⃣ Usuários** (`/users`)
- `GET /users` → Lista todos os usuários.
- `POST /users` → Cria um novo usuário.
- `PUT /users/{id}` → Atualiza um usuário.
- `DELETE /users/{id}` → Deleta um usuário.

---

## 📝 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

Copyright (c) 2025 Pedro Joaquim Araujo
