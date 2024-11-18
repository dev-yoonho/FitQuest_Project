CREATE DATABASE fitquest;

USE fitquest;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    nickname VARCHAR(50) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    height FLOAT NOT NULL,
    weight FLOAT NOT NULL,
    age INT NOT NULL
);

CREATE TABLE Exercise (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    exercise_name VARCHAR(100) NOT NULL,
    exercise_description TEXT,
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

CREATE TABLE CharacterInfo (
    user_id INT PRIMARY KEY,
    character_job ENUM('전사', '마법사') NOT NULL,
    character_gender ENUM('남성', '여성'),
    strength INT DEFAULT 0,
    agility INT DEFAULT 0,
    toughness INT DEFAULT 0,
    stamina INT DEFAULT 0,
    luck INT DEFAULT 0,
    patk INT DEFAULT 0,
    def INT DEFAULT 0,
    cridam INT DEFAULT 0,
    matk INT DEFAULT 0,
    mres INT DEFAULT 0,
    hprecov INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE CharacterCustomization (
    customization_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    customization_options VARCHAR(255),
    illustration_id INT,
    voice_id INT,
    FOREIGN KEY (user_id) REFERENCES CharacterInfo(user_id),
    FOREIGN KEY (illustration_id) REFERENCES Illustration(illustration_id),
    FOREIGN KEY (voice_id) REFERENCES Voice(voice_id)
);

CREATE TABLE Illustration (
    illustration_id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL
);

CREATE TABLE Voice (
    voice_id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL
);

CREATE TABLE Post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
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

CREATE TABLE Party (
    party_id INT AUTO_INCREMENT PRIMARY KEY,
    party_name VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE PartyMember (
    user_id INT PRIMARY KEY,
    party_id INT,
    joined_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES Party(party_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Monster (
	monster_name VARCHAR(50) PRIMARY KEY,
    monster_desc TEXT NOT NULL,
    img_path VARCHAR(255) NOT NULL,
    stamina INT DEFAULT 0,
    patk INT DEFAULT 0,
    def INT DEFAULT 0,
    matk INT DEFAULT 0,
    mres INT DEFAULT 0,
    hprecov INT DEFAULT 0
);

CREATE TABLE MonsterRaid (
	raid_id INT AUTO_INCREMENT PRIMARY KEY,
    party_id INT,
    monster_name VARCHAR(50),
    stamina INT DEFAULT 0,
    raided_at DATETIME DEFAULT NULL,
    FOREIGN KEY (party_id) REFERENCES Party(party_id),
    FOREIGN KEY (monster_name) REFERENCES Monster(monster_name)
);
