package com.example.lab4.controller.api;

import com.example.lab4.dto.PurchaseDto;
import com.example.lab4.model.Purchase;
import com.example.lab4.repository.PurchaseRepository;
import com.example.lab4.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/purchases", produces = {"application/json", "application/xml"})
public class PurchaseController {
    final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{id}")
    public Purchase findById(@PathVariable Integer id){
        return purchaseService.findById(id);
    }

    @GetMapping("/all")
    public List<Purchase> findAll(){
        return purchaseService.findAll();
    }

    @PostMapping("/update")
    public void update(@RequestBody Purchase purchase){
        purchaseService.update(purchase.getId(), purchase.getBookId(), purchase.getClientId(), purchase.getAmount());
    }

    @PostMapping("/create")
    public Purchase create(@RequestBody PurchaseDto purchaseDto){
        return purchaseService.create(purchaseDto.getBookId(), purchaseDto.getClientId(), purchaseDto.getAmount());
    }

    @DeleteMapping("/delete/{id}")
    public Purchase delete(@PathVariable Integer id){
        return purchaseService.delete(id);
    }
}
