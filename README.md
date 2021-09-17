# 帮助手册

## Kafka本地下载

> http://kafka.apache.org/downloads

> 最新版本2.12-2.8.0 ，下载binary版本，直接解压到本地目录即可



## Maven

> Kafka-client用的是 2.8.0



## Kafka环境变量

> KAFKA_HOME 指向本地kafka根目录
>
> 我是windows，mac应该也要，此处不做概述，可查阅baidu



## 本地启动Kafka

> 双开命令行窗口，一个先启动zookeeper，另一个再启动Kafka，(注意顺序)，ZK在Kafka文件夹中有
>
> 启动后两者皆为守护进程，请勿关闭窗口，最小化即可



### windows命令

```json
//切换到kafka根目录下的bin目录下的windows目录
.....\kafka_2.12-2.8.0\bin\windows

.\zookeeper-server-start.bat ..\..\config\zookeeper.properties

.\kafka-server-start.bat ..\..\config\server.properties
```



### mac命令

```json
//切换到kafka根目录下
cd .....\kafka_2.12-2.8.0

bin\zookeeper-server-start.sh config\zookeeper.properties &

bin\kafka-server-start.sh config\server.properties &
```



## 测试前提准备

> 再新开一个窗口

```json
//切换到kafka根目录下bin目录
cd .....\kafka_2.12-2.8.0\bin
```

> 首先要确保 kafka 里要有 topic

- 查看创建topic 列表（注意我是windows，都是.bat，mac请换成.sh）

```
 .\kafka-topics.bat --list --zookeeper localhost:2181
```

- 如果没有的话，可执行以下命令，创建topic

```
 .\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic ufo-test-topic1
```

> 分区数partition可自行设置，可以不用动
>
> 订阅主题名称topic 可自行修改

- 可再次查看创建topic命令，查看topic是否创建成功



## 命令行启动producer和consumer测试功能是否正常

> 生产者和消费者同理，需要新开两个窗口，切换到bin目录

- 启动生产者（注意.bat）

```
 .\kafka-console-producer.bat --broker-list localhost:9092 --topic ufo-test-topic1
```

> 需指定订阅topic名称

- 启动消费者

```
 .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic ufo-test-topic1 --from-beginning
```

> -from-beginning表示从头消费

> 两者同时开着的情况下，在producer端输入任意字符，回车表示发送，在consumer端接收到即可认为成功



# Demo代码使用

> 原理和上述命令行启动一致