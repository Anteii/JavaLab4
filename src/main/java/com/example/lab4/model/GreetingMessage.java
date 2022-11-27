package com.example.lab4.model;

// Importing required classes
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotation
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// Class
// Implementing Serializable interface
public class GreetingMessage implements Serializable {

    // Class data members
    static final long serialVersionUID
            = -7462433555964441775L;
    private UUID id;
    private String message;
}
