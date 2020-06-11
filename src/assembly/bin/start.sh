#!/bin/bash

#JAVA_HOME=/opt/java/jdk1.8.0_101
#PATH=$JAVA_HOME/bin:$PATH

cd `dirname $0`
HOME=`pwd`
cd ..

DEPLOY_DIR=`pwd`

STDOUT_FILE=$DEPLOY_DIR/stdout.log
MAINWAR=$DEPLOY_DIR/bin/bootstrap.jar
MODEL=-Dspring.profiles.active=prod
MODEL="$MODEL -Dloader.path=lib,config"
MODEL="$MODEL -Dapplication.name=$DEPLOY_DIR"

#JAVA_OPTS="-Xmn1024m -Xms2048m -Xmx2048m -XX:+UseCompressedOops -XX:+AlwaysPreTouch -XX:AutoBoxCacheMax=5000"
JAVA_OPTS="-Xms80m -Xmx80m -XX:+UseCompressedOops -XX:+AlwaysPreTouch -XX:AutoBoxCacheMax=200"
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError"

echo java -jar $JAVA_OPTS $MODEL $MAINWAR $STDOUT_FILE 2>&1 &
java -jar $JAVA_OPTS $MODEL $MAINWAR > $STDOUT_FILE 2>&1 &

while [ -z "$PROCESS" ]; do
    sleep 2
    PROCESS=`ps axfww | grep "$MAINWAR" | grep "$DEPLOY_DIR" | grep -v grep`
done

echo OK!
PIDS=`ps -C java -f --width 1000 | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo $PIDS > run.pid
echo "PID: $PIDS"
tail -f $STDOUT_FILE