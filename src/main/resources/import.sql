-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;


-- Inserir usuário root/admin
INSERT INTO TaskUser(id, name, email, password, apiKey)
VALUES (1, 'Administrador', 'admin@exemplo.com', 'senha123', '38400000-8cf0-11bd-b23e-10b96e4ef00d');

-- Inserir categorias padrão
INSERT INTO Category(id, name) VALUES (1, 'Trabalho');
INSERT INTO Category(id, name) VALUES (2, 'Pessoal');
INSERT INTO Category(id, name) VALUES (3, 'Estudo');
INSERT INTO Category(id, name) VALUES (4, 'Projetos');

-- Inserir tarefas de exemplo atribuídas ao usuário admin
INSERT INTO Task(id, title, description, completed, priority, dueDate, category_id, user_id)
VALUES (1, 'Configurar projeto', 'Configurar ambiente de desenvolvimento', false, 3, '2023-12-30', 1, 1);

INSERT INTO Task(id, title, description, completed, priority, dueDate, category_id, user_id)
VALUES (2, 'Estudar Quarkus', 'Aprender sobre APIs REST com Quarkus', false, 2, '2023-12-25', 3, 1);

INSERT INTO Task(id, title, description, completed, priority, dueDate, category_id, user_id)
VALUES (3, 'Fazer exercícios', 'Manter a saúde em dia', false, 1, '2023-12-20', 2, 1);

-- Resetar as sequências para o próximo ID
ALTER SEQUENCE TaskUser_SEQ RESTART WITH 2;
ALTER SEQUENCE Category_SEQ RESTART WITH 5;
ALTER SEQUENCE Task_SEQ RESTART WITH 4;