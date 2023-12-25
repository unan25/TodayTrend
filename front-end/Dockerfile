# 1. node 이미지 사용
FROM    node:20-alpine

# 2. 패키지 우선 복사
COPY    ./package* /usr/src/app/
WORKDIR /usr/src/app
RUN     npm install

# 3. 소스 복사
COPY . /usr/src/app

# 4. WEB 서버 실행 (Listen 포트 정의)
EXPOSE 3000
CMD    npm start