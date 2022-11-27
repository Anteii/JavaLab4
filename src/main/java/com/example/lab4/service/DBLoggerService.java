package com.example.lab4.service;

import com.example.lab4.model.*;
import com.example.lab4.repository.AuditRepository;
import com.example.lab4.repository.BookAuditRepository;
import com.example.lab4.repository.ClientAuditRepository;
import com.example.lab4.repository.PurchaseAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DBLoggerService {

    private final AuditRepository auditRepository;
    private final BookAuditRepository bookAuditRepository;
    private final ClientAuditRepository clientAuditRepository;
    private final PurchaseAuditRepository purchaseAuditRepository;

    public void logCreateEvent(Map<String, String> object, String table, Date date){
        switch (table) {
            case "book" -> logBookChange(object, null, "Create", date);
            case "client" -> logClientChange(object, null, "Create", date);
            case "purchase" -> logPurchaseChange(object, null, "Create", date);
        }
    }

    public void logDeleteEvent(Map<String, String> object, String table, Date date){
        switch (table) {
            case "book" -> logBookChange(null, object, "Delete", date);
            case "client" -> logClientChange(null, object, "Delete", date);
            case "purchase" -> logPurchaseChange(null, object, "Delete", date);
        }
    }

    public void logUpdateEvent(Map<String, String> objectNew, Map<String, String> objectOld, String table, Date date){
        switch (table) {
            case "book" -> logBookChange(objectNew, objectOld, "Update", date);
            case "client" -> logClientChange(objectNew, objectOld, "Update", date);
            case "purchase" -> logPurchaseChange(objectNew, objectOld, "Update", date);
        }
    }

    private void logBookChange(Map<String, String> objectNew, Map<String, String> objectOld, String eventType, Date date){

        Book bookOld = Book.fromMap(objectOld);
        Book bookNew = Book.fromMap(objectNew);

        BookAudit bookAudit = new BookAudit(bookOld.getTitle(), bookNew.getTitle(),
                bookOld.getAuthorName(), bookNew.getAuthorName(),
                bookOld.getGenre(), bookNew.getGenre(),
                bookOld.getPrice(), bookNew.getPrice());



        bookAudit = bookAuditRepository.save(bookAudit);

        //add eventType
        DBChange dbChange = new DBChange("book", bookAudit.getId(), eventType, new java.sql.Date(date.getTime()));
        auditRepository.saveAndFlush(dbChange);
    }

    private void logClientChange(Map<String, String> objectNew, Map<String, String> objectOld, String eventType, Date date){
        Client clientOld = Client.fromMap(objectOld);
        Client clientNew = Client.fromMap(objectNew);


        ClientAudit clientAudit = new ClientAudit(clientOld.getName(), clientNew.getName(),
                clientOld.getCity(), clientNew.getCity(),
                clientOld.getEmail(), clientNew.getEmail());

        clientAudit = clientAuditRepository.save(clientAudit);

        DBChange dbChange = new DBChange("client", clientAudit.getId(), eventType, new java.sql.Date(date.getTime()));
        auditRepository.saveAndFlush(dbChange);

    }

    private void logPurchaseChange(Map<String, String> objectNew, Map<String, String> objectOld, String eventType, Date date){

        Purchase purchaseOld = Purchase.fromMap(objectOld);
        Purchase purchaseNew = Purchase.fromMap(objectNew);

        PurchaseAudit purchaseAudit = new PurchaseAudit(purchaseOld.getBookId(), purchaseNew.getBookId(),
                purchaseOld.getClientId(), purchaseNew.getClientId(),
                purchaseOld.getAmount(), purchaseNew.getAmount());

        purchaseAudit = purchaseAuditRepository.save(purchaseAudit);

        DBChange dbChange = new DBChange("purchase", purchaseAudit.getId(), eventType, new java.sql.Date(date.getTime()));
        auditRepository.saveAndFlush(dbChange);

    }

}
