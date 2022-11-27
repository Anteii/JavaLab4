package com.example.lab4.controller.xslt;

import com.example.lab4.model.Purchase;
import com.example.lab4.repository.PurchaseRepository;
import com.example.lab4.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;


@Controller
@RequestMapping("/xslt/purchases")
public class PurchaseControllerXslt {

    final PurchaseService purchaseService;

    public PurchaseControllerXslt(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ModelAndView get() throws JsonProcessingException {
        List<Purchase> clients = purchaseService.findAll();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(clients);

        Source source = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
        ModelAndView modelAndView = new ModelAndView("purchases");
        modelAndView.addObject("xmlSource", source);

        return modelAndView;
    }
}