#!/usr/bin/env bash
#
# author : konbluesky
# date : 2019-12-23
#

# builder jfinal jar
set -x
cd ../db-metadata-web-iview
yarn install
yarn build
if [ -d "dist" ]; then
  echo -e "db metadata build finished."
fi
echo -e "not finished"
#mkdir -p ../db-metadata-web-jfinal/target/classes/webapp;
#cp -R ./dist/index.html ../db-metadata-web-jfinal/target/classes/webapp
#cp -R ./dist/favicon.ico ../db-metadata-web-jfinal/target/classes/webapp
#cp -R ./dist/static ../db-metadata-web-jfinal/target/classes/webapp
#mvn -f ../db-metadata-web-jfinal/pom.xml clean package -Dmaven.test.skip=true
#mvn -f ../db-metadata-web-jfinal/pom.xml clean package -Dmaven.test.skip=true
