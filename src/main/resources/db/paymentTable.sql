CREATE TABLE payment_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    payment_key VARCHAR(200),
    type VARCHAR(20),
    user_id INT,
    order_id VARCHAR(64),
    order_name VARCHAR(100),
    method VARCHAR(20),
    total_amount BIGINT,
    requested_at TIMESTAMP,
    approved_at TIMESTAMP,
    status VARCHAR(30),
    cancel_amount BIGINT,
    canceled_at TIMESTAMP,
    cancel_reason VARCHAR(255),
    cancel_status VARCHAR(20)
);

