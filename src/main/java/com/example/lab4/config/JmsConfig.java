package com.example.lab4.config;

// Importing required classes
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

// Annotations
@Configuration

// Class
public class JmsConfig {

    // Class data member
    public static final String QUEUE = "greet";
    public static final String bookTableChangeQueue = "bookTableChange";
    public static final String clientTableChange = "clientTableChange";
    public static final String purchaseTableChange = "PurchaseTableChange";
    public static final String DB_CHANGE_QUEUE = "dbChange";

    // Annotation
    @Bean

    // Class
    public MessageConverter messageConverter()
    {

        MappingJackson2MessageConverter converter
                = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

    // Enabling spring to take jms messages and flip it
    // to a json message. then it can read
    // that jms message as a jms text message and
    // convert it back to java object
}
