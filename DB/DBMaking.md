## 기능 구체화

### 1. 운동 기록

- **운동 등록/조회/수정/삭제 기능**: 사용자는 자신의 운동 기록을 등록, 조회, 수정, 삭제할 수 있습니다. 각 운동 기록에는 운동의 종류(근력, 유산소, 코어)와 운동 날짜가 포함됩니다.
- **운동 카테고리 분류**: 근력 운동, 유산소 운동, 코어 운동으로 분류됩니다.
  - **근력 운동**: 사용자가 직접 입력 (세트 수, 세트당 횟수, 무게).
  - **유산소 운동**: 거리 입력 / 기한에 여유가 있으면 계산 (GPS, 자이로 센서 WEB API 이용, 실외) 또는 시간, 속도 입력 (실내).
  - **코어 운동**: 사용자가 직접 입력 (세트 수, 세트당 횟수 또는 수행 시간).
- **운동 등록 날짜**: 사용자가 언제 운동을 했는지를 기록하여 운동 이력을 관리합니다.

### 2. 식단 기록

- **개별 사용자 정보 등록/수정/조회/삭제 기능**: 사용자가 자신의 식단을 등록하고, 조회, 수정, 삭제할 수 있습니다.
- **BMR 계산 및 목표 칼로리 설정**: 사용자 정보(키, 몸무게, 나이, 성별)를 바탕으로 BMR(기초대사량)을 계산하고 목표 칼로리를 설정합니다.
- **식단 기록 입력**: 사용자가 직접 입력합니다.
- **식단 등록 날짜**: 식단 기록이 언제 등록되었는지 관리합니다.

### 3. 스탯 조정

- **스탯 등록/수정/삭제/조회 기능**: 캐릭터의 주요 스탯인 힘(STR), 민첩(AGI), 정신력(Toughness), 체력(STA), 운(LUK)을 관리합니다.
- **일반 스탯 조정 로직**: 운동 기록과 식단 기록에 따라 캐릭터의 스탯이 자동으로 증가합니다.
  - **근력 운동**: 힘(STR) 증가 (세트 수, 횟수, 무게에 비례).
  - **유산소 운동**: 민첩(AGI) 증가 (달린 거리 반영).
  - **코어 운동**: 정신력(Toughness) 증가 (운동 시간에 비례).
  - **식단 기록**: 목표 칼로리 달성 시 체력(STA) 증가.
  - **출석 보상**: 출석 보상에 따라 운(LUK) 증가.
- **파생 스탯 계산**: 각 직업별 특화된 파생 스탯(전사 - 물리 공격력, 방어력, 치명타 피해 / 마법사 - 마법 공격력, 마법 저항력, 체력 회복 속도).

### 4. 캐릭터 설정

- **캐릭터 정보 등록/수정/삭제/조회 기능**: 사용자는 자신의 캐릭터를 생성, 조회, 수정, 삭제할 수 있습니다.
- **직업 설정**: 전사(Warrior) 또는 마법사(Mage)를 선택할 수 있으며, 추후 다른 직업을 업데이트할 계획입니다.
- **캐릭터와 사용자 일대일 관계**: 각 사용자는 하나의 캐릭터만 보유할 수 있습니다. 캐릭터와 사용자의 성별은 다를 수 있습니다.

### 5. 캐릭터 커스터마이제이션

- **일러스트와 보이스 등록/수정/삭제/조회 기능**: 사용자는 자신의 캐릭터를 커스터마이즈할 수 있습니다.
- **초기 제공 일러스트와 보이스**: 캐릭터 생성 시 기본 일러스트와 보이스가 제공됩니다.
- **추가 커스터마이징 아이템 제공**: 스탯 총합이 5의 배수가 될 때마다 추가 일러스트와 보이스가 제공됩니다.
- **캐릭터와 커스터마이제이션 간 일대다 관계**: 하나의 캐릭터는 여러 개의 일러스트와 보이스를 가질 수 있습니다.

### 6. 커뮤니티

- **게시물 등록, 조회, 수정, 삭제 기능**: 사용자는 커뮤니티 게시판에 글을 올리고 조회, 수정, 삭제할 수 있습니다.
- **게시물 정보**: 제목, 내용, 작성자, 작성 시간, 조회 수, 추천 수, 카테고리, 첨부된 파일 경로.
  - **카테고리**: 자유, 파티 모집으로 구분됩니다.
