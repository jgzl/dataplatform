#!/bin/sh
ENVIRONMENT="$1"

SOURCE="$0"
while [ -h "$SOURCE"  ]; do
    DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
    SOURCE="$(readlink "$SOURCE")"
    [[ $SOURCE != /*  ]] && SOURCE="$DIR/$SOURCE"
done
DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
echo $0 '脚本地址上层目录为' $DIR

# 镜像打包命名需要如下参数
commit_id=$(git rev-parse --short HEAD)
current_time=`date '+%Y%m%d%H%M%S'`

if [ -z $IMAGE_VERSION ]; then
  export IMAGE_VERSION=$commit_id"-"$current_time
fi

build_frontend() {
  cd $DIR/admin-ui/
  echo '开始删除数据中台管理服务前端包'
  rm -rf dist
  echo '结束删除数据中台管理服务前端包'
  yarn
  yarn build
}

build_backend() {
  cd $DIR
  echo '开始打包后端包'
  mvn clean package -P${ENVIRONMENT} -Dmaven.test.skip=true
  echo '结束打包后端包'
}

build_push_docker() {
  cd $DIR
  echo '开始打包推送镜像,version为:'$IMAGE_VERSION
  export DOCKER_BUILDKIT=1

  if [ -z $IMAGE_REPO_URL ]
  then
    export IMAGE_REPO_URL=docker.io/dlhf
    echo "IMAGE_REPO_URL:"$IMAGE_REPO_URL
  else
    echo "IMAGE_REPO_URL:"$IMAGE_REPO_URL
  fi

  docker-compose -f docker-compose-build.yaml build
  docker-compose -f docker-compose-build.yaml push
  IMAGE_VERSION=latest
  docker-compose -f docker-compose-build.yaml build
  docker-compose -f docker-compose-build.yaml push
  echo '结束打包推送镜像,version为:'$IMAGE_VERSION
}

build_frontend
build_backend
build_push_docker