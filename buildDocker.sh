#!/bin/bash
echo "删除docker中generator:1.0镜像"
docker rmi generator:1.0
echo "删除镜像完毕，执行maven打包"
mvn clean package -DskipTests=true
echo "maven打包完毕"
cp Dockerfile ./target
cd target
echo "执行docker镜像构建"
docker build -t generator:1.0 . -f Dockerfile
echo "docker镜像构建完毕，执行docker镜像导出"
docker save -o generator.tar generator:1.0
echo "docker镜像导出完毕"
cd ..
echo "开始执行镜像拷贝"
scp ./target/generator.tar root@111.229.180.167:/data/docker/generator
echo "远程镜像拷贝完毕"

echo "开始执行远程服务器连接操作"
ssh root@111.229.180.167
