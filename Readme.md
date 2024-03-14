# 中间件相关脚本

中间件需要的脚本与配置见source文件夹

1.nacos(记得要在数据库上部署相关的sql)
```shell
  docker run --name nacos -p 8848:8849 --privileged=true --restart=always -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=localhost -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=lyh701721 -e MYSQL_SERVICE_DB_NAME=nacos -e MODE=standalone -d nacos/nacos-server:2.0.2
```

2.mysql
```shell
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 --name my_mysql mysql
```

3.seata(未使用nacos管理)
```shell
docker run --net=host --name=seata-server --restart=always  -d seataio/seata-server:latest
```

4.redis
```shell
docker run -it --name myredis -p 6379:6379 -v /mydata/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf -v /mydata/redis/data/:/data -v /mydata/redis/log/redis.log:/var/log/redis.log -d redis:6.2.7 /usr/local/etc/redis/redis.conf
```

5.zk
```shell
docker run --name some-zookeeper -p 2181:2181 --restart always -d zookeeper
```

