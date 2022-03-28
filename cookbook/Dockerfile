FROM nginx:1.17.0-alpine-perl
RUN rm /etc/nginx/conf.d/default.conf
COPY site.template /etc/nginx/conf.d/
COPY docs/.vuepress/dist/  /usr/share/nginx/html/
