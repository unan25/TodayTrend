#노드 버전 20.9.0
FROM node:20.9.0-alpine as builder

# 작업 위치 지정
WORKDIR /app

#환경 변수 저장
ENV PATH /app/node_modules/.bin:$PATH

# 현재 파일을 이미지의 /app 위치에 복사
COPY . /app

# package.json 에 명시된 의존성 설치
RUN npm install

# 빌드 시작
RUN npm run build

# ----  NginX ---- #
FROM nginx:latest

# 기존 환경설정 제거
RUN rm -rf /etc/nginx/conf.d

# 내 디렉토리의 conf 폴더 내용을 복사 (conf/conf.d/default.conf 가 존재해야함)
COPY conf /etc/nginx

# builder로 부터 /app/build를 복사
COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]