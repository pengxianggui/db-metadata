#!/usr/bin/env bash
#
# author : konbluesky
# date : 2019-12-23
#

# builder jfinal jar
set -x
echo  -e "build web frontend"
cd ../db-metadata-web-iview
yarn install
yarn build
if [ -d "dist" ]; then
  echo -e "db metadata build finished."
  cp -R -f dist ../db-metadata-web-jfinal/target/webapp
fi
echo -e "Copy [webapp] and [metadata-server.jar] of together to your website."
#mkdir -p ../db-metadata-web-jfinal/target/classes/webapp;
#cp -R ./dist/index.html ../db-metadata-web-jfinal/target/classes/webapp
#cp -R ./dist/favicon.ico ../db-metadata-web-jfinal/target/classes/webapp
#cp -R ./dist/static ../db-metadata-web-jfinal/target/classes/webapp
#mvn -f ../db-metadata-web-jfinal/pom.xml clean package -Dmaven.test.skip=true
#mvn -f ../db-metadata-web-jfinal/pom.xml clean package -Dmaven.test.skip=true
