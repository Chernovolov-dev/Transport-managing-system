package ua.nure.diploma.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routeSheet")
public class RouteSheetController {

    @GetMapping("/manage")
    public String getRouteSheetPanel(){

        return "We are on the routeSheet panel page";
    }
}
