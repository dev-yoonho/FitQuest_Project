CREATE DATABASE fitquest;

USE fitquest;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    nickname VARCHAR(50) UNIQUE NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    height FLOAT NOT NULL,
    weight FLOAT NOT NULL,
    age INT NOT NULL
);

CREATE TABLE Exercise (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    exercise_name VARCHAR(100) NOT NULL,
    exercise_type ENUM('근력', '유산소', '코어') NOT NULL,
    exercise_set INT,
    repetitions INT,
    weight FLOAT,
    distance FLOAT,
    time INT,
    speed FLOAT,
    indoor_outdoor ENUM('실내', '실외'),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Diet (
    diet_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    food_name VARCHAR(100) NOT NULL,
    calories INT NOT NULL,
    meal_type ENUM('아침', '점심', '저녁') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category ENUM('자유', '질문', '모임') NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    likes INT DEFAULT 0,
    attachment_path VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    likes INT DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES Post(post_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);