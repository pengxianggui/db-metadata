#!/bin/bash
# 【pinpoint agent】
# 如果设置pinpoint为false，则不使用pinpoint
# COLLECTOR_IP为pinpoint的ip,如果需要配置其它的pinpoint参数，则参照sed进行匹配修改
# applicationName和agentId为pinpoint上的配置

# 【skywalking agent】
# 如果设置skywalking为true，表示开启skywalking接入
# SW_AGENT_NAME: skywalking中显示的服务名
# SW_GRPC_LOG_SERVER_HOST: skywalking oap server上报地址
# SW_GRPC_LOG_SERVER_PORT: skywalking oap server上报端口

if [ $DEBUG'' = "true" ]; then
    set -x
fi

set -e

jarPath="/app/app.jar"
# stop
echo "begin stop if exist. the process will be killed:"
echo `ps -ef | grep $jarPath | grep -v "grep"`
pid=`ps -ef | grep $jarPath | grep -v "grep" | awk '{print $1}'`
for id in $pid
do
  kill -9 $id
  echo "killed $id"
done

# start
echo "begin start.."
cmd="java $JAVA_OPTS "

if [ $skywalking'' == "true" ]; then
  cmd="$cmd -javaagent:/root/skywalking-agent/skywalking-agent.jar \
  -Dskywalking.agent.service_name=${SW_AGENT_NAME:-"db-metadata"} \
  -Dskywalking.plugin.toolkit.log.grpc.reporter.server_host=${SW_GRPC_LOG_SERVER_HOST:-"asoco-skywalking"} \
  -Dskywalking.plugin.toolkit.log.grpc.reporter.server_port=${SW_GRPC_LOG_SERVER_PORT:-"11800"} \
  -Dskywalking.collector.backend_service=${SW_AGENT_COLLECTOR_BACKEND_SERVICES:-"asoco-skywalking:11800"} "
fi

cd /app # 如果不进入app执行启动命令, 配置文件的加载存在问题(app.jar 采用的是PropertiesLauncher启动的，具体参考: https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar.property-launcher)
cmd="$cmd -Dloader.path=${APP_PLUGIN_DIR:-"/app/plugins"} -jar ${jarPath}"
echo "execute command: $cmd"
eval "$cmd"

exec "$@"
