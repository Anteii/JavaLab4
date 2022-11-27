package com.example.lab4.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class NotificationService {

    final private EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
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
