#!/bin/bash
#版本
version=$1

#前缀
prefix="registry.cn-hangzhou.aliyuncs.com/hthj_asoco/"

# build server
docker  build  -f db-metadata-server-demo/Dockerfile  --rm=true  -t $prefix"db-metadata-server:"${version} ./db-metadata-server-demo ;
# build web
docker  build -f db-metadata-web/Dockerfile --rm=true  -t $prefix"db-metadata-web:"${version} ./db-metadata-web ;
#登录凭证
docker login -u "${PLUGIN_DOCKER_USERNAME}" -p "${PLUGIN_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com;

# 推送镜像
docker push $prefix"db-metadata-server:"${version} ;
docker push $prefix"db-metadata-web:"${version} ;

# 清理镜像
docker rmi $prefix"db-metadata-server:"${version} ;
docker rmi $prefix"db-metadata-web:"${version} ;

#清理空间
docker system prune -f;

exit 0
