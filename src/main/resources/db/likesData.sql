INSERT INTO likes_tb values (1,11, '2024-08-01');
INSERT INTO likes_tb values (2,11, '2024-08-01');
INSERT INTO likes_tb values (3,11, '2024-08-01');
INSERT INTO likes_tb values (4,11, '2024-08-01');
INSERT INTO likes_tb values (5,11, '2024-08-01');
INSERT INTO likes_tb values (6,11, '2024-08-01');
INSERT INTO likes_tb values (7,11, '2024-08-01');
INSERT INTO likes_tb values (8,11, '2024-08-01');
INSERT INTO likes_tb values (9,11, '2024-08-01');
INSERT INTO likes_tb values (10,11, '2024-08-01');
INSERT INTO likes_tb values (11,11, '2024-08-01');
INSERT INTO likes_tb values (12,11, '2024-08-01');
update book_tb set likes = 12 where id = 11;

INSERT INTO likes_tb values (1,22, '2024-08-01');
INSERT INTO likes_tb values (2,22, '2024-08-01');
INSERT INTO likes_tb values (3,22, '2024-08-01');
INSERT INTO likes_tb values (4,22, '2024-08-01');
INSERT INTO likes_tb values (5,22, '2024-08-01');
INSERT INTO likes_tb values (6,22, '2024-08-01');
INSERT INTO likes_tb values (7,22, '2024-08-01');
INSERT INTO likes_tb values (8,22, '2024-08-01');
INSERT INTO likes_tb values (9,22, '2024-08-01');
INSERT INTO likes_tb values (10,22, '2024-08-01');
update book_tb set likes = 10 where id = 22;

INSERT INTO likes_tb values (1,33, '2024-08-01');
INSERT INTO likes_tb values (2,33, '2024-08-01');
INSERT INTO likes_tb values (3,33, '2024-08-01');
INSERT INTO likes_tb values (4,33, '2024-08-01');
INSERT INTO likes_tb values (5,33, '2024-08-01');
INSERT INTO likes_tb values (6,33, '2024-08-01');
INSERT INTO likes_tb values (7,33, '2024-08-01');
INSERT INTO likes_tb values (8,33, '2024-08-01');
update book_tb set likes = 8 where id = 33;

INSERT INTO likes_tb values (1,44, '2024-08-01');
INSERT INTO likes_tb values (2,44, '2024-08-01');
INSERT INTO likes_tb values (3,44, '2024-08-01');
INSERT INTO likes_tb values (4,44, '2024-08-01');
INSERT INTO likes_tb values (5,44, '2024-08-01');
INSERT INTO likes_tb values (6,44, '2024-08-01');
update book_tb set likes = 6 where id = 44;

INSERT INTO likes_tb values (1,55, '2024-08-01');
INSERT INTO likes_tb values (2,55, '2024-08-01');
INSERT INTO likes_tb values (3,55, '2024-08-01');
INSERT INTO likes_tb values (4,55, '2024-08-01');
update book_tb set likes = 4 where id = 55;

update book_tb set score = 10 where id = 10;
update book_tb set score = 8.5 where id = 100;
update book_tb set score = 6.6 where id = 207;
update book_tb set score = 5.7 where id = 315;
update book_tb set score = 5.5 where id = 516;