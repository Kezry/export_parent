package cn.itcast.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-rabbitmq-consumer.xml");
        context.start();

    }
}
