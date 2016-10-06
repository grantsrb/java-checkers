--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

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
-- Name: checkers; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE checkers (
    id integer NOT NULL,
    type integer,
    columnposition integer,
    rowposition integer,
    gameid integer
);


ALTER TABLE checkers OWNER TO "Guest";

--
-- Name: checkers_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE checkers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkers_id_seq OWNER TO "Guest";

--
-- Name: checkers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE checkers_id_seq OWNED BY checkers.id;


--
-- Name: games; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE games (
    id integer NOT NULL,
    playercount integer,
    playerturn integer,
    saved boolean,
    takenredcheckers integer,
    takenwhitecheckers integer
);


ALTER TABLE games OWNER TO "Guest";

--
-- Name: games_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE games_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE games_id_seq OWNER TO "Guest";

--
-- Name: games_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE games_id_seq OWNED BY games.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    username character varying,
    password character varying,
    gameswon integer,
    gameslost integer
);


ALTER TABLE users OWNER TO "Guest";

--
-- Name: users_games; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE users_games (
    id integer NOT NULL,
    userid integer,
    gameid integer
);


ALTER TABLE users_games OWNER TO "Guest";

--
-- Name: users_games_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_games_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_games_id_seq OWNER TO "Guest";

--
-- Name: users_games_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_games_id_seq OWNED BY users_games.id;


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY checkers ALTER COLUMN id SET DEFAULT nextval('checkers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY games ALTER COLUMN id SET DEFAULT nextval('games_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users_games ALTER COLUMN id SET DEFAULT nextval('users_games_id_seq'::regclass);


--
-- Data for Name: checkers; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY checkers (id, type, columnposition, rowposition, gameid) FROM stdin;
4713	1	4	5	200
4835	1	6	5	205
4720	4	3	4	200
4537	1	1	0	193
4538	1	3	0	193
4540	1	7	0	193
4541	1	0	1	193
4542	1	2	1	193
4543	1	4	1	193
4545	1	1	2	193
4549	2	0	5	193
4552	2	6	5	193
4554	2	3	6	193
4555	2	5	6	193
4556	2	7	6	193
4557	2	0	7	193
4558	2	2	7	193
4559	2	4	7	193
4560	2	6	7	193
4705	1	1	0	200
2819	1	5	4	121
2827	2	4	5	121
4707	1	5	0	200
2820	1	6	3	121
4708	1	7	0	200
4709	1	0	1	200
4712	1	6	1	200
4716	1	7	2	200
4717	2	0	5	200
4825	1	1	0	205
4723	2	5	6	200
4724	2	7	6	200
4726	2	2	7	200
4727	2	4	7	200
2809	1	1	0	121
2810	1	3	0	121
2811	1	5	0	121
2812	1	7	0	121
4826	1	3	0	205
2815	1	4	1	121
2816	1	6	1	121
4728	2	6	7	200
4827	1	5	0	205
4828	1	7	0	205
4829	1	0	1	205
2825	2	1	6	121
2826	2	3	6	121
2828	2	7	6	121
2829	2	0	7	121
2830	2	2	7	121
2831	2	4	7	121
2832	2	6	7	121
4830	1	2	1	205
4831	1	4	1	205
4714	1	6	5	200
4832	1	6	1	205
4718	2	0	3	200
4721	2	1	4	200
4725	2	1	6	200
4722	2	2	5	200
4833	1	1	2	205
4548	1	6	3	193
4539	1	7	2	193
4836	1	7	2	205
4837	2	0	5	205
4839	2	4	5	205
4841	2	1	6	205
4842	2	3	6	205
4843	2	5	6	205
4844	2	7	6	205
4845	2	0	7	205
4846	2	2	7	205
4847	2	4	7	205
4848	2	6	7	205
4838	2	3	2	205
2823	2	1	2	121
2814	1	1	4	121
\.


--
-- Name: checkers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('checkers_id_seq', 4848, true);


--
-- Data for Name: games; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY games (id, playercount, playerturn, saved, takenredcheckers, takenwhitecheckers) FROM stdin;
121	2	1	t	\N	\N
205	2	2	f	1	1
200	2	1	t	\N	\N
193	2	2	t	\N	\N
\.


--
-- Name: games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('games_id_seq', 205, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, username, password, gameswon, gameslost) FROM stdin;
1	YoungDro	123	\N	\N
2	cgrahams	1234gogo	\N	\N
3	chance	chance	\N	\N
4	mungus	qwert	\N	\N
\.


--
-- Data for Name: users_games; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users_games (id, userid, gameid) FROM stdin;
15	2	121
16	2	193
17	3	200
\.


--
-- Name: users_games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_games_id_seq', 17, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 4, true);


--
-- Name: checkers_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY checkers
    ADD CONSTRAINT checkers_pkey PRIMARY KEY (id);


--
-- Name: games_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY games
    ADD CONSTRAINT games_pkey PRIMARY KEY (id);


--
-- Name: users_games_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY users_games
    ADD CONSTRAINT users_games_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

