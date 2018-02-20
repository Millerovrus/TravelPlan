CREATE SEQUENCE edge_id_seq;

CREATE SEQUENCE hibernate_sequence;

CREATE SEQUENCE route_id_seq;

CREATE SEQUENCE user_id_seq;

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
  is_admin          BOOLEAN      NOT NULL
);
CREATE TABLE routes
(
  id                INTEGER      NOT NULL
    CONSTRAINT routes_pkey
    PRIMARY KEY,
  creation_date     TIMESTAMP    NOT NULL,
  start_point       VARCHAR(255) NOT NULL,
  destination_point VARCHAR(255) NOT NULL,
  route_type        SMALLINT      NOT NULL,
  cost              DOUBLE PRECISION NOT NULL,
  duration          DOUBLE PRECISION NOT NULL,
  distance          DOUBLE PRECISION NOT NULL,
  user_id           INTEGER
    CONSTRAINT fktn5l1ci7sxbp52akvblqjg4jm
    REFERENCES users
);

CREATE TABLE edges
(
  id                INTEGER          NOT NULL
    CONSTRAINT edges_pkey
    PRIMARY KEY,  
  creation_date     TIMESTAMP        NOT NULL,  
  start_point       VARCHAR(255)     NOT NULL,
  destination_point VARCHAR(255)     NOT NULL,
  distance          DOUBLE PRECISION,
  duration          DOUBLE PRECISION NOT NULL,
  start_date        TIMESTAMP        NOT NULL,
  end_date          TIMESTAMP        NOT NULL,  
  cost              DOUBLE PRECISION,
  currency          VARCHAR(255),  
  transport_type    VARCHAR(255) NOT NULL,
  edge_type         SMALLINT,
  start_point_iata_code VARCHAR(3),
  end_point_iata_code VARCHAR(3),
  edge_order		SMALLINT NOT NULL,
  route_id   INTEGER NOT NULL
    CONSTRAINT fko41lp6h2i31x6jdo8e2xd532w
    REFERENCES routes
);

CREATE TABLE airports
(
  id           VARCHAR(255) NOT NULL
    CONSTRAINT airports_pkey
    PRIMARY KEY,
  code         VARCHAR(255),
  type         VARCHAR(255),
  name         VARCHAR(255),
  latitude     DOUBLE PRECISION,
  longitude    DOUBLE PRECISION,
  city_name    VARCHAR(255),
  city_code    VARCHAR(255),
  country_name VARCHAR(255),
  country_code VARCHAR(255),  
  timezone     VARCHAR(255)
);
