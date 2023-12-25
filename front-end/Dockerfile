# step 1 빌드를 하기 위한 과정
FROM node:20-alpine AS build
COPY ./package* /usr/src/app/
WORKDIR /usr/src/app
RUN npm install
COPY . /usr/src/app
RUN npm run build

# step 2 실행 스테이지를 위한 과정
FROM nginx:stable-alpine
# 엔진엑스 설치
RUN apk update && apk add nginx 
RUN rm -rf /etc/nginx/conf.d

# 프로젝트 디렉토리에 conf/conf.d/default.conf 파일이 있다고 가정
COPY TodayTrend/front-end/conf/conf.d/default.conf /etc/nginx/conf.d
COPY --from=build /usr/src/app/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
