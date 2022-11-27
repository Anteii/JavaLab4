package com.example.lab4.service;


import com.example.lab4.model.Client;
import com.example.lab4.model.Purchase;
import com.example.lab4.repository.ClientRepository;
import com.example.lab4.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    final PurchaseRepository purchaseRepository;
    final AuditService auditService;

    public PurchaseService(PurchaseRepository purchaseRepository, AuditService auditService) {
        this.purchaseRepository = purchaseRepository;
        this.auditService = auditService;
    }

    public Purchase findById(Integer id){
        return purchaseRepository.findById(id).orElseThrow();
    }

    public Purchase create(Integer bookId, Integer clientId, Integer amount) {
        Purchase purchase = purchaseRepository.saveAndFlush(new Purchase(bookId, clientId, amount));

        auditService.log(purchase, "purchase", AuditService.EVENT_CREATE);


        return purchase;
    }

    public Purchase update(Integer id, Integer bookId, Integer clientId, Integer amount) {
        Purchase purchaseOld = purchaseRepository.findById(id).orElseThrow();
        Purchase purchaseNew = new Purchase(id, bookId, clientId, amount);

        auditService.log(purchaseOld, purchaseNew, "purchase", AuditService.EVENT_UPDATE);

        return purchaseRepository.saveAndFlush(purchaseNew);
    }

    public Purchase delete(Integer id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow();
        purchaseRepository.delete(purchase);

        auditService.log(purchase, "purchase", AuditService.EVENT_DELETE);

        return purchase;
    }

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }

}
