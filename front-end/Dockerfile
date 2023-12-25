# step 1 빌드를 하기 위한 과정
FROM node:20-alpine AS build
WORKDIR /usr/src/app
COPY ./package* ./
RUN npm install
COPY . ./
RUN npm run build

# step 2 실행 스테이지를 위한 과정
FROM nginx:stable-alpine
# 엔진엑스 설치
RUN apk update && apk add nginx 
RUN rm -rf /etc/nginx/conf.d

# Jenkins 작업 디렉토리에서 경로 수정
COPY TodayTrend/front-end/conf/conf.d/default.conf /etc/nginx/conf.d
COPY --from=build /usr/src/app/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]