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

# Nginx 설정 직접 작성
RUN echo "server {" > /etc/nginx/conf.d/default.conf \
    && echo "    listen 3000;" >> /etc/nginx/conf.d/default.conf \
    && echo "    location / {" >> /etc/nginx/conf.d/default.conf \
    && echo "        root   /usr/share/nginx/html;" >> /etc/nginx/conf.d/default.conf \
    && echo "        index  index.html index.htm;" >> /etc/nginx/conf.d/default.conf \
    && echo "        try_files \$uri \$uri/ /index.html;" >> /etc/nginx/conf.d/default.conf \
    && echo "    }" >> /etc/nginx/conf.d/default.conf \
    && echo "}" >> /etc/nginx/conf.d/default.conf

COPY --from=build /usr/src/app/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
