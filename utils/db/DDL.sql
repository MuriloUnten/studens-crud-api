CREATE DATABASE boletim;
\c boletim;

CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(127)
);

CREATE TABLE nota (
    id_nota SERIAL,
    id_aluno INTEGER REFERENCES aluno ON DELETE CASCADE,
    nota NUMERIC
);
