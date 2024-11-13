CREATE DATABASE fitquest;

USE fitquest;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    is_admin BOOLEAN DEFAULT FALSE,
    nickname VARCHAR(50) NOT NULL,
    illustration_id INT,
    FOREIGN KEY (illustration_id) REFERENCES Illustration(illustration_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Illustration (
    illustration_id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL
);

CREATE TABLE Exercise (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    exercise_name VARCHAR(100) NOT NULL,
    exercise_description TEXT,
    exercise_type ENUM('근력', '유산소', '코어') NOT NULL,
    sets INT NULL,
    repetitions INT NULL,
    weight FLOAT NULL,
    distance FLOAT NULL,
    time INT NULL,
    speed FLOAT NULL,
    indoor_outdoor ENUM('실내', '실외') NULL
);

CREATE TABLE Food (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    food_name VARCHAR(100) NOT NULL,
    calories INT NOT NULL
);

CREATE TABLE Diet (
    diet_id INT AUTO_INCREMENT PRIMARY KEY,
    food_id INT NOT NULL,
    user_id INT NOT NULL,
    meal_type ENUM('아침', '점심', '저녁') NOT NULL,
    FOREIGN KEY (food_id) REFERENCES Food(food_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterInfo (
    character_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    character_job VARCHAR(50) NOT NULL,
    character_gender ENUM('남성', '여성') NOT NULL,
    strength INT DEFAULT 0,
    agility INT DEFAULT 0,
    toughness INT DEFAULT 0,
    stamina INT DEFAULT 0,
    luck INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterCustomization (
    customization_id INT AUTO_INCREMENT PRIMARY KEY,
    character_id INT NOT NULL,
    customization_options VARCHAR(255),
    FOREIGN KEY (character_id) REFERENCES CharacterInfo(character_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    likes INT DEFAULT 0,
    attachment_path VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    likes INT DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES Post(post_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Party (
    party_id INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    bosses_defeated INT DEFAULT 0
);

CREATE TABLE PartyMember (
    party_id INT NOT NULL,
    user_id INT NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (party_id, user_id),
    FOREIGN KEY (party_id) REFERENCES Party(party_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE BossMonster (
    monster_id INT AUTO_INCREMENT PRIMARY KEY,
    monster_name VARCHAR(100) UNIQUE NOT NULL,
    monster_description TEXT,
    strength INT,
    agility INT,
    toughness INT,
    stamina INT
);

CREATE TABLE BossRaidRecord (
    raid_record_id INT AUTO_INCREMENT PRIMARY KEY,
    party_id INT NOT NULL,
    monster_id INT NOT NULL,
    defeated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (party_id) REFERENCES Party(party_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (monster_id) REFERENCES BossMonster(monster_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Voice (
    voice_id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL
);

CREATE INDEX idx_user_id ON Post(user_id);
CREATE INDEX idx_post_id ON Comment(post_id);
CREATE INDEX idx_party_id ON PartyMember(party_id);
CREATE INDEX idx_monster_id ON BossRaidRecord(monster_id);