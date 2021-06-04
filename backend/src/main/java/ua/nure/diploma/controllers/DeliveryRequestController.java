package ua.nure.diploma.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DeliveryRequestController {

    @GetMapping("deliveryRequest/all")
    public String getAllRequests(){
        return "You are on a manager request page";
    }

}
