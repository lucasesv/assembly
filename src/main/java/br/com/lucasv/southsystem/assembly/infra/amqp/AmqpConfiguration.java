package br.com.lucasv.southsystem.assembly.infra.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configure the AMQP and RabbitMQ.
 * 
 * @author Lucas Vasconcelos
 *
 */
@Configuration
@EnableScheduling
public class AmqpConfiguration {
  
  @Value("${amqp.assembly.exchange-name}")
  private String assemblyExchange;
  
  @Value("${amqp.assembly.queue-name}")
  private String votingResultQueue;
  
  @Value("${amqp.assembly.routing-key-voting-result}")
  private String votingResultRoutingKey;
  
  /**
   * Configure the main exchange for the assembly application.
   * 
   * @return The main exchange.
   */
  @Bean
  public TopicExchange assemblyExchange() {
    return new TopicExchange(assemblyExchange);
  }

  /**
   * Configure the queue for voting result.
   * 
   * @return The queue for voting result.
   */
  @Bean
  public Queue assemblyQueue() {
    return new Queue(votingResultQueue, false);
  }
  
  /**
   * Configure the binding between the assembly exchange and
   * the voting result queue routed by a voting result
   * routing key.
   * 
   * @param queue The queue to make the binding.
   * @param exchange The exchange to make the binding
   * @return The voting result binding between the assembly exchange and
   *        the voting result queue routed by a voting result routing key.
   */
  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue)
        .to(exchange)
        .with(votingResultRoutingKey);
  }
  
  /**
   * Configure a {@link RabbitTemplate} bean to publish messages via AMQP.
   * Also sets a JSON converter in order to convert objects in JSON before
   * send the messages.
   * 
   * @param connectionFactory
   * @return
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }
  
}
