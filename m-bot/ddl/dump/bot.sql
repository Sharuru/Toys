PGDMP                         u            bot    9.6.1    9.6.1     [           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            \           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            ]           1262    21766    bot    DATABASE     �   CREATE DATABASE bot WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Japanese_Japan.932' LC_CTYPE = 'Japanese_Japan.932';
    DROP DATABASE bot;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            ^           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12387    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            _           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    29967    fish_time_record    TABLE     +  CREATE TABLE fish_time_record (
    id integer NOT NULL,
    user_id character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    checkin_time timestamp with time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);
 $   DROP TABLE public.fish_time_record;
       public         postgres    false    3            `           0    0    COLUMN fish_time_record.id    COMMENT     5   COMMENT ON COLUMN fish_time_record.id IS '记录ID';
            public       postgres    false    185            a           0    0    COLUMN fish_time_record.user_id    COMMENT     :   COMMENT ON COLUMN fish_time_record.user_id IS '用户ID';
            public       postgres    false    185            b           0    0     COLUMN fish_time_record.username    COMMENT     <   COMMENT ON COLUMN fish_time_record.username IS '用户名';
            public       postgres    false    185            c           0    0 $   COLUMN fish_time_record.checkin_time    COMMENT     C   COMMENT ON COLUMN fish_time_record.checkin_time IS '出勤时间';
            public       postgres    false    185            d           0    0 "   COLUMN fish_time_record.created_at    COMMENT     G   COMMENT ON COLUMN fish_time_record.created_at IS '记录创建时间';
            public       postgres    false    185            e           0    0 "   COLUMN fish_time_record.updated_at    COMMENT     G   COMMENT ON COLUMN fish_time_record.updated_at IS '记录更新时间';
            public       postgres    false    185            �            1259    29973    fish_time_record_id_seq    SEQUENCE     y   CREATE SEQUENCE fish_time_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.fish_time_record_id_seq;
       public       postgres    false    185    3            f           0    0    fish_time_record_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE fish_time_record_id_seq OWNED BY fish_time_record.id;
            public       postgres    false    186            �            1259    29987 	   roll_user    TABLE     +  CREATE TABLE roll_user (
    id integer NOT NULL,
    user_id character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    user_amount numeric(12,2) DEFAULT 0.00,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL
);
    DROP TABLE public.roll_user;
       public         postgres    false    3            �            1259    29985    roll_user_id_seq    SEQUENCE     r   CREATE SEQUENCE roll_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.roll_user_id_seq;
       public       postgres    false    3    188            g           0    0    roll_user_id_seq    SEQUENCE OWNED BY     7   ALTER SEQUENCE roll_user_id_seq OWNED BY roll_user.id;
            public       postgres    false    187            �           2604    29975    fish_time_record id    DEFAULT     l   ALTER TABLE ONLY fish_time_record ALTER COLUMN id SET DEFAULT nextval('fish_time_record_id_seq'::regclass);
 B   ALTER TABLE public.fish_time_record ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    186    185            �           2604    29990    roll_user id    DEFAULT     ^   ALTER TABLE ONLY roll_user ALTER COLUMN id SET DEFAULT nextval('roll_user_id_seq'::regclass);
 ;   ALTER TABLE public.roll_user ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    187    188    188            U          0    29967    fish_time_record 
   TABLE DATA               `   COPY fish_time_record (id, user_id, username, checkin_time, created_at, updated_at) FROM stdin;
    public       postgres    false    185   �       h           0    0    fish_time_record_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('fish_time_record_id_seq', 3, true);
            public       postgres    false    186            X          0    29987 	   roll_user 
   TABLE DATA               X   COPY roll_user (id, user_id, username, user_amount, created_at, updated_at) FROM stdin;
    public       postgres    false    188   �       i           0    0    roll_user_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('roll_user_id_seq', 2, true);
            public       postgres    false    187            �           2606    29977 &   fish_time_record fish_time_record_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY fish_time_record
    ADD CONSTRAINT fish_time_record_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.fish_time_record DROP CONSTRAINT fish_time_record_pkey;
       public         postgres    false    185    185            �           2606    29996    roll_user roll_user_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY roll_user
    ADD CONSTRAINT roll_user_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.roll_user DROP CONSTRAINT roll_user_pkey;
       public         postgres    false    188    188            U   ]   x�U�;� �Nao$o]>��ƠX�oo�C&�.��s���y=S�v����v��ā|GPzȯI�W&'H�g�A��4�������      X   M   x�3�4˵�LLM4IN+*JKON�7O6..�,7+�,-N-�440�32�4204�50�54Q04�25�24�"f����� ;�     