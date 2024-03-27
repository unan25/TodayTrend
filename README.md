# TodayTrend

##### 상세 보기 : https://unnan.notion.site/TodayTrend-e4fbc65733e7441faa58046340c88e0d?pvs=4
---

## | 📖프로젝트 소개

- **프로젝트 :** Today Trend
- **개발 기간 :** 2023.10.25 ~ 2023.12.28
- **개발인원 :** 4명
  - 조장 : [권윤환](https://github.com/unan25)
  - 부조장 : [임승혁](https://github.com/Yim119)
  - 팀원 : [최성민](https://github.com/seongmin1117),  [호지원](https://github.com/jiwon0207)
- **내용 :** 패션 Web SNS

---

## | 👨‍👩‍👧‍👧역할 분담

- **권윤환 (조장)**
  - 프로젝트 관리 (GitHub, Jira, Notion 등)
  - 회원, 인증/인가, 데브옵스
- **임승혁 (부조장 👑)**
  - React, 소셜 로그인, Redis
- **최성민**
  - React, 이미지 업로드, 댓글, RabbitMQ
- **호지원**
  - SNS 전반적인 부분, Resilience
- **상세보기 : *[역할분담](https://www.notion.so/2958fc434c614c41a3110d99aa686850?pvs=21)***

---

## | ⚙ 기술 스택 
      
![todaytrend skills](https://github.com/unan25/TodayTrend/assets/137999702/6506caba-4fb0-4b4c-a538-8a51f8631b8c)

---

## | 🛠 설계
### - ERD
![erd](https://github.com/unan25/TodayTrend/assets/137999702/2bad4358-1a3d-46bc-a65e-3a75bbb384c7)
### - ERD FLOW
![erdflow](https://github.com/unan25/TodayTrend/assets/137999702/6f362805-272f-408a-b38a-989a045c8a84)
### - 서버 구조도
![서버구조도](https://github.com/unan25/TodayTrend/assets/137999702/eb6d75b1-386d-4d27-b91e-6aef6011317e)
### - CI/CD
![cicd](https://github.com/unan25/TodayTrend/assets/137999702/57868d22-298e-41e9-b59d-924eef30f880)
### - USER FLOW
![userflow](https://github.com/unan25/TodayTrend/assets/137999702/e337d40e-9a66-4cd5-9f5e-33fd6547bbcc)
![userflow2](https://github.com/unan25/TodayTrend/assets/137999702/9a8c9ac3-f973-43da-af6d-24379ff331a1)

---

## | ✨ 주요 기능
- **인증/인가**
    - JWT, Spring Security를 접목 및 Access Token, Refresh Token을 이용하여 보안 강화
- **Post**
    - 유저 검색, 해시태그 검색, 카테고리 선택 등을 통해 해당 게시물 조회
    - 게시물 작성 시, 카테고리 선택을 통해 게시물 분류
        - Drag & Drop을 이용하여 Web에서의 불편함 감소
    - 댓글 및 대댓글, 좋아요 구현
        - 위 기능을 통해 유저 간의 간단한 소통 기능 구현
- **캐싱 전략 이용**
    - 1페이지 캐싱을 통해 유저가 방문했을 때의 불편함 감소
- **무한 스크롤**
    - 무한 스크롤을 통해 이전의 콘텐츠 자동 로드
    - 사용자들이 지루함을 느끼지 않고 계속 컨텐츠를 찾아볼 수 있도록 구현
- **Notification**
    - 팔로우 및 게시물 좋아요, 댓글 등에 대한 알람 기능 구현
    - 알람 개수 및 알람 선택 시 해당 게시글 또는 유저로 이동

---

## | 🎇 결과물
#### 첫 방문시 페이지 모습
![1](https://github.com/unan25/TodayTrend/assets/137999702/9136c04a-4bf5-4125-9e72-23c08c4d8b02)
#### 로그인 화면 (자체 로그인 및 소셜 로그인 구현)
![2](https://github.com/unan25/TodayTrend/assets/137999702/ca7c1556-3e94-4c45-851f-c00e52fabfdf)
#### 로그인 시, Token이 발급된 모습
![3](https://github.com/unan25/TodayTrend/assets/137999702/d3fe3da9-089d-49b1-a002-fb7d5dd55d55)
#### 마이페이지
![4](https://github.com/unan25/TodayTrend/assets/137999702/ed8ac7c7-846b-4d09-800f-50173e69c84c)
#### 검색 기능 (해시태그)
![5](https://github.com/unan25/TodayTrend/assets/137999702/69805f06-87a9-4d5c-a120-4e5a08c2b56e)
#### 해시태그 검색 결과 화면
![6](https://github.com/unan25/TodayTrend/assets/137999702/4ad93203-0fa6-4af8-afae-0a2eb2fd0e81)
#### 유저 검색 화면 
![7](https://github.com/unan25/TodayTrend/assets/137999702/8c9fa704-2de5-44f1-adbc-1d130aa2d0bd)
#### 게시물 상세 보기 화면
![8](https://github.com/unan25/TodayTrend/assets/137999702/5c26263c-232e-4d20-bb4a-93460267ab66)
#### 각 서비스 서버 별 인스턴스 구현
![9](https://github.com/unan25/TodayTrend/assets/137999702/3ff98e5d-e0eb-49f8-85d5-6ece86f8af50)
#### 각 서비스 별로 파이프라인 구축 및 배포
![10](https://github.com/unan25/TodayTrend/assets/137999702/96ef55dc-4dd3-4c51-82b9-d99ce39c787a)
#### 🎥 영상 결과물
https://youtu.be/zO2RHKdbPBE
