# 1. node 이미지 사용
FROM    node:20-alpine

# 2. 패키지 우선 복사
COPY    ./package* /usr/src/app/
WORKDIR /usr/src/app

# 3. npm install 실행 전에 소스 복사
COPY . /usr/src/app/

# 4. 이제 npm install을 실행합니다.
RUN     npm install

# 5. WEB 서버 실행 (Listen 포트 정의)
EXPOSE 3000
CMD    npm start