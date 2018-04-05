DROP TABLE IF EXISTS ROUTES CASCADE;
DROP SEQUENCE public.route_id_seq RESTRICT;
DROP TABLE IF EXISTS USERS CASCADE;
DROP SEQUENCE public.user_id_seq RESTRICT;

CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE route_id_seq;

CREATE TABLE users
(
  id                INTEGER      NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  first_name        VARCHAR(50)  NOT NULL,
  last_name         VARCHAR(50)  NOT NULL,
  email             VARCHAR(255) UNIQUE,
  birth_date        DATE,
  password          VARCHAR(255) NOT NULL,
  registration_date TIMESTAMP    NOT NULL,
  is_admin          BOOLEAN      NOT NULL,
  avatar            VARCHAR(255) DEFAULT 'img/man-user.png'
);

CREATE TABLE routes
(
  id                INTEGER      NOT NULL
    CONSTRAINT routes_pkey
    PRIMARY KEY,
  creation_date     TIMESTAMP    NOT NULL,
  start_point       VARCHAR(255) NOT NULL,
  destination_point VARCHAR(255) NOT NULL,
  cost              DOUBLE PRECISION NOT NULL,
  duration          DOUBLE PRECISION NOT NULL,
  user_id           INTEGER
    CONSTRAINT fktn5l1ci7sxbp52akvblqjg4jm
    REFERENCES users
);