-- 필수 데이터
-- 컴퓨터 세팅
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 2
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 5
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 11
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 14
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 16
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (false); -- 18
INSERT INTO computer_tb (status) VALUES (true);
INSERT INTO computer_tb (status) VALUES (true);

-- 샘플 데이터
-- 컴퓨터 예약 내역 샘플
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (1, 1, '2024-09-24 11:00:00', '2024-09-24 12:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (8, 2, '2024-09-24 11:00:00', '2024-09-24 12:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (3, 3, '2024-09-24 11:30:00', '2024-09-24 12:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (4, 4, '2024-09-24 11:00:00', '2024-09-24 12:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (9, 5, '2024-09-24 12:00:00', '2024-09-24 13:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (1, 6, '2024-09-24 12:00:00', '2024-09-24 13:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (7, 1, '2024-09-24 13:00:00', '2024-09-24 14:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (8, 13, '2024-09-23 10:30:00', '2024-09-23 11:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (8, 8, '2024-09-25 10:30:00', '2024-09-25 11:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (7, 11, '2024-09-25 15:30:00', '2024-09-25 16:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (19, 3, '2024-09-25 14:30:00', '2024-09-25 15:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (19, 18, '2024-09-25 09:30:00', '2024-09-25 10:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (6, 16, '2024-09-23 16:30:00', '2024-09-23 17:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (9, 14, '2024-09-25 14:30:00', '2024-09-25 15:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (4, 19, '2024-09-23 16:30:00', '2024-09-23 17:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (4, 16, '2024-09-25 13:00:00', '2024-09-25 14:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (15, 1, '2024-09-23 10:30:00', '2024-09-23 11:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (10, 4, '2024-09-25 15:30:00', '2024-09-25 16:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (19, 5, '2024-09-23 17:30:00', '2024-09-23 18:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (12, 18, '2024-09-25 10:00:00', '2024-09-25 11:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (9, 16, '2024-09-25 23:30:00', '2024-09-22 00:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (17, 13, '2024-09-25 15:30:00', '2024-09-25 16:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (15, 17, '2024-09-23 16:00:00', '2024-09-23 16:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (9, 9, '2024-09-25 12:30:00', '2024-09-25 13:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (17, 11, '2024-09-23 10:00:00', '2024-09-23 10:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (9, 16, '2024-09-25 15:30:00', '2024-09-25 16:30:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (1, 9, '2024-09-23 13:00:00', '2024-09-23 14:00:00');
INSERT INTO computer_reservation_tb (computer_id, user_id, start_time, end_time) VALUES (1, 6, '2024-09-23 13:30:00', '2024-09-23 14:00:00'),
																						(15, 16, '2024-09-23 10:30:00', '2024-09-23 11:00:00'),
																						(9, 4, '2024-09-25 09:30:00', '2024-09-25 10:00:00'),
																						(20, 7, '2024-09-23 15:30:00', '2024-09-23 16:00:00'),
																						(3, 3, '2024-09-23 15:30:00', '2024-09-23 16:00:00'),
																						(8, 11, '2024-09-23 10:30:00', '2024-09-23 11:30:00'),
																						(3, 7, '2024-09-25 12:30:00', '2024-09-25 13:00:00'),
																						(4, 10, '2024-09-25 11:00:00', '2024-09-25 11:30:00'),
																						(19, 2, '2024-09-23 13:30:00', '2024-09-23 14:30:00'),
																						(10, 15, '2024-09-25 13:00:00', '2024-09-25 13:30:00'),
																						(1, 1, '2024-09-23 23:30:00', '2024-09-25 00:00:00'),
																						(8, 16, '2024-09-25 17:30:00', '2024-09-25 18:30:00'),
																						(4, 17, '2024-09-23 15:00:00', '2024-09-23 15:30:00'),
																						(7, 11, '2024-09-25 23:30:00', '2024-09-22 00:00:00'),
																						(1, 18, '2024-09-23 16:00:00', '2024-09-23 16:30:00'),
																						(17, 17, '2024-09-23 16:30:00', '2024-09-23 17:30:00'),
																						(6, 11, '2024-09-25 13:00:00', '2024-09-25 13:30:00'),
																						(13, 3, '2024-09-25 15:00:00', '2024-09-25 15:30:00'),
																						(8, 16, '2024-09-25 10:30:00', '2024-09-25 11:30:00'),
																						(15, 17, '2024-09-25 09:00:00', '2024-09-25 10:00:00');
                                                                                        

