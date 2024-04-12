package rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.slack.name}")
    private String queue;

    @Value("${rabbitmq.exchange.slack.name}")
    private String exchange;

    @Value("${rabbitmq.routing.slack.key}")
    private String routingKey;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public org.springframework.amqp.core.Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

}