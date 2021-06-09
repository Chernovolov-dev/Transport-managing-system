package ua.nure.diploma.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryRequest")
public class DeliveryRequestController {

    @GetMapping("/all")
    public String getAllDeliveryRequests(){
        return "You are on a manager request page";
    }

}
