#!/bin/bash

#版本
version=$1

## 部署目标: code/doc
type=$(echo $2 | sed 's/\\n.*//g')

prefix="registry.cn-hangzhou.aliyuncs.com/hthj_asoco/db-metadata:"

echo "type:${type}"
echo "version:${version}"
echo "prefix:${prefix}"

if [ ${type} == 'cookbook' ]; then
  echo "部署 db-metadata-cookbook:"
  bash /bin/update.sh db-metadata-cookbook db-metadata-cookbook ${prefix}"cookbook-" ${version}
  echo "db-metadata-cookbook 部署完成"

else
  echo "部署 db-metadata-server:"
  bash /bin/update.sh db-metadata-server db-metadata-server ${prefix}"server-" ${version}
  echo "db-metadata-server 部署完成"

  echo "部署 db-metadata-web:"
  bash /bin/update.sh db-metadata-web db-metadata-web ${prefix}"web-" ${version}
  echo "db-metadata-web 部署完成"

fi

#清理空间
exit 0;
