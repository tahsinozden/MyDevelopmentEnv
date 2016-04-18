-- Table: "AppSchema".employee

DROP TABLE "AppSchema".employee;

CREATE TABLE "AppSchema".employee
(
  id integer,
  name text,
  surname text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "AppSchema".employee
  OWNER TO postgres;

  
-- Table: "AppSchema".notification_registry

DROP TABLE "AppSchema".notification_registry;

CREATE TABLE "AppSchema".notification_registry
(
  rec_id integer,
  user_name character varying(128),
  src_currency_code character varying(128),
  dst_currency_code character varying(128),
  status character varying(32),
  notic_period character varying(32),
  threshold_value double precision,
  threshold_type character varying(32),
  last_notic_send_date timestamp without time zone,
  email_send_status character varying(255),
  notification_email character varying(255),
  current_rate double precision
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "AppSchema".notification_registry
  OWNER TO postgres;



-- Table: "AppSchema".user_sessions

DROP TABLE "AppSchema".user_sessions;

CREATE TABLE "AppSchema".user_sessions
(
  session_id character varying(1024) NOT NULL,
  user_name character varying(128),
  session_start_time timestamp without time zone,
  session_exp_time timestamp without time zone,
  CONSTRAINT user_sessions_pkey PRIMARY KEY (session_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "AppSchema".user_sessions
  OWNER TO postgres;


-- Table: "AppSchema".users

DROP TABLE "AppSchema".users;

CREATE TABLE "AppSchema".users
(
  user_name character varying(128) NOT NULL,
  password character varying(128),
  activation_date date,
  expiration_date date,
  status_flag integer,
  CONSTRAINT users_pkey PRIMARY KEY (user_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "AppSchema".users
  OWNER TO postgres;

  
-- Table: "AppSchema".currencies

DROP TABLE "AppSchema".currencies;

CREATE TABLE "AppSchema".currencies
(
  code character varying(255) NOT NULL,
  last_update_time timestamp without time zone,
  name character varying(255),
  rate double precision,
  CONSTRAINT currencies_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "AppSchema".currencies
  OWNER TO postgres;

  
--drop table user_sessions;
--create table user_sessions(
--	session_id varchar(1024) primary key,
--	user_name varchar(128),
--	session_start_time timestamp,
--	session_exp_time timestamp
--
--)
--
--drop table notification_registry;
--create table notification_registry (
--	rec_id int,
--	user_name varchar(128),
--	src_currency_code varchar(128),
--	dst_currency_code varchar(128),
--	status varchar(32),
--	notic_period  varchar(32),
--	threshold_value float
--
--)