- **댓글 및 대댓글**: 댓글과 대댓글을 등록, 조회, 수정, 삭제할 수 있습니다. 댓글에도 추천 기능이 있습니다.
- **게시물 파일 업로드**: 게시물에 파일(이미지 포함)을 첨부할 수 있습니다.
- **댓글 제한**: 댓글에는 텍스트만 입력 가능합니다.

### 7. 회원

- **회원가입, 로그인, 로그아웃 기능**: 사용자는 회원가입을 통해 계정을 생성하고 로그인/로그아웃할 수 있습니다.
- **프로필 관리**: 이름, ID, 비밀번호, 주소, 이메일, 전화번호, 닉네임, 관리자 여부 등 기본적인 사용자 정보를 관리합니다.
- **비밀번호 보안**: 해시 함수를 이용한 비밀번호 암호화 저장(개발 시간 부족 시 제외 가능).
- **사용자 삭제 시 데이터 처리**: 사용자가 삭제되어도 해당 사용자가 작성한 게시물과 댓글은 유지됩니다.

### 8. 파티(길드)

- **파티 등록/수정/조회/삭제 기능**: 사용자는 파티를 생성하고 다른 사용자와 함께 파티를 구성할 수 있습니다.
- **파티의 형태**: 파티 멤버 간의 커뮤니티 활동과 몬스터 레이드를 지원합니다.
- **파티 멤버 관리**: 각 사용자는 하나의 파티에만 참여할 수 있으며, 파티 멤버 간의 활동을 기록하고 조회할 수 있습니다.

### 9. 몬스터 레이드
- **레이드 참가 조건**: 파티에 참여하고 있는 사용자만 레이드가 가능합니다.
- **레이드 진행 방식**: 한 번의 레이드에는 하나의 파티만 참여할 수 있으며, 파티원들이 협력하여 몬스터를 처치합니다.
- **몬스터 정보**: 각 레이드에는 몬스터의 정보(체력, 공격력, 방어력 등)가 제공되며, 몬스터의 체력이 0이 되면 레이드가 완료됩니다.
- **레이드 단계**: 몬스터의 난이도는 여러 단계로 구성되어 있으며, 이전 단계의 몬스터를 처치해야 다음 단계로 진행할 수 있습니다.
- **레이드 보상**: 레이드를 완료하면 참여한 파티원들에게 보상이 지급되며, 보상은 캐릭터의 스탯 증가 또는 커스터마이징 아이템으로 구성될 수 있습니다.
- **레이드 기록 관리**: 레이드가 완료되면 레이드 기록이 저장되며, 파티별로 레이드 진행 상황을 확인할 수 있습니다.
- **잔여 체력 관리**: 레이드 진행 중 몬스터의 잔여 체력을 실시간으로 관리하며, 레이드 테이블에 업데이트됩니다.


## 테이블 목록

### 1. 사용자 (User)

