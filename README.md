# TaskQuark

TaskQuark é uma API para gerenciamento de tarefas domésticas, desenvolvida com **Quarkus**, **Hibernate ORM** e **H2 Database**.

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
- `DELETE /tasks/{id}` → Deleta uma tarefa.

### **2️⃣ Categorias** (`/categories`)
- `GET /categories` → Lista todas as categorias.
- `POST /categories` → Cria uma nova categoria.

### **3️⃣ Usuários** (`/users`)
- `GET /users` → Lista todos os usuários.
- `POST /users` → Cria um novo usuário.


---

## 📝 Licença MIT
Este projeto está sob a licença MIT - qualquer um pode usá-lo, desde que mencione **Pedro Joaquim Araujo** como criador original.

```txt
MIT License

Copyright (c) 2025 Pedro Joaquim Araujo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
```

---