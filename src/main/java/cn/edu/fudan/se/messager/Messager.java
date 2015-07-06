package cn.edu.fudan.se.messager;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public abstract class Messager {
//    private static final String NS_IP = "10.131.252.221";
    private static final String NS_IP = "127.0.0.1";
    private static final int NS_PORT = 9876;
    private static final String NS_IP_PORT = String.format("%s:%d", NS_IP, NS_PORT);

    private String topic;
    private DefaultMQPushConsumer consumer;
    private DefaultMQProducer producer;

    protected Messager(String topic, String consumerGroup, String producerGroup) {
        this.topic = topic;
        initProducer(producerGroup);
        initConsumer(consumerGroup);
    }

    private void initProducer(String producerGroup) {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(NS_IP_PORT);
        producer.setInstanceName(producerGroup + System.currentTimeMillis());
    }

    private void initConsumer(String consumerGroup) {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(NS_IP_PORT);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setInstanceName(consumerGroup + System.currentTimeMillis());
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                MessageExt message = list.get(0);
                String tag = message.getTags();
                String messageId = message.getMsgId();
                Serializable messageBody = bytes2Bean(message.getBody());
                log("[receive]id:" + messageId + " body:" + messageBody);
                return onReceiveMessage(messageId, messageBody,tag) ?
                        ConsumeConcurrentlyStatus.CONSUME_SUCCESS :
                        ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            private Serializable bytes2Bean(byte[] bytes) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (Serializable) in.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    protected void start(String subscribeTag) {
        try {
            consumer.subscribe(topic, subscribeTag);
            consumer.start();
            producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stop() {
        consumer.shutdown();
        producer.shutdown();
    }

    protected <Bean extends Serializable> SendResult sendMessage(String tag, String key, Bean body) {
        SendResult result = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(body);
            out.close();
            Message message = new Message(topic, tag, key, bytes.toByteArray());
            result = producer.send(message);
            log("[sendMessage]id:" + result.getMsgId() + " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected abstract boolean onReceiveMessage(String messageId, Object messageBody,String tag);

    protected void log(String message) {
        System.out.printf("[%tT]%s\n", new Date(), message);
    }
}
