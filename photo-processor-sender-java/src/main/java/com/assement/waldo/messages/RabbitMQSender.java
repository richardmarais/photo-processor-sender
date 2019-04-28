package com.assement.waldo.messages;

import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchangename}")
	private String exchange;
	
	@Value("${rabbitmq.routingkeyname}")
	private String routingkey;	
	
	public void send(UUID uuid) {
		rabbitTemplate.convertAndSend(exchange, routingkey, uuid);
		System.out.println("Send RabbitMQ msg = " + uuid+" ("+exchange+" "+routingkey+")");	    
	}
}
