package cn.itcast.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-rabbitmq-consumer-quart.xml");
        context.start();

    }
}
