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
  echo "构建cookbook镜像..."
  # build cookbook
  docker  build -f cookbook/Dockerfile --rm=true  -t $prefix"cookbook-"${version} ./cookbook ;

  echo "登录镜像仓库..."
  #登录凭证
  docker login -u "${PLUGIN_DOCKER_USERNAME}" -p "${PLUGIN_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com;
  echo "推送镜像..."
  docker push $prefix"cookbook-"${version} ;
  echo "删除本地镜像..."
  docker rmi $prefix"cookbook-"${version} ;
else
  echo "构建server镜像..."
  # build server
  docker  build  -f db-metadata-server-demo/Dockerfile  --rm=true  -t $prefix"server-"${version} ./db-metadata-server-demo ;

  echo "构建web镜像..."
  # build web
  docker  build -f db-metadata-web/Dockerfile --rm=true  -t $prefix"web-"${version} ./db-metadata-web ;

  echo "登录镜像仓库..."
  docker login -u "${PLUGIN_DOCKER_USERNAME}" -p "${PLUGIN_DOCKER_PASSWORD}" registry.cn-hangzhou.aliyuncs.com;

  echo "推送server镜像..."
  docker push $prefix"server-"${version} ;

  echo "推送web镜像..."
  docker push $prefix"web-"${version} ;

  # 清理镜像
  echo "清理本地server镜像..."
  docker rmi $prefix"server-"${version} ;
  echo "清理本地web镜像..."
  docker rmi $prefix"web-"${version} ;
fi

#清理空间
docker system prune -f;

exit 0;
