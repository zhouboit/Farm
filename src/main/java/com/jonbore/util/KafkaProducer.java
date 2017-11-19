package com.jonbore.util;


import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.security.JaasUtils;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class KafkaProducer{


    private ZkUtils zkUtils = ZkUtils.apply("127.0.0.1:2181", 3000, 3000, JaasUtils.isZkSecurityEnabled());

    @Test
    public void createTopic() {
        Properties properties = new Properties();
        AdminUtils.createTopic(zkUtils, "topic1", 1, 1, properties, RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
    }

    @Test
    public void deleteTopic() {
        AdminUtils.deleteTopic(zkUtils, "topic1");
        zkUtils.close();
    }

    @Test
    public void queryTopic() {
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), "topic1");
        // 查询topic-level属性
        Iterator it = props.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }

    public Producer createProducer() {
        Producer producer = null;
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "127.0.0.1:2181");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        producer = new org.apache.kafka.clients.producer.KafkaProducer(properties);
        return producer;
    }

    @Test
    public void ProducerTest() {
        KafkaProducer kafkaProducer = new KafkaProducer();
        Producer producer = kafkaProducer.createProducer();
        ProducerRecord producerRecord = new ProducerRecord("topic1", "1111", "2222");
        producer.send(producerRecord);
    }

}
