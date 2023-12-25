# Stage 1: 빌드를 위한 단계
FROM node:20-alpine AS build

# 소스 코드 복사
COPY ./package* /usr/src/app/
WORKDIR /usr/src/app

# 의존성 설치
RUN npm install

# 소스 코드 복사
COPY . /usr/src/app

# 빌드 수행
RUN npm run build

# Stage 2: 실행을 위한 단계
FROM nginx:stable-alpine

# 기존 nginx 설정 제거
RUN rm -rf /etc/nginx/conf.d

# 새로운 nginx 설정 추가
COPY conf /etc/nginx

# 빌드 단계에서 생성된 파일 복사
COPY --from=build /usr/src/app/build /usr/share/nginx/html

# 포트 노출
EXPOSE 3000

# nginx를 백그라운드에서 실행하도록 설정
CMD ["nginx", "-g", "daemon off;"]
