CREATE TABLE user_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(100) NOT NULL UNIQUE,
    social_id VARCHAR(100) UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(20) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER' COMMENT 'user, admin',
    subscribe TINYINT DEFAULT 0 COMMENT '0:비구독 1:구독',
    wave INT DEFAULT 0,
    mileage INT DEFAULT 0,
    status INT NOT NULL DEFAULT '0' COMMENT '0:정상 1:탈퇴예정 -1:탈퇴',
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_detail_tb (
	user_id INT PRIMARY KEY,
	email VARCHAR(100) NOT NULL UNIQUE,
    gender TINYINT NOT NULL COMMENT '0:남성 1:여성',
    phone VARCHAR(13) NOT NULL UNIQUE,
    zip VARCHAR(10) DEFAULT NULL,
    addr1 VARCHAR(255) DEFAULT NULL,
    addr2 VARCHAR(255) DEFAULT NULL
);

CREATE TABLE book_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    cover VARCHAR(255) COMMENT 'url',
    category VARCHAR(255) NOT NULL,
    publish_date DATE NOT NULL,
    total_stock INT NOT NULL,
    current_stock INT NOT NULL,
    ebook INT DEFAULT 2 COMMENT '0:종이책 1:ebook 2:둘다',
    ebook_path VARCHAR(255),
    likes INT DEFAULT 0,
    score DOUBLE,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE lend_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    status INT DEFAULT 0 COMMENT '0:진행중 -1: 완료 1: 연체',
    lend_date TIMESTAMP DEFAULT NOW(),
    return_date TIMESTAMP,
    returned_date TIMESTAMP
);

CREATE TABLE reservation_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    reservation_date TIMESTAMP DEFAULT NOW(),
    wait_date TIMESTAMP COMMENT '예약 순번이 오면 해당 날짜까지 대출 신청 다시 해야됨',
    status INT DEFAULT 0 COMMENT '0:진행중 -1: 완료 1: 대기'
);

CREATE TABLE items_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL
);

CREATE TABLE item_purchase_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    mileage_amount INT DEFAULT 0,
    mileage_balance INT,
    wave_amount INT DEFAULT 0,
    wave_balance INT,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE charge_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount INT,
    status INT DEFAULT 0 COMMENT '0: 정상 -1: 환불됨',
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE refund_tb (
	charge_id INT,
    user_id INT,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (charge_id, user_id)
);

CREATE TABLE likes_tb (
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    PRIMARY KEY (user_id , book_id)
);

CREATE TABLE favorites_tb (
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    PRIMARY KEY (user_id , book_id)
);

CREATE TABLE user_ebook_tb (
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    PRIMARY KEY (user_id , book_id)
);

CREATE TABLE review_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    score INT NOT NULL,
    content VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(),
    edited_at TIMESTAMP
);

CREATE TABLE notice_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    views INT NOT NULL,
    file_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE question_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    answer_id INT,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE answer_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    user_id INT NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE faq_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    category VARCHAR(10) NOT NULL COMMENT '결제, 개인정보, 도서, 시설, 기타'
);

CREATE TABLE subscribe_tb (
	id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT,
    start_date TIMESTAMP DEFAULT NOW(),
    end_date TIMESTAMP
);

