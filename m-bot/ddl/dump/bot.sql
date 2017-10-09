--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2017-06-27 11:42:57

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12393)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16410)
-- Name: bot_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bot_item (
    id integer NOT NULL,
    item_name character varying(255),
    item_description character varying(255),
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE bot_item OWNER TO postgres;

--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN bot_item.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_item.id IS '记录ID';


--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN bot_item.item_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_item.item_name IS '物品名';


--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN bot_item.item_description; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_item.item_description IS '物品描述';


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN bot_item.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_item.created_at IS '记录创建时间';


--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN bot_item.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_item.updated_at IS '记录更新时间';


--
-- TOC entry 187 (class 1259 OID 16408)
-- Name: bot_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bot_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bot_item_id_seq OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 187
-- Name: bot_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bot_item_id_seq OWNED BY bot_item.id;


--
-- TOC entry 190 (class 1259 OID 16421)
-- Name: bot_stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bot_stock (
    id integer NOT NULL,
    user_id character varying NOT NULL,
    item_id integer,
    item_count numeric(12,2) DEFAULT 0,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE bot_stock OWNER TO postgres;

--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.id IS '记录ID';


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.user_id IS '用户ID';


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.item_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.item_id IS '物品ID';


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.item_count; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.item_count IS '物品数量';


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.created_at IS '记录创建时间';


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN bot_stock.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bot_stock.updated_at IS '记录修改时间';


--
-- TOC entry 189 (class 1259 OID 16419)
-- Name: bot_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bot_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bot_stock_id_seq OWNER TO postgres;

--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 189
-- Name: bot_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bot_stock_id_seq OWNED BY bot_stock.id;


--
-- TOC entry 186 (class 1259 OID 16389)
-- Name: fish_time_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE fish_time_record (
    id integer NOT NULL,
    user_id character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    checkin_time timestamp(6) with time zone,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE fish_time_record OWNER TO postgres;

--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.id IS '记录ID';


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.user_id IS '用户ID';


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.username IS '用户名';


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.checkin_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.checkin_time IS '出勤时间';


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.created_at IS '记录创建时间';


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.updated_at IS '记录更新时间';


--
-- TOC entry 185 (class 1259 OID 16385)
-- Name: fish_time_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fish_time_record_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fish_time_record_id_seq OWNER TO postgres;

--
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 185
-- Name: fish_time_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fish_time_record_id_seq OWNED BY fish_time_record.id;


--
-- TOC entry 2021 (class 2604 OID 16413)
-- Name: bot_item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bot_item ALTER COLUMN id SET DEFAULT nextval('bot_item_id_seq'::regclass);


--
-- TOC entry 2022 (class 2604 OID 16424)
-- Name: bot_stock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bot_stock ALTER COLUMN id SET DEFAULT nextval('bot_stock_id_seq'::regclass);


--
-- TOC entry 2020 (class 2604 OID 16392)
-- Name: fish_time_record id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fish_time_record ALTER COLUMN id SET DEFAULT nextval('fish_time_record_id_seq'::regclass);


--
-- TOC entry 2151 (class 0 OID 16410)
-- Dependencies: 188
-- Data for Name: bot_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bot_item (id, item_name, item_description, created_at, updated_at) FROM stdin;
2	无色小晶块	ROLL 功能抽卡用的基础素材	2017-06-27 11:06:00	2017-06-27 11:06:02
3	金额	系统基础货币	2017-06-27 11:10:16	2017-06-27 11:10:18
\.


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 187
-- Name: bot_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bot_item_id_seq', 3, true);


--
-- TOC entry 2153 (class 0 OID 16421)
-- Dependencies: 190
-- Data for Name: bot_stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bot_stock (id, user_id, item_id, item_count, created_at, updated_at) FROM stdin;
3	6m9yaea4cfrrfgcdo7c3su9w6r	2	100.00	2017-06-27 11:11:46	2017-06-27 11:11:48
1	6m9yaea4cfrrfgcdo7c3su9w6r	3	1245000.99	2017-06-27 11:11:33	2017-06-27 11:11:35
\.


--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 189
-- Name: bot_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bot_stock_id_seq', 3, true);


--
-- TOC entry 2149 (class 0 OID 16389)
-- Dependencies: 186
-- Data for Name: fish_time_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY fish_time_record (id, user_id, username, checkin_time, created_at, updated_at) FROM stdin;
150	6m9yaea4cfrrfgcdo7c3su9w6r	test	2017-06-14 01:05:00+00	2017-06-14 11:54:31.907	2017-06-14 13:45:33.889
200	6m9yaea4cfrrfgcdo7c3su9w6r	sharuru	2017-06-23 00:43:00+00	2017-06-23 06:47:43.631	2017-06-23 06:47:43.631
250	5wpq4e346fr6pddfp8ek7e9iba	lapapapingouin	2017-06-23 01:09:00+00	2017-06-23 09:01:12.205	2017-06-23 09:01:12.205
251	s57jcdun63gg3egw3d9rirfa9w	hqz	2017-06-23 01:00:00+00	2017-06-23 09:04:00.192	2017-06-23 09:04:00.192
252	i4rreoonrtbd5eynojnqd9sbpa	dawn	2017-06-23 01:13:00+00	2017-06-23 09:14:55.288	2017-06-23 09:14:55.288
253	sbtgoobeopfo5ytw9wcuzymbbo	zhangyi	2017-06-23 01:21:00+00	2017-06-23 09:17:39.998	2017-06-23 09:17:39.998
254	16i3iqx8n3f5icryecqw5mrwec	jjq_milk	2017-06-23 01:00:00+00	2017-06-23 09:56:50.37	2017-06-23 09:56:50.37
255	6m9yaea4cfrrfgcdo7c3su9w6r	sharuru	2017-06-26 00:43:00+00	2017-06-26 00:55:13.369	2017-06-26 00:55:13.369
256	aupmbyaf6igeuyp66jefoj71jh	w..	2017-06-26 01:00:00+00	2017-06-26 01:06:17.773	2017-06-26 01:06:17.773
257	s57jcdun63gg3egw3d9rirfa9w	hqz	2017-06-26 01:00:00+00	2017-06-26 01:07:10.76	2017-06-26 01:07:10.76
258	i4rreoonrtbd5eynojnqd9sbpa	dawn	2017-06-26 01:08:00+00	2017-06-26 01:33:43.577	2017-06-26 01:33:43.577
259	16i3iqx8n3f5icryecqw5mrwec	jjq_milk	2017-06-26 01:00:00+00	2017-06-26 03:12:18.922	2017-06-26 03:12:18.922
260	8yufqh99kfnnuqoofwjanu35bh	wyfsama	2017-06-26 01:00:00+00	2017-06-26 07:07:54.979	2017-06-26 07:07:54.979
261	9bzwrwzjojrrdnjhrketuw5fxo	zjj	2017-06-26 01:00:00+00	2017-06-26 07:09:09.252	2017-06-26 07:09:09.252
300	kf3q5rm1st8fbdb7gjiy1ypazw	akisec.	2017-06-26 01:00:00+00	2017-06-26 09:43:39.341	2017-06-26 09:43:39.341
350	6m9yaea4cfrrfgcdo7c3su9w6r	sharuru	2017-06-27 00:59:00+00	2017-06-27 01:21:03.205	2017-06-27 01:21:25.524
351	s57jcdun63gg3egw3d9rirfa9w	hqz	2017-06-27 01:00:00+00	2017-06-27 01:32:44.895	2017-06-27 01:32:44.895
352	i4rreoonrtbd5eynojnqd9sbpa	dawn	2017-06-27 01:13:00+00	2017-06-27 01:35:48.043	2017-06-27 01:35:48.043
353	16i3iqx8n3f5icryecqw5mrwec	jjq_milk	2017-06-27 01:04:00+00	2017-06-27 01:41:26.978	2017-06-27 01:41:43.86
354	aupmbyaf6igeuyp66jefoj71jh	w..	2017-06-27 01:19:00+00	2017-06-27 01:46:26.679	2017-06-27 01:46:26.679
\.


--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 185
-- Name: fish_time_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fish_time_record_id_seq', 7, true);


--
-- TOC entry 2027 (class 2606 OID 16418)
-- Name: bot_item bot_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bot_item
    ADD CONSTRAINT bot_item_pkey PRIMARY KEY (id);


--
-- TOC entry 2029 (class 2606 OID 16430)
-- Name: bot_stock bot_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bot_stock
    ADD CONSTRAINT bot_stock_pkey PRIMARY KEY (id);


--
-- TOC entry 2025 (class 2606 OID 16405)
-- Name: fish_time_record fish_time_record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fish_time_record
    ADD CONSTRAINT fish_time_record_pkey PRIMARY KEY (id);


--
-- TOC entry 2030 (class 2606 OID 16431)
-- Name: bot_stock bot_stock_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bot_stock
    ADD CONSTRAINT bot_stock_item_id_fkey FOREIGN KEY (item_id) REFERENCES bot_item(id);


-- Completed on 2017-06-27 11:42:57

--
-- PostgreSQL database dump complete
--

