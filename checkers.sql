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
    saved boolean
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
1393	1	1	0	59
1394	1	3	0	59
1395	1	5	0	59
1396	1	7	0	59
1397	1	0	1	59
1398	1	2	1	59
1399	1	4	1	59
1400	1	6	1	59
1401	1	1	2	59
1402	1	3	2	59
1403	1	5	2	59
1404	1	7	2	59
1405	2	0	5	59
1406	2	2	5	59
1407	2	4	5	59
1408	2	6	5	59
1409	2	1	6	59
1410	2	3	6	59
1411	2	5	6	59
1412	2	7	6	59
1413	2	0	7	59
1414	2	2	7	59
1415	2	4	7	59
1416	2	6	7	59
1417	1	1	0	60
1418	1	3	0	60
1419	1	5	0	60
1420	1	7	0	60
1421	1	0	1	60
1422	1	2	1	60
1423	1	4	1	60
1424	1	6	1	60
1425	1	1	2	60
1427	1	5	2	60
1428	1	7	2	60
1429	2	0	5	60
1431	2	4	5	60
1432	2	6	5	60
1433	2	1	6	60
1434	2	3	6	60
1435	2	5	6	60
1436	2	7	6	60
1437	2	0	7	60
1438	2	2	7	60
1439	2	4	7	60
1440	2	6	7	60
1430	2	3	4	60
1426	1	4	3	60
1441	1	1	0	61
1442	1	3	0	61
1443	1	5	0	61
1444	1	7	0	61
1445	1	0	1	61
1446	1	2	1	61
1447	1	4	1	61
1448	1	6	1	61
1449	1	1	2	61
1452	1	7	2	61
1453	2	0	5	61
1585	1	1	0	67
1455	2	4	5	61
1456	2	6	5	61
1457	2	1	6	61
1458	2	3	6	61
1459	2	5	6	61
1460	2	7	6	61
1461	2	0	7	61
1462	2	2	7	61
1463	2	4	7	61
1464	2	6	7	61
1777	1	1	0	75
1589	1	0	1	67
1450	1	1	4	61
1451	1	2	5	61
1465	1	1	0	62
1466	1	3	0	62
1467	1	5	0	62
1468	1	7	0	62
1469	1	0	1	62
1470	1	2	1	62
1473	1	1	2	62
1474	1	3	2	62
1477	2	0	5	62
1478	2	2	5	62
1593	1	1	2	67
1481	2	1	6	62
1482	2	3	6	62
1484	2	7	6	62
1485	2	0	7	62
1486	2	2	7	62
1487	2	4	7	62
1488	2	6	7	62
1530	2	3	2	64
1601	2	1	6	67
1603	2	5	6	67
1475	1	3	4	62
1476	1	6	5	62
1471	1	5	2	62
1472	1	7	2	62
1483	2	4	5	62
1489	1	1	0	63
1490	1	3	0	63
1491	1	5	0	63
1492	1	7	0	63
1493	1	0	1	63
1494	1	2	1	63
1495	1	4	1	63
1496	1	6	1	63
1497	1	1	2	63
1498	1	3	2	63
1499	1	5	2	63
1500	1	7	2	63
1501	2	0	5	63
1503	2	4	5	63
1504	2	6	5	63
1505	2	1	6	63
1506	2	3	6	63
1507	2	5	6	63
1508	2	7	6	63
1509	2	0	7	63
1510	2	2	7	63
1511	2	4	7	63
1512	2	6	7	63
1605	2	0	7	67
1502	2	4	3	63
1513	1	1	0	64
1514	1	3	0	64
1515	1	5	0	64
1516	1	7	0	64
1517	1	0	1	64
1607	2	4	7	67
1519	1	4	1	64
1520	1	6	1	64
1595	1	3	4	67
1591	1	5	2	67
1523	1	5	2	64
1524	1	7	2	64
1599	2	6	3	67
1597	2	1	4	67
1527	2	4	5	64
1528	2	6	5	64
1529	2	1	6	64
1780	1	7	0	75
1783	1	4	1	75
1633	1	1	0	69
1609	3	2	7	68
1615	3	6	7	68
1625	4	1	0	68
1617	3	2	5	68
1637	1	0	1	69
1639	1	4	1	69
1641	1	1	2	69
1645	2	0	5	69
1649	2	1	6	69
1531	2	5	6	64
1532	2	7	6	64
1533	2	0	7	64
1534	2	2	7	64
1535	2	4	7	64
1536	2	6	7	64
1537	1	1	0	65
1538	1	3	0	65
1539	1	5	0	65
1540	1	7	0	65
1541	1	0	1	65
1542	1	2	1	65
1543	1	4	1	65
1544	1	6	1	65
1545	1	1	2	65
1546	1	3	2	65
1547	1	5	2	65
1548	1	7	2	65
1549	2	0	5	65
1550	2	2	5	65
1551	2	4	5	65
1552	2	6	5	65
1553	2	1	6	65
1554	2	3	6	65
1555	2	5	6	65
1556	2	7	6	65
1557	2	0	7	65
1558	2	2	7	65
1559	2	4	7	65
1560	2	6	7	65
1561	1	1	0	66
1586	1	3	0	67
1590	1	2	1	67
1565	1	0	1	66
1778	1	3	0	75
1596	1	7	2	67
1781	1	0	1	75
1602	2	3	6	67
1606	2	2	7	67
1608	2	6	7	67
1784	1	6	1	75
1713	1	2	3	72
1786	1	3	2	75
1667	1	4	3	70
1604	2	7	4	67
1588	1	6	1	67
1788	1	7	2	75
1790	2	2	5	75
1792	2	6	5	75
1598	4	5	0	67
1592	1	6	5	67
1794	2	3	6	75
1612	1	7	0	68
1616	1	6	1	68
1796	2	7	6	75
1798	2	2	7	75
1800	2	6	7	75
1802	1	3	0	76
1719	2	3	4	72
1671	4	5	0	70
1664	1	7	4	70
1571	3	0	7	66
1804	1	7	0	76
1806	1	2	1	76
1626	2	7	2	68
1808	1	6	1	76
1810	1	3	2	76
1812	1	7	2	76
1814	2	2	5	76
1816	2	6	5	76
1818	2	3	6	76
1820	2	7	6	76
1822	2	2	7	76
1824	2	6	7	76
1826	1	3	0	77
1693	2	0	5	71
1828	1	7	0	77
1614	3	0	7	68
1624	4	3	0	68
1697	2	1	6	71
1698	2	3	6	71
1632	4	5	0	68
1628	2	4	1	68
1634	1	3	0	69
1636	1	7	0	69
1638	1	2	1	69
1642	1	3	2	69
1830	1	2	1	77
1646	2	2	5	69
1650	2	3	6	69
1651	2	5	6	69
1652	2	7	6	69
1653	2	0	7	69
1654	2	2	7	69
1655	2	4	7	69
1656	2	6	7	69
1832	1	6	1	77
1700	2	7	6	71
1701	2	0	7	71
1702	2	2	7	71
1640	1	4	3	69
1703	2	4	7	71
1648	4	5	0	69
1657	1	1	0	70
1572	3	6	5	66
1658	1	3	0	70
1566	3	3	6	66
1834	1	3	2	77
1660	1	7	0	70
1661	1	0	1	70
1662	1	2	1	70
1663	1	4	1	70
1665	1	1	2	70
1666	1	3	2	70
1836	1	7	2	77
1669	2	0	5	70
1670	2	2	5	70
1838	2	2	5	77
1673	2	1	6	70
1674	2	3	6	70
1675	2	5	6	70
1676	2	7	6	70
1677	2	0	7	70
1678	2	2	7	70
1679	2	4	7	70
1680	2	6	7	70
1704	2	6	7	71
1705	1	0	1	72
1717	4	1	0	72
1709	1	0	3	72
1840	2	6	5	77
1842	2	3	6	77
1844	2	7	6	77
1846	2	2	7	77
1848	2	6	7	77
1850	1	3	0	78
1699	4	1	0	71
1696	4	4	1	71
1695	4	4	5	71
1706	1	3	0	72
1707	1	5	0	72
1708	1	7	0	72
1711	1	4	1	72
1712	1	6	1	72
1714	1	3	2	72
1715	1	5	2	72
1716	1	7	2	72
1718	2	2	5	72
1720	2	6	5	72
1721	2	1	6	72
1722	2	3	6	72
1723	2	5	6	72
1724	2	7	6	72
1725	2	0	7	72
1726	2	2	7	72
1727	2	4	7	72
1728	2	6	7	72
1730	1	3	0	73
1731	1	5	0	73
1732	1	7	0	73
1735	1	4	1	73
1736	1	6	1	73
1738	1	3	2	73
1739	1	5	2	73
1740	1	7	2	73
1743	2	4	5	73
1744	2	6	5	73
1745	2	1	6	73
1746	2	3	6	73
1747	2	5	6	73
1748	2	7	6	73
1749	2	0	7	73
1750	2	2	7	73
1751	2	4	7	73
1752	2	6	7	73
1737	1	0	3	73
1734	1	1	2	73
1742	2	1	4	73
1729	1	2	1	73
1741	4	1	0	73
1773	2	0	7	74
1766	2	0	1	74
1769	2	2	3	74
1774	2	2	5	74
1770	4	4	7	74
1779	1	5	0	75
1782	1	2	1	75
1785	1	1	2	75
1787	1	5	2	75
1789	2	0	5	75
1791	2	4	5	75
1793	2	1	6	75
1795	2	5	6	75
1797	2	0	7	75
1767	4	1	0	74
1799	2	4	7	75
1801	1	1	0	76
1771	2	2	1	74
1803	1	5	0	76
1805	1	0	1	76
1807	1	4	1	76
1809	1	1	2	76
1811	1	5	2	76
1813	2	0	5	76
1815	2	4	5	76
1817	2	1	6	76
1819	2	5	6	76
1821	2	0	7	76
1823	2	4	7	76
1825	1	1	0	77
1827	1	5	0	77
1829	1	0	1	77
1831	1	4	1	77
1833	1	1	2	77
1835	1	5	2	77
1837	2	0	5	77
1839	2	4	5	77
1841	2	1	6	77
1843	2	5	6	77
1845	2	0	7	77
1847	2	4	7	77
1849	1	1	0	78
1851	1	5	0	78
1852	1	7	0	78
1853	1	0	1	78
1854	1	2	1	78
1855	1	4	1	78
1856	1	6	1	78
1858	1	3	2	78
1859	1	5	2	78
1860	1	7	2	78
1861	2	0	5	78
1863	2	4	5	78
1864	2	6	5	78
1865	2	1	6	78
1866	2	3	6	78
1867	2	5	6	78
1868	2	7	6	78
1869	2	0	7	78
1870	2	2	7	78
1871	2	4	7	78
1872	2	6	7	78
1862	2	1	4	78
1857	1	2	3	78
1873	1	1	0	79
1874	1	3	0	79
1875	1	5	0	79
1876	1	7	0	79
1877	1	0	1	79
1878	1	2	1	79
1879	1	4	1	79
1880	1	6	1	79
1881	1	1	2	79
1882	1	3	2	79
1883	1	5	2	79
1884	1	7	2	79
1885	2	0	5	79
1886	2	2	5	79
1887	2	4	5	79
1888	2	6	5	79
1889	2	1	6	79
1890	2	3	6	79
1891	2	5	6	79
1892	2	7	6	79
1893	2	0	7	79
1894	2	2	7	79
1895	2	4	7	79
1896	2	6	7	79
1898	1	3	0	80
1899	1	5	0	80
1900	1	7	0	80
1901	1	0	1	80
1903	1	4	1	80
1904	1	6	1	80
1907	1	5	2	80
1908	1	7	2	80
1909	2	0	5	80
1916	2	7	6	80
1917	2	0	7	80
1918	2	2	7	80
1920	2	6	7	80
1910	2	3	4	80
1911	4	1	0	80
1912	2	4	3	80
1914	2	4	5	80
1915	2	6	5	80
1902	1	1	4	80
1919	2	5	6	80
1905	3	4	7	80
1921	1	1	0	81
1922	1	3	0	81
1923	1	5	0	81
1924	1	7	0	81
1925	1	0	1	81
1926	1	2	1	81
1927	1	4	1	81
1928	1	6	1	81
1929	1	1	2	81
1932	1	7	2	81
1933	2	0	5	81
1934	2	2	5	81
1937	2	1	6	81
1938	2	3	6	81
1939	2	5	6	81
1940	2	7	6	81
1941	2	0	7	81
1942	2	2	7	81
1943	2	4	7	81
1944	2	6	7	81
1935	2	3	4	81
1930	1	4	3	81
1936	2	5	2	81
1945	1	1	0	82
1946	1	3	0	82
1947	1	5	0	82
1948	1	7	0	82
1949	1	0	1	82
1950	1	2	1	82
1951	1	4	1	82
1952	1	6	1	82
1953	1	1	2	82
1954	1	3	2	82
1955	1	5	2	82
1956	1	7	2	82
1957	2	0	5	82
1958	2	2	5	82
1959	2	4	5	82
1960	2	6	5	82
1961	2	1	6	82
1962	2	3	6	82
1963	2	5	6	82
1964	2	7	6	82
1965	2	0	7	82
1966	2	2	7	82
1967	2	4	7	82
1968	2	6	7	82
1969	1	1	0	83
1970	1	3	0	83
1971	1	5	0	83
1972	1	7	0	83
1973	1	0	1	83
1974	1	2	1	83
1975	1	4	1	83
1976	1	6	1	83
1978	1	3	2	83
1979	1	5	2	83
1980	1	7	2	83
1981	2	0	5	83
1983	2	4	5	83
1984	2	6	5	83
1985	2	1	6	83
1986	2	3	6	83
1987	2	5	6	83
1988	2	7	6	83
1989	2	0	7	83
1990	2	2	7	83
1991	2	4	7	83
1992	2	6	7	83
1982	2	1	2	83
1993	1	1	0	84
1994	1	3	0	84
1995	1	5	0	84
1996	1	7	0	84
1997	1	0	1	84
1998	1	2	1	84
1999	1	4	1	84
2000	1	6	1	84
2001	1	1	2	84
2002	1	3	2	84
2003	1	5	2	84
2004	1	7	2	84
2005	2	0	5	84
2006	2	2	5	84
2007	2	4	5	84
2008	2	6	5	84
2009	2	1	6	84
2010	2	3	6	84
2011	2	5	6	84
2012	2	7	6	84
2013	2	0	7	84
2014	2	2	7	84
2015	2	4	7	84
2016	2	6	7	84
\.


--
-- Name: checkers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('checkers_id_seq', 2016, true);


--
-- Data for Name: games; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY games (id, playercount, playerturn, saved) FROM stdin;
82	2	2	\N
83	2	1	\N
59	2	1	\N
60	2	2	\N
61	2	2	\N
62	2	2	\N
63	2	2	\N
84	2	2	\N
64	2	1	\N
65	2	2	\N
66	2	2	\N
74	2	1	\N
75	2	2	\N
76	2	2	\N
77	2	2	\N
67	2	2	\N
71	2	1	\N
78	1	2	\N
68	2	2	\N
79	2	2	\N
72	2	2	\N
69	2	2	\N
70	2	2	\N
73	2	1	\N
80	2	2	\N
81	2	1	\N
\.


--
-- Name: games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('games_id_seq', 84, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, username, password, gameswon, gameslost) FROM stdin;
1	YoungDro	123	\N	\N
2	cgrahams	1234gogo	\N	\N
3	chance	chance	\N	\N
\.


--
-- Data for Name: users_games; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users_games (id, userid, gameid) FROM stdin;
\.


--
-- Name: users_games_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_games_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 3, true);


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

