package com.example.lab4.service;

import com.example.lab4.config.JmsConfig;
import com.example.lab4.dto.MessagePOJO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Service
public class NotificationService {

    final private EmailService emailService;
    final private JMSParserService jmsParserService;

    public NotificationService(EmailService emailService, JMSParserService jmsParserService) {
        this.emailService = emailService;
        this.jmsParserService = jmsParserService;
    }

    @JmsListener(destination = JmsConfig.CHANGE_TOPIC)
    public void listen(@Payload Map<String, String> payload, @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException, ParseException {

        MessagePOJO parsedMessage = jmsParserService.parse(payload, messageHeaders, message);
        String eventType = parsedMessage.getEventType();

        switch (eventType) {
            case "Create" -> sendCreateNotification(parsedMessage.getObject1(), parsedMessage.getTable(), parsedMessage.getDate());
            case "Delete" -> sendDeleteNotification(parsedMessage.getObject1(), parsedMessage.getTable(), parsedMessage.getDate());
            case "Update" -> sendUpdateNotification(parsedMessage.getObject1(), parsedMessage.getObject2(), parsedMessage.getTable(), parsedMessage.getDate());
        }
    }

    public void sendCreateNotification(Map<String, String> object, String table, Date date) {

        String subject = "New object has been created in table '" + table + "'";
        String text = buildCreateEventText(object, table, date);

        emailService.sendEmail(subject, text);
    }

    public void sendDeleteNotification(Map<String, String> object, String table, Date date) {

        String subject = "Object has been deleted from table '" + table + "'";
        String text = buildDeleteEventText(object, table, date);

        emailService.sendEmail(subject, text);
    }

    public void sendUpdateNotification(Map<String, String> objectNew, Map<String, String> objectOld,
                                       String table, Date date) {

        String subject = "Object has been updated in table '" + table + "'";
        String text = buildUpdateEventText(objectNew, objectOld, table, date);

        emailService.sendEmail(subject, text);
    }

    private String buildCreateEventText(Map<String, String> object, String table, Date date){

        return "New entry has been created in table '" + table + "'" + "\r\n" +
                stringifyObject(object) +
                "Date of change: " + date.toString();
    }

    private String buildDeleteEventText(Map<String, String> object, String table, Date date){
        return "Object has been deleted from table '" + table + "'" + "\r\n" +
                stringifyObject(object) +
                "Date of change: " + date.toString();
    }

    private String buildUpdateEventText(Map<String, String> objectNew, Map<String, String> objectOld, String table, Date date){
        return "Object has been updated in table '" + table + "'" + "\r\n" +
                "Old values: " + stringifyObject(objectOld) +
                "New values: " + stringifyObject(objectNew) +
                "Date of change: " + date.toString();
    }

    private String stringifyObject(Map<String, String> object) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<String, String> entry : object.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        builder.append("}").append("\r\n");

        return builder.toString();
    }
}
