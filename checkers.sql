--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: checkers; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE checkers (
    id integer NOT NULL,
    type integer,
    columnposition integer,
    rowposition integer,
    gameid integer
);


ALTER TABLE checkers OWNER TO satchelgrant;

--
-- Name: checkers_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE checkers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkers_id_seq OWNER TO satchelgrant;

--
-- Name: checkers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE checkers_id_seq OWNED BY checkers.id;


--
-- Name: games; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE games (
    id integer NOT NULL,
    playercount integer,
    playerturn integer,
    saved boolean,
    takenwhitecheckers integer,
    takenredcheckers integer,
    aitype integer
);


ALTER TABLE games OWNER TO satchelgrant;

--
-- Name: games_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE games_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE games_id_seq OWNER TO satchelgrant;

--
-- Name: games_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE games_id_seq OWNED BY games.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE users (
    id integer NOT NULL,
    username character varying,
    password character varying,
    gameswon integer,
    gameslost integer
);


ALTER TABLE users OWNER TO satchelgrant;

--
-- Name: users_games; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE users_games (
    id integer NOT NULL,
    userid integer,
    gameid integer
);


ALTER TABLE users_games OWNER TO satchelgrant;

--
-- Name: users_games_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE users_games_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_games_id_seq OWNER TO satchelgrant;

--
-- Name: users_games_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE users_games_id_seq OWNED BY users_games.id;


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO satchelgrant;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY checkers ALTER COLUMN id SET DEFAULT nextval('checkers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY games ALTER COLUMN id SET DEFAULT nextval('games_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users_games ALTER COLUMN id SET DEFAULT nextval('users_games_id_seq'::regclass);


--
-- Data for Name: checkers; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY checkers (id, type, columnposition, rowposition, gameid) FROM stdin;
\.


--
-- Name: checkers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('checkers_id_seq', 144, true);


--
-- Data for Name: games; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY games (id, playercount, playerturn, saved, takenwhitecheckers, takenredcheckers, aitype) FROM stdin;
\.


--
-- Name: games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('games_id_seq', 6, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY users (id, username, password, gameswon, gameslost) FROM stdin;
\.


--
-- Data for Name: users_games; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY users_games (id, userid, gameid) FROM stdin;
\.


--
-- Name: users_games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('users_games_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('users_id_seq', 1, true);


--
-- Name: checkers_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY checkers
    ADD CONSTRAINT checkers_pkey PRIMARY KEY (id);


--
-- Name: games_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY games
    ADD CONSTRAINT games_pkey PRIMARY KEY (id);


--
-- Name: users_games_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users_games
    ADD CONSTRAINT users_games_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: satchelgrant
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM satchelgrant;
GRANT ALL ON SCHEMA public TO satchelgrant;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

