package com.common.camel.bean;

import com.common.camel.controller.dee;
import com.common.camel.model.HttpResponseBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class CamelDemo {

    @PostConstruct
    public void init(){
        CamelContext context = new DefaultCamelContext();
        Processor myCustomProcessor = new Processor() {
            public void process(Exchange exchange) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                String json = exchange.getIn().getBody(String.class);
//                log.info(json);
//                List<dee> ppl2 = new ArrayList<>(Arrays.asList(mapper.readValue(json, dee[].class)));
//                ppl2.add(new dee("55", "66"));
//                exchange.getOut().setBody(mapper.writeValueAsString(ppl2));

                ArrayNode root = (ArrayNode)mapper.readTree(json);
                for(int i = 0; i < root.size(); i++){
//                    log.info(root.get(i).get("code"));

                    ObjectNode on = mapper.createObjectNode();
                    on.set("id", root.get(i).get("code"));
                    on.set("desc", root.get(i).get("name"));
                    root.set(i, on);
                }


//                exchange.getOut().setHeader("Content-Type", "application/json;charset=UTF-8");
                exchange.getOut().setHeaders(exchange.getIn().getHeaders());
                exchange.getOut().setBody(root.toString());
            }
        };

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("jetty:http://0.0.0.0:8081/districts?matchOnUriPrefix=true&httpMethodRestrict=POST")
                            .to("http:10.5.31.72:8803/catalogue/addresses/search" +
                                    "?page=0" +
                                    "&size=10" +
                                    "&httpMethod=POST" +
                                    "&bridgeEndpoint=true");
//                            .process(myCustomProcessor);
                }
            });

//            context.addRoutes(new RouteBuilder() {
//                @Override
//                public void configure() throws Exception {
//                    from("jetty:http://0.0.0.0:8081/config_def?matchOnUriPrefix=true")
//                            .to("file://C:/Users/av.eliseev/Documents/common/camel-api?fileName=config_def.json&bridgeEndpoint=true");
//                }
//            });
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
