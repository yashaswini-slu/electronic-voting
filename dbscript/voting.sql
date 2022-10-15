-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.3
-- PostgreSQL version: 13.0
-- Project Site: pgmodeler.io
-- Model Author: Yashaswini

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: voting | type: DATABASE --
-- DROP DATABASE IF EXISTS voting;
CREATE DATABASE voting;
-- ddl-end --


-- object: code | type: SCHEMA --
-- DROP SCHEMA IF EXISTS code CASCADE;
CREATE SCHEMA code;
-- ddl-end --
ALTER SCHEMA code OWNER TO postgres;
-- ddl-end --

SET search_path TO pg_catalog,public,code;
-- ddl-end --

-- object: public.allowed_response_option | type: TABLE --
-- DROP TABLE IF EXISTS public.allowed_response_option CASCADE;
CREATE TABLE public.allowed_response_option (
	allowed_response_option_id bigserial NOT NULL,
	option varchar(100) NOT NULL,
	poll_question_id bigint,
	is_correct boolean NOT NULL,
	CONSTRAINT response_pk PRIMARY KEY (allowed_response_option_id)

);
-- ddl-end --
ALTER TABLE public.allowed_response_option OWNER TO postgres;
-- ddl-end --

-- object: public.poll_question | type: TABLE --
-- DROP TABLE IF EXISTS public.poll_question CASCADE;
CREATE TABLE public.poll_question (
	poll_question_id bigserial NOT NULL,
	poll_question_uuid uuid,
	poll_question text NOT NULL,
	poll_id_poll bigint,
	CONSTRAINT poll_issue_pk PRIMARY KEY (poll_question_id)

);
-- ddl-end --
ALTER TABLE public.poll_question OWNER TO postgres;
-- ddl-end --

