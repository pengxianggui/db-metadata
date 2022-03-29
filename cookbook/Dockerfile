FROM nginx:1.17.0-alpine-perl
RUN rm /etc/nginx/conf.d/default.conf
COPY site.template /etc/nginx/conf.d/
CMD mv /etc/nginx/conf.d/site.template /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
COPY docs/.vuepress/dist/  /usr/share/nginx/html/
