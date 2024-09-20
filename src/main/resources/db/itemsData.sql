-- 초기 필수 데이터
INSERT INTO page_tb (name) VALUES ('ebookList');
INSERT INTO page_tb (name) VALUES ('bookDetail');
INSERT INTO item_tb (name, price) VALUES ('extend-category', 500);
INSERT INTO item_tb (name, price) VALUES ('ebook', 500);
INSERT INTO item_tb (name, price) VALUES ('subscribe', 9900);
INSERT INTO page_item_tb VALUES (
	(SELECT id FROM page_tb WHERE name = 'ebookList'),
    (SELECT id FROM item_tb WHERE name = 'extend-category')
);
INSERT INTO page_item_tb VALUES (
	(SELECT id FROM page_tb WHERE name = 'bookDetail'),
    (SELECT id FROM item_tb WHERE name = 'ebook')
);

-- 샘플 데이터
INSERT INTO balance_history_tb (user_id, wave_change, mileage_change, description) VALUES (1, 5000, 5000, '테스트용 재화 부여');