-- object: poll_question_fk | type: CONSTRAINT --
-- ALTER TABLE public.allowed_response_option DROP CONSTRAINT IF EXISTS poll_question_fk CASCADE;
ALTER TABLE public.allowed_response_option ADD CONSTRAINT poll_question_fk FOREIGN KEY (poll_question_id)
REFERENCES public.poll_question (poll_question_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.poll | type: TABLE --
-- DROP TABLE IF EXISTS public.poll CASCADE;
CREATE TABLE public.poll (
	poll_id bigserial NOT NULL,
	title text,
	description text NOT NULL,
	start_date date NOT NULL,
	end_date smallint NOT NULL,
	CONSTRAINT poll_pk PRIMARY KEY (poll_id)

);
-- ddl-end --
COMMENT ON COLUMN public.poll.title IS E'title/Question/Issue';
-- ddl-end --
ALTER TABLE public.poll OWNER TO postgres;
-- ddl-end --

-- object: public.voter_response | type: TABLE --
-- DROP TABLE IF EXISTS public.voter_response CASCADE;
CREATE TABLE public.voter_response (
	voter_response_uid bigserial NOT NULL,
	pr_pr_relation_id bigint,
	cast_time timestamptz NOT NULL,
	allowed_response_option_id bigint,
	CONSTRAINT voter_cast_pk PRIMARY KEY (voter_response_uid,cast_time)

);
-- ddl-end --
ALTER TABLE public.voter_response OWNER TO postgres;
-- ddl-end --

-- object: allowed_response_option_fk | type: CONSTRAINT --
-- ALTER TABLE public.voter_response DROP CONSTRAINT IF EXISTS allowed_response_option_fk CASCADE;
ALTER TABLE public.voter_response ADD CONSTRAINT allowed_response_option_fk FOREIGN KEY (allowed_response_option_id)
REFERENCES public.allowed_response_option (allowed_response_option_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.party | type: TABLE --
-- DROP TABLE IF EXISTS public.party CASCADE;
CREATE TABLE public.party (
	party_id bigserial NOT NULL,
	party_cd_party_cd smallint,
	CONSTRAINT party_pk PRIMARY KEY (party_id)

);
-- ddl-end --
ALTER TABLE public.party OWNER TO postgres;
-- ddl-end --

-- object: code.party_cd | type: TABLE --
-- DROP TABLE IF EXISTS code.party_cd CASCADE;
CREATE TABLE code.party_cd (
	party_cd smallint NOT NULL,
	code varchar(100) NOT NULL,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	CONSTRAINT party_cd_pk PRIMARY KEY (party_cd)

);
-- ddl-end --
ALTER TABLE code.party_cd OWNER TO postgres;
-- ddl-end --

-- object: party_cd_fk | type: CONSTRAINT --
-- ALTER TABLE public.party DROP CONSTRAINT IF EXISTS party_cd_fk CASCADE;
ALTER TABLE public.party ADD CONSTRAINT party_cd_fk FOREIGN KEY (party_cd_party_cd)
REFERENCES code.party_cd (party_cd) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.party_role | type: TABLE --
-- DROP TABLE IF EXISTS public.party_role CASCADE;
CREATE TABLE public.party_role (
	party_role_id bigserial NOT NULL,
	party_id_party bigint,
	party_role_cd_party_role_cd smallint,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	CONSTRAINT party_role_pk PRIMARY KEY (party_role_id)

);
-- ddl-end --
ALTER TABLE public.party_role OWNER TO postgres;
-- ddl-end --

-- object: party_fk | type: CONSTRAINT --
-- ALTER TABLE public.party_role DROP CONSTRAINT IF EXISTS party_fk CASCADE;
ALTER TABLE public.party_role ADD CONSTRAINT party_fk FOREIGN KEY (party_id_party)
REFERENCES public.party (party_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: code.party_role_cd | type: TABLE --
-- DROP TABLE IF EXISTS code.party_role_cd CASCADE;
CREATE TABLE code.party_role_cd (
	party_role_cd smallint NOT NULL,
	code varchar(75) NOT NULL,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	CONSTRAINT party_role_cd_pk PRIMARY KEY (party_role_cd)

);
-- ddl-end --
COMMENT ON TABLE code.party_role_cd IS E'Voter,  Organizer, Checker, Scrutinizer, Proxy';
-- ddl-end --
ALTER TABLE code.party_role_cd OWNER TO postgres;
-- ddl-end --

-- object: party_role_cd_fk | type: CONSTRAINT --
-- ALTER TABLE public.party_role DROP CONSTRAINT IF EXISTS party_role_cd_fk CASCADE;
ALTER TABLE public.party_role ADD CONSTRAINT party_role_cd_fk FOREIGN KEY (party_role_cd_party_role_cd)
REFERENCES code.party_role_cd (party_role_cd) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.pr_pr_relation | type: TABLE --
-- DROP TABLE IF EXISTS public.pr_pr_relation CASCADE;
CREATE TABLE public.pr_pr_relation (
	pr_pr_relation_id bigserial NOT NULL,
	party_role_id_party_role bigint,
	party_role_id_party_role1 bigint,
	pr_pr_relation_cd smallint,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	weightage smallint,
	CONSTRAINT pr_pr_relation_pk PRIMARY KEY (pr_pr_relation_id)

);
-- ddl-end --
COMMENT ON TABLE public.pr_pr_relation IS E'Party Role - Party Role relationship';
-- ddl-end --
COMMENT ON COLUMN public.pr_pr_relation.weightage IS E'Sum of all e-voter weightage must be equal to 100.';
-- ddl-end --
ALTER TABLE public.pr_pr_relation OWNER TO postgres;
-- ddl-end --

-- object: party_role_fk | type: CONSTRAINT --
-- ALTER TABLE public.pr_pr_relation DROP CONSTRAINT IF EXISTS party_role_fk CASCADE;
ALTER TABLE public.pr_pr_relation ADD CONSTRAINT party_role_fk FOREIGN KEY (party_role_id_party_role)
REFERENCES public.party_role (party_role_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: party_role_fk1 | type: CONSTRAINT --
-- ALTER TABLE public.pr_pr_relation DROP CONSTRAINT IF EXISTS party_role_fk1 CASCADE;
ALTER TABLE public.pr_pr_relation ADD CONSTRAINT party_role_fk1 FOREIGN KEY (party_role_id_party_role1)
REFERENCES public.party_role (party_role_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: code.locale_cd | type: TABLE --
-- DROP TABLE IF EXISTS code.locale_cd CASCADE;
CREATE TABLE code.locale_cd (
	locale_cd smallint NOT NULL,
	code varchar(75) NOT NULL,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	CONSTRAINT locale_cd_pk PRIMARY KEY (locale_cd)

);
-- ddl-end --
ALTER TABLE code.locale_cd OWNER TO postgres;
-- ddl-end --

-- object: code.pr_pr_relation_cd | type: TABLE --
-- DROP TABLE IF EXISTS code.pr_pr_relation_cd CASCADE;
CREATE TABLE code.pr_pr_relation_cd (
	pr_pr_relation_cd smallint NOT NULL,
	code varchar(150) NOT NULL,
	start_date timestamptz NOT NULL,
	end_date timestamptz,
	CONSTRAINT pr_pr_relation_cd_pk PRIMARY KEY (pr_pr_relation_cd)

);
-- ddl-end --
COMMENT ON TABLE code.pr_pr_relation_cd IS E'Party role - Party role relationship';
-- ddl-end --
ALTER TABLE code.pr_pr_relation_cd OWNER TO postgres;
-- ddl-end --

-- object: pr_pr_relation_cd_fk | type: CONSTRAINT --
-- ALTER TABLE public.pr_pr_relation DROP CONSTRAINT IF EXISTS pr_pr_relation_cd_fk CASCADE;
ALTER TABLE public.pr_pr_relation ADD CONSTRAINT pr_pr_relation_cd_fk FOREIGN KEY (pr_pr_relation_cd)
REFERENCES code.pr_pr_relation_cd (pr_pr_relation_cd) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pr_pr_relation_fk | type: CONSTRAINT --
-- ALTER TABLE public.voter_response DROP CONSTRAINT IF EXISTS pr_pr_relation_fk CASCADE;
ALTER TABLE public.voter_response ADD CONSTRAINT pr_pr_relation_fk FOREIGN KEY (pr_pr_relation_id)
REFERENCES public.pr_pr_relation (pr_pr_relation_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: code.party_locale | type: TABLE --
-- DROP TABLE IF EXISTS code.party_locale CASCADE;
CREATE TABLE code.party_locale (
	description varchar(255) NOT NULL,
	start_time timestamptz NOT NULL DEFAULT current_timestamp,
	end_time timestamptz,
	party_cd smallint,
	locale_cd smallint
);
-- ddl-end --
ALTER TABLE code.party_locale OWNER TO postgres;
-- ddl-end --

-- object: party_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.party_locale DROP CONSTRAINT IF EXISTS party_cd_fk CASCADE;
ALTER TABLE code.party_locale ADD CONSTRAINT party_cd_fk FOREIGN KEY (party_cd)
REFERENCES code.party_cd (party_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: locale_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.party_locale DROP CONSTRAINT IF EXISTS locale_cd_fk CASCADE;
ALTER TABLE code.party_locale ADD CONSTRAINT locale_cd_fk FOREIGN KEY (locale_cd)
REFERENCES code.locale_cd (locale_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: code.party_role_locale | type: TABLE --
-- DROP TABLE IF EXISTS code.party_role_locale CASCADE;
CREATE TABLE code.party_role_locale (
	party_role_cd smallint,
	locale_cd smallint,
	description varchar(255) NOT NULL,
	start_time timestamptz NOT NULL DEFAULT current_timestamp,
	end_time timestamptz
);
-- ddl-end --
ALTER TABLE code.party_role_locale OWNER TO postgres;
-- ddl-end --

-- object: party_role_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.party_role_locale DROP CONSTRAINT IF EXISTS party_role_cd_fk CASCADE;
ALTER TABLE code.party_role_locale ADD CONSTRAINT party_role_cd_fk FOREIGN KEY (party_role_cd)
REFERENCES code.party_role_cd (party_role_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: locale_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.party_role_locale DROP CONSTRAINT IF EXISTS locale_cd_fk CASCADE;
ALTER TABLE code.party_role_locale ADD CONSTRAINT locale_cd_fk FOREIGN KEY (locale_cd)
REFERENCES code.locale_cd (locale_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: code.pr_pr_relation_locale | type: TABLE --
-- DROP TABLE IF EXISTS code.pr_pr_relation_locale CASCADE;
CREATE TABLE code.pr_pr_relation_locale (
	pr_pr_relation_cd smallint,
	locale_cd smallint,
	description varchar(255) NOT NULL,
	start_time timestamptz NOT NULL,
	end_time timestamptz
);
-- ddl-end --
ALTER TABLE code.pr_pr_relation_locale OWNER TO postgres;
-- ddl-end --

-- object: locale_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.pr_pr_relation_locale DROP CONSTRAINT IF EXISTS locale_cd_fk CASCADE;
ALTER TABLE code.pr_pr_relation_locale ADD CONSTRAINT locale_cd_fk FOREIGN KEY (locale_cd)
REFERENCES code.locale_cd (locale_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: pr_pr_relation_cd_fk | type: CONSTRAINT --
-- ALTER TABLE code.pr_pr_relation_locale DROP CONSTRAINT IF EXISTS pr_pr_relation_cd_fk CASCADE;
ALTER TABLE code.pr_pr_relation_locale ADD CONSTRAINT pr_pr_relation_cd_fk FOREIGN KEY (pr_pr_relation_cd)
REFERENCES code.pr_pr_relation_cd (pr_pr_relation_cd) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.login | type: TABLE --
-- DROP TABLE IF EXISTS public.login CASCADE;
CREATE TABLE public.login (
	login_id bigserial NOT NULL,
	user_id varchar(1000) NOT NULL,
	start_date date NOT NULL,
	end_date date,
	party_id_party bigint,
	CONSTRAINT login_primary_key PRIMARY KEY (login_id)

);
-- ddl-end --
ALTER TABLE public.login OWNER TO postgres;
-- ddl-end --

-- object: party_fk | type: CONSTRAINT --
-- ALTER TABLE public.login DROP CONSTRAINT IF EXISTS party_fk CASCADE;
ALTER TABLE public.login ADD CONSTRAINT party_fk FOREIGN KEY (party_id_party)
REFERENCES public.party (party_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: login_uq | type: CONSTRAINT --
-- ALTER TABLE public.login DROP CONSTRAINT IF EXISTS login_uq CASCADE;
ALTER TABLE public.login ADD CONSTRAINT login_uq UNIQUE (party_id_party);
-- ddl-end --

-- object: public.password | type: TABLE --
-- DROP TABLE IF EXISTS public.password CASCADE;
CREATE TABLE public.password (
	password_id bigserial NOT NULL,
	scerete_key varchar(50) NOT NULL,
	login_id_login bigint,
	CONSTRAINT password_primary_key PRIMARY KEY (password_id)

);
-- ddl-end --
ALTER TABLE public.password OWNER TO postgres;
-- ddl-end --

-- object: login_fk | type: CONSTRAINT --
-- ALTER TABLE public.password DROP CONSTRAINT IF EXISTS login_fk CASCADE;
ALTER TABLE public.password ADD CONSTRAINT login_fk FOREIGN KEY (login_id_login)
REFERENCES public.login (login_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.party_name | type: TABLE --
-- DROP TABLE IF EXISTS public.party_name CASCADE;
CREATE TABLE public.party_name (
	party_name_id bigserial NOT NULL,
	first_name varchar(250) NOT NULL,
	last_name varchar(250) NOT NULL,
	start_date date NOT NULL,
	end_date date,
	party_id_party bigint,
	rest_of_name varchar(250),
	middle_name varchar(250),
	is_preferred boolean NOT NULL,
	CONSTRAINT party_name_pk PRIMARY KEY (party_name_id)

);
-- ddl-end --
ALTER TABLE public.party_name OWNER TO postgres;
-- ddl-end --

-- object: party_fk | type: CONSTRAINT --
-- ALTER TABLE public.party_name DROP CONSTRAINT IF EXISTS party_fk CASCADE;
ALTER TABLE public.party_name ADD CONSTRAINT party_fk FOREIGN KEY (party_id_party)
REFERENCES public.party (party_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.login_history | type: TABLE --
-- DROP TABLE IF EXISTS public.login_history CASCADE;
CREATE TABLE public.login_history (
	login_history_id bigserial NOT NULL,
	start_date timestamp NOT NULL,
	end_date timestamp,
	login_id_login bigint,
	CONSTRAINT login_history_pk PRIMARY KEY (login_history_id)

);
-- ddl-end --
ALTER TABLE public.login_history OWNER TO postgres;
-- ddl-end --

-- object: login_fk | type: CONSTRAINT --
-- ALTER TABLE public.login_history DROP CONSTRAINT IF EXISTS login_fk CASCADE;
ALTER TABLE public.login_history ADD CONSTRAINT login_fk FOREIGN KEY (login_id_login)
REFERENCES public.login (login_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: poll_fk | type: CONSTRAINT --
-- ALTER TABLE public.poll_question DROP CONSTRAINT IF EXISTS poll_fk CASCADE;
ALTER TABLE public.poll_question ADD CONSTRAINT poll_fk FOREIGN KEY (poll_id_poll)
REFERENCES public.poll (poll_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


