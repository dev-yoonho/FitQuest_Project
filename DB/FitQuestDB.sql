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

SELECT * FROM User;

INSERT INTO Post (user_id, category, title, content, views, likes, attachment_path) VALUES
(1, '자유', '안녕하세요! 첫 글입니다.', '여러분과 함께하는 즐거운 시간!', 150, 10, NULL),
(2, '질문', '운동 루틴 추천 부탁드립니다.', '다이어트를 시작했는데, 어떤 루틴이 좋을까요?', 200, 20, NULL),
(1, '모임', '이번 주말 산행 같이 하실 분?', '이번 주말 함께 등산하실 분 구합니다!', 75, 15, NULL),
(2, '자유', '오늘의 다짐', '오늘부터 하루 30분 운동 시작!', 50, 5, NULL),
(1, '질문', '영양제 추천 부탁드립니다.', '어떤 영양제를 먹으면 좋을까요?', 90, 8, NULL),
(2, '모임', '플로깅 함께해요!', '주말에 플로깅하실 분 연락 주세요.', 60, 12, NULL),
(1, '자유', '내일의 계획', '내일은 아침에 조깅할 예정입니다!', 45, 4, NULL),
(2, '질문', '운동 전후 식단은?', '운동 전후 어떤 음식을 먹는 게 좋을까요?', 110, 9, NULL),
(1, '자유', '가벼운 마음으로 글 써봅니다.', '요즘 몸이 좋아지고 있는 것 같아요.', 70, 7, NULL),
(2, '모임', '함께 배드민턴 치실 분?', '평일 저녁 배드민턴 같이 하실 분 찾습니다.', 85, 11, NULL),
(1, '질문', '홈트레이닝 기구 추천', '집에서 운동할 때 추천하는 기구 있을까요?', 120, 14, NULL),
(2, '자유', '오늘 하루 운동 성공!', '오늘은 목표 달성했어요. 뿌듯합니다.', 65, 6, NULL),
(1, '모임', '축구 동호회 모집합니다.', '월요일 저녁 축구 모임 같이 하실 분 연락 주세요.', 90, 10, NULL),
(2, '질문', '유산소 운동 효과', '유산소 운동을 하면 체력이 얼마나 좋아질까요?', 105, 9, NULL),
(1, '자유', '새로운 운동복 샀어요.', '운동복 입으니까 더 운동하고 싶네요.', 40, 3, NULL),
(2, '모임', '같이 자전거 타실 분 구합니다.', '주말에 한강 자전거 타실 분 구해요.', 95, 13, NULL),
(1, '질문', '웨이트 트레이닝 팁', '초보자가 꼭 알아야 할 팁이 있나요?', 115, 17, NULL),
(2, '자유', '운동 전후 스트레칭', '스트레칭 꼭 하세요! 정말 좋습니다.', 50, 5, NULL),
(1, '모임', '등산 동호회 모집합니다.', '등산하실 분 모집합니다. 연락 주세요.', 85, 12, NULL),
(2, '질문', '체중 감량 운동 추천', '체중 감량에 효과적인 운동이 궁금합니다.', 140, 18, NULL);

SELECT * FROM Post;