- **사용자 ID (user\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **이름 (name)**: VARCHAR(50), NOT NULL
- **아이디 (username)**: VARCHAR(50), UNIQUE, NOT NULL
- **비밀번호 (password)**: VARCHAR(255), NOT NULL
- **주소 (address)**: VARCHAR(255)
- **이메일 (email)**: VARCHAR(100), UNIQUE, NOT NULL
- **전화번호 (phone\_number)**: VARCHAR(20)
- **닉네임 (nickname)**: VARCHAR(50), NOT NULL
- **관리자 여부 (is\_admin)**: BOOLEAN, DEFAULT FALSE
- **일러스트레이션 ID (illustration\_id)**: INT, FOREIGN KEY REFERENCES Illustration(illustration\_id)

### 2. 운동 (Exercise)

- **운동 ID (exercise\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **운동 이름 (exercise\_name)**: VARCHAR(100), NOT NULL
- **운동 설명 (exercise\_description)**: TEXT
- **운동 유형 (exercise\_type)**: ENUM('근력', '유산소', '코어'), NOT NULL
- **세트 수 (sets)**: INT
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

### 4. 캐릭터 (CharacterInfo)

- **사용자 ID (user\_id)**: PRIMARY KEY, FOREIGN KEY REFERENCES User(user\_id)
- **캐릭터 직업 (character\_job)**: ENUM('전사', '마법사'), NOT NULL
- **캐릭터 성별 (character\_gender)**: ENUM('남성', '여성')
- **힘 (strength)**: INT, DEFAULT 0
- **민첩 (agility)**: INT, DEFAULT 0
- **정신력 (toughness)**: INT, DEFAULT 0
- **체력 (stamina)**: INT, DEFAULT 0
- **운 (luck)**: INT, DEFAULT 0
- **물리 공격력 (patk)**: INT, DEFAULT 0
- **방어력 (def)**: INT, DEFAULT 0
- **치명타 피해 (cridam)**: INT, DEFAULT 0
- **마법 공격력 (matk)**: INT, DEFAULT 0
- **마법 저항력 (mres)**: INT, DEFAULT 0
- **체력 회복 속도 (hprecov)**: INT, DEFAULT 0

### 5. 캐릭터 커스터마이제이션 (CharacterCustomization)

- **커스터마이제이션 ID (customization\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES CharacterInfo(user\_id)
- **커스터마이징 옵션 (customization\_options)**: VARCHAR(255)
- **일러스트 ID (illustration\_id)**: FOREIGN KEY REFERENCES Illustration(illustration\_id)
- **보이스 ID (voice\_id)**: FOREIGN KEY REFERENCES Voice(voice\_id)

### 6. 일러스트 (Illustration)

- **일러스트 ID (illustration\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **파일 경로 (file\_path)**: VARCHAR(255), NOT NULL

### 7. 보이스 (Voice)

- **보이스 ID (voice\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **파일 경로 (file\_path)**: VARCHAR(255), NOT NULL

### 8. 게시글 (Post)

- **게시글 ID (post\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **제목 (title)**: VARCHAR(255), NOT NULL
- **내용 (content)**: TEXT NOT NULL
- **작성 시간 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP
- **조회수 (views)**: INT DEFAULT 0
- **추천 수 (likes)**: INT DEFAULT 0
- **첨부 파일 경로 (attachment\_path)**: VARCHAR(255)

### 9. 댓글 (Comment)

- **댓글 ID (comment\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **게시글 ID (post\_id)**: FOREIGN KEY REFERENCES Post(post\_id)
- **사용자 ID (user\_id)**: FOREIGN KEY REFERENCES User(user\_id)
- **내용 (content)**: TEXT NOT NULL
- **작성 시간 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP
- **추천 수 (likes)**: INT DEFAULT 0

### 10. 파티 (Party)

- **파티 ID (party\_id)**: PRIMARY KEY, INT, AUTO\_INCREMENT
- **파티 이름 (party\_name)**: VARCHAR(100), NOT NULL
- **생성일시 (created\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP

### 11. 파티 멤버 (PartyMember)

- **사용자 ID (user\_id)**: PRIMARY KEY, FOREIGN KEY REFERENCES User(user\_id)
- **파티 ID (party\_id)**: FOREIGN KEY REFERENCES Party(party\_id)
- **참여 일자 (joined\_at)**: DATETIME DEFAULT CURRENT\_TIMESTAMP

### 12. 몬스터 (Monster)

- **몬스터 이름 (monster_name)**: PRIMARY KEY, VARCHAR(50)
- **몬스터 설명 (monster_desc)**: TEXT NOT NULL
- **몬스터 이미지 경로(img_path)**: VARCHAR(255), NOT NULL
- **체력 (stamina)**: INT, DEFAULT 0
- **물리 공격력 (patk)**: INT, DEFAULT 0
- **방어력 (def)**: INT, DEFAULT 0
- **마법 공격력 (matk)**: INT, DEFAULT 0
- **마법 저항력 (mres)**: INT, DEFAULT 0
- **체력 회복 속도 (hprecov)**: INT, DEFAULT 0

### 13. 몬스터 레이드 (MonsterRaid)

- **레이드 ID (raid_id)**: PRIMARY KEY, INT, AUTO_INCREMENT
- **파티 ID (party_id)**: FOREIGN KEY REFERENCES Party(party_id)
- **몬스터 이름 (monster_name)**: FOREIGN KEY REFERENCES Monster(monster_name)
- **잔여 체력 (stamina)**: INT, DEFAULT 0
- **레이드 완료 일자 (raided_at)**: DATETIME DEFAULT NULL


