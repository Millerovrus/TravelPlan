DROP TABLE IF EXISTS ROUTES CASCADE;
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