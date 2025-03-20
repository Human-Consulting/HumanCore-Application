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
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa)
);

CREATE TABLE projeto (
idProjeto INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
progresso FLOAT(2),
orcamento FLOAT(4),
com_impedimento BOOLEAN,
fkEmpresa INT, FOREIGN KEY (fkEmpresa) REFERENCES empresa (idEmpresa),
fkResponsavel INT, FOREIGN KEY (fkResponsavel) REFERENCES usuario (idUsuario)
);

CREATE TABLE sprint (
idSprint INT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(250),
dtInicio date,
dtFim date,
progresso float(2),
comImpedimento boolean,
fkProjeto INT, FOREIGN KEY (fkProjeto) REFERENCES projeto (idProjeto)
);

CREATE TABLE entrega (
idSprint INT PRIMARY KEY AUTO_INCREMENT,
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

select * from empresa;
select * from usuario;
select * from projeto;
select * from sprint;
select * from financeiro;

select projeto.idProjeto, projeto.descricao as "Projeto", projeto.progresso as "Progresso", projeto.orcamento as "Orçamento", empresa.nome as "Empresa", usuario.nome as "Responsavel" FROM projeto JOIN empresa ON fkEmpresa = idEmpresa JOIN usuario ON idUsuario = fkResponsavel;