-- 초기 필수 데이터
INSERT INTO page_tb (name) VALUES ('ebookList');
INSERT INTO page_tb (name) VALUES ('bookDetail');
INSERT INTO page_tb (name) VALUES ('computerReservation');
INSERT INTO page_tb (name) VALUES ('renew');
INSERT INTO page_tb (name) VALUES ('printer');
INSERT INTO item_tb (name, price) VALUES ('extend-category', 500);
INSERT INTO item_tb (name, price) VALUES ('ebook', 500);
INSERT INTO item_tb (name, price) VALUES ('subscribe', 9900);
INSERT INTO item_tb (name, price) VALUES ('computer', 300);
INSERT INTO item_tb (name, price) VALUES ('renew1', 100);
INSERT INTO item_tb (name, price) VALUES ('renew2', 200);
INSERT INTO item_tb (name, price) VALUES ('renew3', 300);
INSERT INTO item_tb (name, price) VALUES ('renew4', 400);
INSERT INTO item_tb (name, price) VALUES ('renew5', 500);
INSERT INTO item_tb (name, price) VALUES ('renew6', 600);
INSERT INTO item_tb (name, price) VALUES ('renew7', 700);
INSERT INTO item_tb (name, price) VALUES ('print', 100);
INSERT INTO page_item_tb VALUES (
	(SELECT id FROM item_tb WHERE name = 'extend-category'),
	(SELECT id FROM page_tb WHERE name = 'ebookList')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'ebook'),
    (SELECT id FROM page_tb WHERE name = 'bookDetail')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'computer'),
    (SELECT id FROM page_tb WHERE name = 'computerReservation')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew1'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew2'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew3'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew4'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew5'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew6'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'renew7'),
    (SELECT id FROM page_tb WHERE name = 'renew')
);
INSERT INTO page_item_tb VALUES (
    (SELECT id FROM item_tb WHERE name = 'print'),
    (SELECT id FROM page_tb WHERE name = 'printer')
);

-- 샘플 데이터
INSERT INTO balance_history_tb (user_id, wave_change, mileage_change, description) VALUES (1, 5000, 5000, '테스트용 재화 부여');
