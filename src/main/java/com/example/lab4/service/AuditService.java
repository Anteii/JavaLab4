package com.example.lab4.service;

import com.example.lab4.config.JmsConfig;
import com.example.lab4.model.Mappable;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;

@Service
public class AuditService {


    public static String EVENT_CREATE = "Create";
    public static String EVENT_UPDATE = "Update";
    public static String EVENT_DELETE = "Delete";
    private final JmsTemplate jmsTemplate;

    public AuditService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @SneakyThrows
    public void log(Mappable object, String table, String eventType) {
        Topic springTopic = jmsTemplate.getConnectionFactory().createConnection()
                .createSession().createTopic(JmsConfig.CHANGE_TOPIC);

        jmsTemplate.convertAndSend(springTopic, object.toMap(), message -> setHeaders(message, table, eventType));
    }

    public void log(Mappable objectOld, Mappable objectNew, String table, String eventType) {

        Map<String, String> oldData = objectOld.toMap();
        Map<String, String> newData = objectNew.toMap();

        Map<String, String> mergedData = new HashMap<>();

        for (String key : objectOld.toMap().keySet()) {
            mergedData.put(key + "_old", oldData.get(key));
            mergedData.put(key + "_new", newData.get(key));
        }

        log(() -> mergedData, table, eventType);
    }

    private Message setHeaders(Message message, String table, String eventType) throws JMSException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        message.setStringProperty("table", table);
        message.setStringProperty("eventType", eventType);
        message.setStringProperty("date", formatter.format(new Date()));
        return message;
    }

}
