INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_CLIENT');

INSERT INTO client (id, username, password) VALUES
    (1201, 'admin', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky'),
    (1202, 'uki', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky'),
    (1203, 'bob', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky'),
    (1204, 'peca', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky'),
    (1205, 'jova', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky'),
    (1206, 'ana', '$2a$10$av7zaFCEOUdda1qqXS1cVuL8V4Fzn7tWTHG47iU7Ah5Het2Nd19Ky');

INSERT INTO client_authority (client_id, authority_id) VALUES
    (1201, 1),
    (1202, 2),
    (1203, 2),
    (1204, 2),
    (1205, 2),
    (1206, 2);