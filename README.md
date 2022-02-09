# Agenda

Um CRUD simples de inclusao e ediçao de Pessoas

Netbeans 8.2 <br/>
Java 8 <br/>
Java EE 7 <br/>
JSF 2.2 <br/>
JPA 2.1 <br/>
Hibernate 4.3 <br/>
Primefaces 8 <br/>

Estrutura do Banco de dados:

CREATE DATABASE agenda;

CREATE TABLE public.pessoa ( <br/>
id integer NOT NULL, <br/>
criado_em timestamp without time zone NOT NULL, <br/>
nome character varying(255) NOT NULL, <br/>
telefone character varying(255) NOT NULL, <br/>
empresa character varying(255) NULL, <br/>
cargo character varying(255) NULL <br/>
); <br/>
ALTER TABLE <br/>
public.pessoa <br/>
ADD <br/>
CONSTRAINT pessoa_pkey PRIMARY KEY (id);
