package com.common.camel.controller;

import com.common.camel.service.CamelService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/camel")
public class CamelController {

    @Autowired
    CamelService camelService;

    @PostMapping("/set")
    public void setNewRoute(){
        camelService.setNewRoute();
    }


    @PostMapping("/ProducerTemplate")
    public void setNewRoutd(){
        camelService.sss();
    }


    @GetMapping
    public List <RouteBuilder> getListContext(){
        return camelService.getListContext();
    }

    @GetMapping("/dee")
    public List <dee> dee(){
        return new ArrayList<dee>(){{
            add(new dee("11", "22"));
            add(new dee("33", "44"));
        }};
    }
}

