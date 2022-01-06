# 下面这个镜像是经过修改集成了pinpoint和设置好上海时区的Java镜像
FROM registry.cn-hangzhou.aliyuncs.com/hthj_asoco/java:8-glibc-pinpoint-skywalking

VOLUME /tmp

# 如果需要内置nginx，可以如下实现安装nginx和自启，配置参考nginx的dockerfile
# RUN apk --no-cache add nginx && \
#  mkdir /run/nginx && touch /run/nginx/nginx.pid && \
#  /usr/sbin/nginx
COPY docker-entrypoint.sh docker-entrypoint.sh
COPY target/db-metadata-server-springboot-*.jar /app/app.jar

RUN chmod a+x docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
