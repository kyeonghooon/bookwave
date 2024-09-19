INSERT INTO likes_tb values (1,1, '2024-08-01');
INSERT INTO likes_tb values (2,1, '2024-08-01');
INSERT INTO likes_tb values (3,1, '2024-08-01');
INSERT INTO likes_tb values (4,1, '2024-08-01');
INSERT INTO likes_tb values (5,1, '2024-08-01');
INSERT INTO likes_tb values (6,1, '2024-08-01');
INSERT INTO likes_tb values (7,1, '2024-08-01');
INSERT INTO likes_tb values (8,1, '2024-08-01');
INSERT INTO likes_tb values (9,1, '2024-08-01');
INSERT INTO likes_tb values (10,1, '2024-08-01');
INSERT INTO likes_tb values (11,1, '2024-08-01');
INSERT INTO likes_tb values (12,1, '2024-08-01');
update book_tb set likes = 12 where id = 1;

INSERT INTO likes_tb values (1,2, '2024-08-01');
INSERT INTO likes_tb values (2,2, '2024-08-01');
INSERT INTO likes_tb values (3,2, '2024-08-01');
INSERT INTO likes_tb values (4,2, '2024-08-01');
INSERT INTO likes_tb values (5,2, '2024-08-01');
INSERT INTO likes_tb values (6,2, '2024-08-01');
INSERT INTO likes_tb values (7,2, '2024-08-01');
INSERT INTO likes_tb values (8,2, '2024-08-01');
INSERT INTO likes_tb values (9,2, '2024-08-01');
INSERT INTO likes_tb values (10,2, '2024-08-01');
update book_tb set likes = 10 where id = 2;

INSERT INTO likes_tb values (1,3, '2024-08-01');
INSERT INTO likes_tb values (2,3, '2024-08-01');
INSERT INTO likes_tb values (3,3, '2024-08-01');
INSERT INTO likes_tb values (4,3, '2024-08-01');
INSERT INTO likes_tb values (5,3, '2024-08-01');
INSERT INTO likes_tb values (6,3, '2024-08-01');
INSERT INTO likes_tb values (7,3, '2024-08-01');
INSERT INTO likes_tb values (8,3, '2024-08-01');
update book_tb set likes = 8 where id = 3;

INSERT INTO likes_tb values (1,4, '2024-08-01');
INSERT INTO likes_tb values (2,4, '2024-08-01');
INSERT INTO likes_tb values (3,4, '2024-08-01');
INSERT INTO likes_tb values (4,4, '2024-08-01');
INSERT INTO likes_tb values (5,4, '2024-08-01');
INSERT INTO likes_tb values (6,4, '2024-08-01');
update book_tb set likes = 6 where id = 4;

INSERT INTO likes_tb values (1,5, '2024-08-01');
INSERT INTO likes_tb values (2,5, '2024-08-01');
INSERT INTO likes_tb values (3,5, '2024-08-01');
INSERT INTO likes_tb values (4,5, '2024-08-01');
update book_tb set likes = 4 where id = 5;

update book_tb set score = 10 where id = 10;
update book_tb set score = 8.5 where id = 100;
update book_tb set score = 6.6 where id = 207;
update book_tb set score = 5.7 where id = 315;
update book_tb set score = 5.5 where id = 516;