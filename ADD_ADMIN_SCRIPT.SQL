CREATE DATABASE play
WITH
OWNER = postgres
ENCODING = 'UTF8'
LC_COLLATE = 'Russian_Russia.1251'
LC_CTYPE = 'Russian_Russia.1251'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS  public.admin (
  adminid INTEGER PRIMARY KEY NOT NULL,
  login CHARACTER VARYING(255),
  password CHARACTER VARYING(255)
);

INSERT INTO admin (adminid, login, password)
VALUES (99999,'admin','admin')
