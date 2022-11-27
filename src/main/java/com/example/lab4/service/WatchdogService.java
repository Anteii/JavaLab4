package com.example.lab4.service;

import com.example.lab4.config.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WatchdogService {

    private final NotificationService notificationService;
    private final DBLoggerService dbLoggerService;
    

    public WatchdogService(NotificationService notificationService, DBLoggerService dbLoggerService) {
        this.notificationService = notificationService;
        this.dbLoggerService = dbLoggerService;
    }

    @JmsListener(destination = JmsConfig.DB_CHANGE_QUEUE)
    public void listen(@Payload Map<String, String> payload, @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException, ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        String table = message.getStringProperty("table");
        String eventType = message.getStringProperty("eventType");
        Date date = formatter.parse(message.getStringProperty("date"));

        if (eventType.equals("Create") || eventType.equals("Delete")){
            Map<String, String> object = extract(payload, "");

            if ("Create".equals(eventType)) {
                //notificationService.sendCreateNotification(object, table, date);
                dbLoggerService.logCreateEvent(object, table, date);
            } else {
                //notificationService.sendDeleteNotification(object, table, date);
                dbLoggerService.logDeleteEvent(object, table, date);
            }
        }
        else if (eventType.equals("Update")){
            Map<String, String> objectNew = extract(payload, "_new");
            Map<String, String> objectOld = extract(payload, "_old");
            //notificationService.sendUpdateNotification(objectNew, objectOld, table, date);
            dbLoggerService.logUpdateEvent(objectNew, objectOld, table, date);
        }
    }


    private Map<String, String> extract(Map<String, String> map, String suffix){
        Map<String, String> extractedObject = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()){
            if (entry.getKey().endsWith(suffix)){
                extractedObject.put(entry.getKey().replace(suffix, ""), entry.getValue());
            }
        }

        return extractedObject;

    }
}
