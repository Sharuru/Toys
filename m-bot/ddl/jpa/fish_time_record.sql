--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

-- Started on 2017-06-10 19:27:01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 16426)
-- Name: fish_time_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE fish_time_record (
    id integer NOT NULL,
    user_id character varying NOT NULL,
    username character varying(255) NOT NULL,
    checkin_time timestamp with time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE fish_time_record OWNER TO postgres;

--
-- TOC entry 2127 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.id IS '记录ID';


--
-- TOC entry 2128 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.username IS '用户名';


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.checkin_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.checkin_time IS '出勤时间';


--
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.created_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.created_at IS '记录创建时间';


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN fish_time_record.updated_at; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN fish_time_record.updated_at IS '记录更新时间';

COMMENT ON COLUMN fish_time_record.user_id IS '用户ID';


--
-- TOC entry 185 (class 1259 OID 16424)
-- Name: fish_time_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fish_time_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fish_time_record_id_seq OWNER TO postgres;

--
-- TOC entry 2132 (class 0 OID 0)
-- Dependencies: 185
-- Name: fish_time_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fish_time_record_id_seq OWNED BY fish_time_record.id;


--
-- TOC entry 2001 (class 2604 OID 16429)
-- Name: fish_time_record id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fish_time_record ALTER COLUMN id SET DEFAULT nextval('fish_time_record_id_seq'::regclass);


--
-- TOC entry 2122 (class 0 OID 16426)
-- Dependencies: 186
-- Data for Name: fish_time_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY fish_time_record (id, username, checkin_time, created_at, updated_at, user_id) FROM stdin;
50	thisIsTestUserName1	2017-06-10 12:22:00+08	2017-06-10 18:48:56.742	2017-06-10 18:48:56.742	thisIsTestUserId1
100	thisIsTestUserName1	2017-10-10 12:22:00+08	2017-06-10 18:58:52.371	2017-06-10 18:58:52.371	thisIsTestUserId1
\.


--
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 185
-- Name: fish_time_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fish_time_record_id_seq', 2, true);


--
-- TOC entry 2003 (class 2606 OID 16431)
-- Name: fish_time_record fish_time_record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fish_time_record
    ADD CONSTRAINT fish_time_record_pkey PRIMARY KEY (id);


-- Completed on 2017-06-10 19:27:01

--
-- PostgreSQL database dump complete
--

