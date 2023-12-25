# step 1 빌드를 하기 위한 과정
FROM node:20-alpine AS build
COPY    ./package* /usr/src/app/
WORKDIR /usr/src/app
RUN     npm install
COPY . /usr/src/app
RUN npm run build

# step 2 실행 스테이지를 위한 과정
FROM nginx:stable-alpine
RUN rm -rf /etc/nginx/conf.d
COPY conf/conf.d /etc/nginx/conf.d  # 수정된 부분
COPY --from=build /usr/src/app/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
