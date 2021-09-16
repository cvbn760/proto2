DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS seq;

CREATE SEQUENCE seq START WITH 0 increment by 1;

SET DATABASE SQL SYNTAX PGS TRUE;

CREATE TABLE user_roles
(
    role_id INTEGER GENERATED BY DEFAULT AS SEQUENCE seq PRIMARY KEY,
    role    VARCHAR(20)                         NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (role_id, role)
);

CREATE TABLE users
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0, INCREMENT BY 1) PRIMARY KEY,
    email            VARCHAR(30)                NOT NULL,
    proto_data       BYTEA                      NOT NULL,
    CONSTRAINT users_idx UNIQUE (email)
);

ALTER TABLE user_roles ADD FOREIGN KEY (role_id) REFERENCES users(id) ON DELETE CASCADE;
