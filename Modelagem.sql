CREATE DATABASE IF NOT EXISTS teste;

USE teste;

CREATE TABLE empresa (
idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(30),
cnpj CHAR(14)
);

CREATE TABLE usuario (
idUsuario INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(30),
email VARCHAR(30),
senha VARCHAR(30),
cargo VARCHAR(30),
area VARCHAR(30),
permissao VARCHAR(30), -- adição do campo permissao
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa)
);

CREATE TABLE projeto ( -- Progresso e comImpedimento vai depender das informações de suas sprints
idProjeto INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
progresso FLOAT(2),
orcamento FLOAT(4),
urlImagem text, -- adição do campo permissao no tipo text por ser maior do que varchar...
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa),
fkResponsavel INT, FOREIGN KEY (fkResponsavel) REFERENCES usuario (idUsuario)
);
CREATE TABLE sprint ( -- Progresso e comImpedimento vai depender das informações de suas entregas
idSprint INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
dtInicio date,
dtFim date,
fkProjeto INT, FOREIGN KEY (fkProjeto) REFERENCES projeto (idProjeto)
);

CREATE TABLE entrega (
idEntrega INT PRIMARY KEY AUTO_INCREMENT, -- Modificação no nome do id
descricao VARCHAR(250),
dtInicio date,
dtFim date,
progresso float(2),
comImpedimento boolean,
fkSprint INT, FOREIGN KEY (fkSprint) REFERENCES sprint(idSprint),
fkResponsavel INT, FOREIGN KEY (fkResponsavel) REFERENCES usuario (idUsuario)
);

CREATE TABLE financeiro (
idFinanceiro INT PRIMARY KEY AUTO_INCREMENT,
valor float(4),
dtInvestimento date,
fkProjeto INT, FOREIGN KEY (fkProjeto) REFERENCES projeto (idProjeto)
);

INSERT INTO empresa (nome, cnpj) VALUES
('Tech Solutions', '12345678000199'),
('InovaDev', '98765432000111'),
('Human Consulting', '12456786543223');

INSERT INTO usuario (nome, email, senha, cargo, area, fkEmpresa) VALUES
('João Pedro Mori Noce', 'jpmorenoce@gmail.com', 'senha123', 'Diretor', 'Executiva', 1),
('Rafaella Guedes', 'rafaella.guedes@gmail.com', '123senha', 'Gerente de Projetos', 'Tecnologia', 1),
('Samuel Luciano', 'samuelDev@gmail.com', 'samual123', 'Dev Frontend', 'Tech', 3),
('Rafael Efgham', 'rafael@gmail.com', 'rafael123', 'Analista de Banco de Dados', 'Tech', 3),
('Pedro Ueda', 'pedro@gmail.com', 'pedro123', 'Analista Backend', 'Tech', 3),
('Stephanie Gomes', 'stephanie@gmail.com', 'ster123', 'PM', 'Negócios', 3);

INSERT INTO projeto (descricao, progresso, orcamento, com_impedimento, fkEmpresa, fkResponsavel) VALUES
('Sistema de Gestão Hospitalar', 75.5, 250.0, false, 1, 1),
('Plataforma E-commerce', 40.0, 120.0, true, 2, 3),
('App de Monitoramento Ambiental', 90.0, 300.0, false, 1, 2),
('Sistema da Human Consulting', 60, 5000, 0, 3, 1);

INSERT INTO sprint (descricao, dtInicio, dtFim, progresso, comImpedimento, fkProjeto) VALUES
('Sprint de elicitação de requisitos e conhecimento da empresa', '2025-03-01', '2025-03-31', 100.0, false, 4),
('Sprint para desenvolvimento do sistema e homologação', '2025-04-01', '2025-04-30', 70, true, 4),
('Sprint para inicio de testes unitários e entrega do projeto', '2025-05-01', '2025-06-01', 0, false, 4);

INSERT INTO entrega (descricao, dtInicio, dtFim, progresso, comImpedimento, fkSprint, fkResponsavel) VALUES
('Entrevistas com stakeholders', '2025-03-07', '2025-03-09', 100, false, 1, 1),
('Mapeamento de processos internos', '2025-03-12', '2025-03-16', 100, false, 1, 6),
('Análise de sistemas existentes', '2025-03-17', '2025-03-23', 90, false, 1, 2),
('Criação do backlog inicial', '2025-03-24', '2025-03-31', 80, false, 1, 1),
('Desenvolvimento da interface inicial', '2025-04-01', '2025-04-08', 60, false, 2, 3),
('Criação das rotas e controllers da API', '2025-04-05', '2025-04-15', 50, false, 2, 5),
('Modelagem e criação de banco de dados', '2025-04-06', '2025-04-18', 70, false, 2, 4),
('Homologação com cliente', '2025-04-20', '2025-04-24', 30, false, 2, 6),
('Revisão técnica de segurança e escalabilidade', '2025-04-25', '2025-04-30', 20, false, 2, 1),
('Testes unitários frontend', '2025-05-01', '2025-05-08', 0, false, 3, 3),
('Testes unitários backend', '2025-05-01', '2025-05-08', 0, false, 3, 5),
('Ajustes finais no banco de dados', '2025-05-06', '2025-05-14', 10, false, 3, 4),
('Validação de requisitos entregues', '2025-05-15', '2025-05-20', 0, false, 3, 2),
('Preparação de documentação e apresentação final', '2025-05-21', '2025-06-01', 0, false, 3, 1);

INSERT INTO financeiro (valor, dtInvestimento, fkProjeto) VALUES
(50000.00, '2024-02-01', 1),
(30000.00, '2024-03-01', 2),
(80000.00, '2024-04-01', 3);

select * from empresa;
select * from usuario;
select * from projeto;
select * from sprint;
select * from entrega;
select * from financeiro;
