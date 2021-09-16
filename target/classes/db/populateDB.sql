SET DATABASE SQL SYNTAX PGS TRUE;

INSERT INTO users (id, email, proto_data)
VALUES
(
0, 'test@yandex.ru', cast (10, 4, 73, 103, 111, 114, 18, 4, 73, 103, 111, 114, 24, 24, 32, -125, -39, 3, 45, 0, 0, -105, 66, 53, 0, 0, 52, 67, 58, 6, 49, 50, 51, 52, 61, 41, 66, 4, 85, 83, 69, 82) AS BYTEA[]
);

INSERT INTO user_roles (role, role_id) VALUES
('USER', 0);
