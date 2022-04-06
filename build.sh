#!/bin/bash

#版本
version=$1

## 部署目标: code/doc
type=$(echo $2 | sed 's/\\n.*//g')

#前缀
prefix="registry.cn-hangzhou.aliyuncs.com/hthj_asoco/db-metadata:"

echo "type:${type}"
echo "version:${version}"
echo "prefix:${prefix}"

if [ ${type} == 'cookbook' ]; then
  # build cookbook
  docker  build -f cookbook/Dockerfile --rm=true  -t $prefix"cookbook-"${version} ./cookbook ;

  #登录凭证
  docker login -u "${PLUGIN_DOCKER_USERNAME}" -p "${PLUGIN_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com;
  docker push $prefix"cookbook-"${version} ;
  docker rmi $prefix"cookbook-"${version} ;
else
  # build server
  docker  build  -f db-metadata-server-demo/Dockerfile  --rm=true  -t $prefix"server-"${version} ./db-metadata-server-demo ;

  # build web
  docker  build -f db-metadata-web/Dockerfile --rm=true  -t $prefix"web-"${version} ./db-metadata-web ;

  #登录凭证
  docker login -u "${PLUGIN_DOCKER_USERNAME}" -p "${PLUGIN_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com;

  # 推送镜像
  docker push $prefix"server-"${version} ;
  docker push $prefix"web-"${version} ;

  # 清理镜像
  docker rmi $prefix"server-"${version} ;
  docker rmi $prefix"web-"${version} ;
fi

#清理空间
docker system prune -f;

exit 0;
