package util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerDemo {

    //需要在Kafka中先创建该topic
    public static final String topicName = "ufo-test-topic1";

    public static void main(String[] args) {

        // 默认本地kafka
        String bootstrapServersAddr = "localhost:9092";
        /**
         * 获取入参中的 KAFKA broker地址
         * args长度为1：localhost:9092
         * args长度为2：localhost 9092
         */
        if(args.length == 1){
            bootstrapServersAddr = args[0];
        }else if (args.length ==2){
            bootstrapServersAddr = args[0] + ":" +args[1];
        }
        System.out.println("The bootstrap Servers Address is: " + bootstrapServersAddr);

        Properties kafkaProps = new Properties();
        /**
         * acks指定了必须要有多少个分区副本收到消息，生产者才会认为消息写入成功。
         * acks=0,生产者在写入消息之前不会等待任何来自服务器的响应；就算发送失败了，生产者也不知道。
         * acks=1,只要集群首领收到消息，生产者就会收到一个来自服务器的成功消息
         * acks=all，所有参与复制的节点都收到消息，生产者才会收到一个来自服务器的成功响应。
         */
        kafkaProps.put("acks", "all");
        /**
         * 发送失败后重发的次数，最终还不成功表示发送彻底的失败
         */
        kafkaProps.put("retries", 0);
        /**
         * 默认情况下，消息发送时不会被压缩。
         * snappy:压缩算法由Google发明，它占用较少的CPU,却能提供较好的性能和相当可观的压缩比
         * gzip:占用较多的CPU,但是提供更高的压缩比，带宽比较有限，可以考虑这个压缩算法。
         * 使用压缩可以降低网络传输开销和存储开销，而这往往时向kafka发送消息的瓶颈
         */
        kafkaProps.put("compression.type", "snappy");
        /**
         * 一个批次可以使用的内存大小；当批次被填满，批次里的所有消息会被发送；不过生产者并不一定等批次被填满才发送；
         * 所以批次大小设置得很大，也不会造成延迟，只是会占用更多得内存而已。但是设置得太小，
         * 因为生产者需要更频繁的发送消息，会增加额外的开销。
         */
        kafkaProps.put("batch.size", 100);
        /**
         * 指定了生产者在发送批次之前等待更多消息加入批次的时间。
         * KafkaProducer会在批次填满或liner.ms达到上限时把批次发送出去。
         * 这样做虽然会出现一些延时，但是会提高吞吐量。
         */
        kafkaProps.put("linger.ms", 1);
        /**
         * 生产者内存缓冲区的大小，生产者用它缓冲要发送到服务器的消息。
         * 如果应用程序发送消息的速度超过发送到服务器的速度，会导致生产者空间不足，
         * 这个时候send()方法要么被阻塞，要么抛出异常。
         */
        kafkaProps.put("buffer.memory", 33554432);
        /**
         * 生产者在收到服务器响应之前可以发送多少个消息。
         * 值越高就会占用越多的内存，不过也会提升吞吐量。
         * 设为1可以保证消息是按照发送顺序填写入服务器的，即使发生了重试。
         */
        kafkaProps.put("max.in.flight.requests.per.connection", 1);
        //kafkaProps.put("bootstrap.servers","192.168.123.128:9092,192.168.123.129:9092,192.168.123.130:9092");
        //主机信息（broker）

        kafkaProps.put("bootstrap.servers", bootstrapServersAddr);
        //键为字符串类型
        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //值为字符串类型
        kafkaProps.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");


        Producer<String,String> producer=new KafkaProducer<String, String>(kafkaProps);

        //如果你想要不停发送不同数据，可以给send方法套一个循环
        String msg = "mo shi mo shi , I am Sanji";

        producer.send(new ProducerRecord<String, String>(topicName, msg));
        System.out.println("Sent:" + msg);
        producer.close();
    }
}
