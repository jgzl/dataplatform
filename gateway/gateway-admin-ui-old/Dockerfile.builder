FROM node:16-alpine As builder
WORKDIR /frontend
COPY . /frontend
RUN yarn && yarn run build

FROM nginx:alpine
MAINTAINER li7hai26@gmail.com
WORKDIR /frontend
COPY --from=builder /frontend/dist /frontend/
COPY --from=builder /frontend/nginx.conf /etc/nginx/conf.d/default.conf
RUN /bin/sh -c 'echo init nginx ok'