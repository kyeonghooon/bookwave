-- 샘플 데이터 sql
insert into lend_tb(user_id, book_id, status, lend_date, return_date, returned_date) values(1, 15, 0, now(), dateadd('DAY', 7, now()), null);
insert into lend_tb(user_id, book_id, status, lend_date, return_date, returned_date) values(1, 16, 0, now(), dateadd('DAY', 7, now()), null);


