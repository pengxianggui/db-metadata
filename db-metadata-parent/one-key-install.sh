#!/usr/bin/env bash
#
# author : konbluesky
# date : 2019-12-23
#

cd $(dirname $0)

function main() {
  #check work dir
  if [[ -d "/root/work/db-metadata" ]]; then
    cd /root/work/db-metadata
    git pull
  else
    cd /root/work
    git clone http://36.22.178.70:10080/konbluesky/db-metadata.git
    cd db-metadata
    echo " db-metadata Downloaded "
  fi

  cd db-metadata-parent
  #edit config
  cp -f config/* ../db-metadata-web-jfinal/src/main/resources/
  #package all project
  mvn clean package -Dmaven.test.skip=true

}
