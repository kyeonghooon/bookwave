insert into payment_tb(id, payment_key, type, user_id, order_id, order_name, method, total_amount, requested_at, approved_at, status, canceled_at, cancel_reason, cancel_status)
values (1, 'ppppp11111', 'NORMAL', 1, 'ooooo11111', '충전', '카드', 10000, '2024-09-01 11:11:11', '2024-09-01 11:11:12', 'DONE', NULL, NULL, NULL),
             (2, 'ppppp22222', 'NORMAL', 1, 'ooooo22222', '충전', '카드', 30000, '2024-09-02 11:11:11', '2024-09-02 11:11:12', 'DONE', NULL, '단순 변심', 'REQUEST_CANCEL');

