## 기능 구체화

### 1. 운동 기록

- **운동 등록/조회/수정/삭제 기능**: 사용자는 자신의 운동 기록을 등록, 조회, 수정, 삭제할 수 있습니다. 각 운동 기록에는 운동의 종류(근력, 유산소, 코어)와 운동 날짜가 포함됩니다.
- **운동 카테고리 분류**: 근력 운동, 유산소 운동, 코어 운동으로 분류됩니다.
- **근력 운동**: 사용자가 직접 입력 (세트 수, 세트당 횟수, 무게).
- **유산소 운동**: 거리, 시간, 속도 입력, 실내외 여부 선택
- **코어 운동**: 사용자가 직접 입력 (세트 수, 세트당 횟수 또는 수행 시간).
- **운동 등록 날짜**: 사용자가 언제 운동을 했는지를 기록하여 운동 이력을 관리합니다.

### 2. 식단 기록

- **개별 사용자 정보 등록/수정/조회/삭제 기능**: 사용자가 자신의 식단을 등록하고, 조회, 수정, 삭제할 수 있습니다.
- **BMR 계산 및 목표 칼로리 설정**: 사용자 정보(키, 몸무게, 나이, 성별)를 바탕으로 BMR(기초대사량)을 계산하고 목표 칼로리를 설정합니다.
- **식단 기록 입력**: 사용자가 직접 입력합니다.
- **식단 등록 날짜**: 식단 기록이 언제 등록되었는지 관리합니다.

### 3. 커뮤니티

- **본인의 게시물 등록/조회/수정/삭제 기능**: 사용자는 커뮤니티 게시판에 글을 올리고 조회, 수정, 삭제할 수 있습니다.
- **게시물 정보**: 제목, 내용, 작성자, 작성 시간, 조회 수, 추천 수, 카테고리, 첨부된 파일 경로.
- **카테고리**: 자유, 파티 모집으로 구분됩니다.
- **댓글 및 대댓글**: 댓글과 대댓글을 등록, 조회, 수정, 삭제할 수 있습니다. 댓글에도 추천 기능이 있습니다.
- **게시물 파일 업로드**: 게시물에 파일(이미지 포함)을 첨부할 수 있습니다.
- **댓글 제한**: 댓글에는 텍스트만 입력 가능합니다.
- **사용자 경험 개선을 위한 조언 댓글 챗봇**: 일정기간 댓글이 달리지 않은 질문글 등의 경우 챗봇이 댓글을 달아 경험 개선

### 4. 일반 회원

- **회원가입, 로그인, 로그아웃 기능**: 사용자는 회원가입을 통해 계정을 생성하고 로그인/로그아웃할 수 있습니다.
- **프로필 관리**: 이름, ID, 비밀번호, 주소, 이메일, 전화번호, 닉네임, 관리자 여부 등 기본적인 사용자 정보를 관리합니다.
- **비밀번호 보안**: 해시 함수를 이용한 비밀번호 암호화 저장(개발 시간 부족 시 제외 가능).
- **사용자 삭제 시 데이터 처리**: 사용자가 삭제되어도 해당 사용자가 작성한 게시물과 댓글은 유지됩니다.

### 5. 관리자

- **모든 회원 등록/조회/수정/삭제 기능**
- **모든 운동 기록 등록/조회/수정/삭제**
- **모든 식단 기록 등록/조회/수정/삭제 기능**
- **모든 게시물 등록/조회/수정/삭제 기능**
- **모든 댓글 등록/조회/수정/삭제 기능**


## 테이블 목록

### 1. 사용자 (User)

- **사용자 ID (user\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **이메일 (email)**: VARCHAR(100), UNIQUE, NOT NULL
- **비밀번호 (password)**: VARCHAR(255), NOT NULL
- **이름 (name)**: VARCHAR(50), NOT NULL
- **전화번호 (phone\_number)**: VARCHAR(20)
- **닉네임 (nickname)**: VARCHAR(50), NOT NULL, UNIQUE
- **관리자 여부 (is\_admin)**: BOOLEAN, DEFAULT FALSE
- **키**: FLOAT, NOT NULL
- **체중**: FLOAT, NOT NULL
- **나이**: INT, NOT NULL

### 2. 운동 (Exercise)

- **운동 ID (exercise\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **운동 이름 (exercise\_name)**: VARCHAR(100), NOT NULL
- **운동 유형 (exercise\_type)**: ENUM('근력', '유산소', '코어'), NOT NULL
- **세트 수 (exercise_set)**: INT
- **반복 횟수 (repetitions)**: INT
- **무게 (weight)**: FLOAT
- **거리 (distance)**: FLOAT
- **시간 (time)**: INT
- **속도 (speed)**: FLOAT
- **실내외 여부 (indoor\_outdoor)**: ENUM('실내', '실외')
- **운동 등록 날짜 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP

### 3. 식단 (Diet)

- **식단 ID (diet\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **음식 이름 (food\_name)**: VARCHAR(100), NOT NULL
- **칼로리 (calories)**: INT, NOT NULL
- **식사 유형 (meal\_type)**: ENUM('아침', '점심', '저녁'), NOT NULL
- **식단 등록 날짜 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP


### 4. 게시글 (Post)

- **게시글 ID (post\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **유형 (category)**: ENUM('자유', '질문', '모임'), NOT NULL
- **제목 (title)**: VARCHAR(255), NOT NULL
- **내용 (content)**: TEXT NOT NULL
- **작성 시간 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP
- **조회수 (views)**: INT DEFAULT 0
- **추천 수 (likes)**: INT DEFAULT 0
- **첨부 파일 경로 (attachment\_path)**: VARCHAR(255)

### 5. 댓글 (Comment)

- **댓글 ID (comment\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **게시글 ID (post\_id)**: FOREIGN KEY REFERENCES Post(post\_id)
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **내용 (content)**: TEXT NOT NULL
- **작성 시간 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP
- **추천 수 (likes)**: INT DEFAULT 0



