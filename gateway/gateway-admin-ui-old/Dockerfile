FROM nginx:alpine
MAINTAINER li7hai26@gmail.com
WORKDIR /frontend
COPY ./dist /frontend/
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
RUN /bin/sh -c 'echo init nginx ok'