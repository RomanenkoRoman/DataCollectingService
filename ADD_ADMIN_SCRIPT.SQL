CREATE TABLE IF NOT EXISTS  public.admin (
  adminid INTEGER PRIMARY KEY NOT NULL,
  login CHARACTER VARYING(255),
  password CHARACTER VARYING(255)
);

INSERT INTO admin (adminid, login, password)
VALUES (99999,'admin','admin')