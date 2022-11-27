package com.example.lab4.controller.api;

import com.example.lab4.dto.ClientDto;
import com.example.lab4.model.Client;
import com.example.lab4.repository.ClientRepository;
import com.example.lab4.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clients", produces = {"application/json", "application/xml"})
public class ClientController {

    final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable Integer id){
        return clientService.findById(id);
    }

    @GetMapping("/all")
    public List<Client> findAll(){
        return clientService.findAll();
    }

    @PostMapping("/update")
    public void update(@RequestBody Client client){
        clientService.update(client.getId(), client.getName(), client.getCity(), client.getEmail());
    }

    @PostMapping("/create")
    public Client create(@RequestBody ClientDto clientDto){
        return clientService.create(clientDto.getName(), clientDto.getCity(), clientDto.getEmail());
    }

    @DeleteMapping("/delete/{id}")
    public Client delete(@PathVariable Integer id){
        return clientService.delete(id);
    }
}