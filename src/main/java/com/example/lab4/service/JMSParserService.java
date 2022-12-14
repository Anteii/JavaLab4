package com.example.lab4.service;

import com.example.lab4.dto.MessagePOJO;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JMSParserService {
    public MessagePOJO parse(Map<String, String> payload, MessageHeaders messageHeaders,
                             Message message) throws JMSException, ParseException {

        MessagePOJO messagePOJO = new MessagePOJO();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        String table = message.getStringProperty("table");
        String eventType = message.getStringProperty("eventType");
        Date date = formatter.parse(message.getStringProperty("date"));

        messagePOJO.setTable(table);
        messagePOJO.setEventType(eventType);
        messagePOJO.setDate(date);

        if (eventType.equals("Create") || eventType.equals("Delete")){
            Map<String, String> object = extract(payload, "");
            messagePOJO.setObject1(object);
        }
        else if (eventType.equals("Update")){
            Map<String, String> objectNew = extract(payload, "_new");
            Map<String, String> objectOld = extract(payload, "_old");

            messagePOJO.setObject1(objectNew);
            messagePOJO.setObject2(objectOld);
        }
        return messagePOJO;
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
