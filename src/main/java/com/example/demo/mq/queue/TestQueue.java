package com.example.demo.mq.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * description: 声明,绑定 队列 交换机 routingKey
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@Component
public class TestQueue {
    @Bean
    public Queue dirQueue() {
        return new Queue("direct");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    Binding bindingExchangeDirect(@Qualifier("dirQueue") Queue dirQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(dirQueue).to(directExchange).with("directTest");
    }
}
