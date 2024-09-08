--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: persons; Type: TABLE; Schema: public
--

CREATE TABLE public.persons (
    id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);

--
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: persons_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.persons_id_seq OWNED BY public.persons.id;

--
-- Name: tasks; Type: TABLE; Schema: public
--

CREATE TABLE public.tasks (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(255),
    creation_time timestamp(6) without time zone,
    author_id bigint
);

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.id;

--
-- Name: performers; Type: TABLE; Schema: public
--

CREATE TABLE public.performers (
    id bigint NOT NULL,
    task_id bigint,
    person_id bigint
);

--
-- Name: performers_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.performers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: performers_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.performers_id_seq OWNED BY public.performers.id;

--
-- Name: persons id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.persons ALTER COLUMN id SET DEFAULT nextval('public.persons_id_seq'::regclass);

--
-- Name: tasks id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.tasks ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);

--
-- Name: performers id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.performers ALTER COLUMN id SET DEFAULT nextval('public.performers_id_seq'::regclass);

--
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);

--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);

--
-- Name: performers performers_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.performers
    ADD CONSTRAINT performers_pkey PRIMARY KEY (id);

--
-- Name: tasks_author_id_idx; Type: INDEX; Schema: public
--

CREATE INDEX tasks_author_id_idx ON public.tasks USING hash (author_id);

--
-- Name: performers_task_id_idx; Type: INDEX; Schema: public
--

CREATE INDEX performers_task_id_idx ON public.performers USING hash (task_id);

--
-- Name: performers_person_id_idx; Type: INDEX; Schema: public
--

CREATE INDEX performers_person_id_idx ON public.performers USING hash (person_id);

--
-- Name: tasks fk_tasks_author; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fk_tasks_author FOREIGN KEY (author_id) REFERENCES public.persons(id);

--
-- Name: performers fk_performers_task; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.performers
    ADD CONSTRAINT fk_performers_task FOREIGN KEY (task_id) REFERENCES public.tasks(id);

--
-- Name: performers fk_performers_person; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.performers
    ADD CONSTRAINT fk_performers_person FOREIGN KEY (person_id) REFERENCES public.persons(id);

--
-- PostgreSQL database dump complete
--