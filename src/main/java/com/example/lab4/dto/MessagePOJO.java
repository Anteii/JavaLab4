package com.example.lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePOJO {
    Map<String, String> object1;
    Map<String, String> object2;
    String eventType;
    String table;
    Date date;
}
