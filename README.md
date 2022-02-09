# Agenda
Um CRUD simples de inclusao e ediçao de Pessoas

Netbeans 8.2
Java 8
Java EE 7
JSF 2.2
JPA 2.1
Hibernate 4.3
Primefaces 8

Estrutura do Banco de dados: 

CREATE DATABASE agenda;

CREATE TABLE public.pessoa (
  id integer NOT NULL,
  criado_em timestamp without time zone NOT NULL,
  nome character varying(255) NOT NULL,
  telefone character varying(255) NOT NULL,
  empresa character varying(255) NULL,
  cargo character varying(255) NULL
);
ALTER TABLE
  public.pessoa
ADD
  CONSTRAINT pessoa_pkey PRIMARY KEY (id);
  
  
