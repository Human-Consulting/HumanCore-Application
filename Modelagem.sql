CREATE DATABASE IF NOT EXISTS teste;

USE teste;

CREATE TABLE empresa (
idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(30),
cnpj CHAR(14),
urlImagem LONGTEXT
);



CREATE TABLE usuario (
idUsuario INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(30),
email VARCHAR(30),
senha VARCHAR(30),
cargo VARCHAR(30),
area VARCHAR(30),
permissao VARCHAR(30),
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa) ON DELETE CASCADE
);

CREATE TABLE projeto (
idProjeto INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
orcamento FLOAT(4),
urlImagem LONGTEXT,
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa) ON DELETE CASCADE,
fkResponsavel INT, FOREIGN KEY (fkResponsavel) REFERENCES usuario (idUsuario) ON DELETE CASCADE
);

CREATE TABLE sprint ( -- Progresso e comImpedimento vai depender das informações de suas entregas
idSprint INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
dtInicio date,
dtFim date,
fkProjeto INT, FOREIGN KEY (fkProjeto) REFERENCES projeto (idProjeto) ON DELETE CASCADE
);

CREATE TABLE tarefa (
idTarefa INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
dtInicio date,
dtFim date,
progresso float(2),
comImpedimento boolean,
fkSprint INT, FOREIGN KEY (fkSprint) REFERENCES sprint(idSprint) ON DELETE CASCADE,
fkResponsavel INT, FOREIGN KEY (fkResponsavel) REFERENCES usuario (idUsuario) ON DELETE CASCADE
);

CREATE TABLE financeiro (
idFinanceiro INT PRIMARY KEY AUTO_INCREMENT,
valor float(4),
dtInvestimento date,
fkProjeto INT, FOREIGN KEY (fkProjeto) REFERENCES projeto (idProjeto) ON DELETE CASCADE
);

INSERT INTO empresa (nome, cnpj) VALUES
('Human Consulting', '12456786543223');