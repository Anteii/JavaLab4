package com.example.lab4.service;

import com.example.lab4.model.Book;
import com.example.lab4.model.Client;
import com.example.lab4.repository.BookRepository;
import com.example.lab4.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    final ClientRepository clientRepository;
    final AuditService auditService;

    public ClientService(ClientRepository clientRepository, AuditService auditService) {
        this.clientRepository = clientRepository;
        this.auditService = auditService;
    }

    public Client findById(Integer id){
        return clientRepository.findById(id).orElseThrow();
    }

    public Client create(String name, String city, String email) {
        Client client = clientRepository.saveAndFlush(new Client(name, city, email));

        auditService.log(client, "client", AuditService.EVENT_CREATE);

        return client;
    }

    public Client update(Integer id, String name, String city, String email){
        Client clientOld = clientRepository.findById(id).orElseThrow();
        Client clientNew = new Client(clientOld.getId(), name, city, email);

        auditService.log(clientOld, clientNew, "client", AuditService.EVENT_UPDATE);

        return clientRepository.saveAndFlush(clientNew);
    }

    public Client delete(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);

        auditService.log(client, "client", AuditService.EVENT_DELETE);


        return client;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

}
