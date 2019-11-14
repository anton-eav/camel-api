package com.common.camel.service;

import com.common.camel.controller.dee;
import com.common.camel.model.HttpResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.ObjectExpression;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class CamelService {
    List <RouteBuilder> camelContexts = new ArrayList<>();

    CamelContext context;

    @PostConstruct
    public void init(){
        context = new DefaultCamelContext();
    }

    public boolean setNewRoute() {
        try {
            //		SimpleRegistry simpleRegistry = new SimpleRegistry(); // this class is just a mapper
//		simpleRegistry.put("spring", RestConsumerFactory.class);

//		ConsumerTemplate ct = context.createConsumerTemplate();
//		ct.receive("http:localhost:8080/start"); // direct

//		ProducerTemplate template = context.createProducerTemplate();
//		template.sendBody("http:localhost:8080/start", "This is a test message");
// adding routes to camel context

            try {
                context.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
//                        from("direct:start").to("seda:end");
                        from("seda:camel/dee")
//                            .log("camel/dee")
//                            .serviceCall("foo", "http:localhost:8080/camel/dee")
                                .to("mock:result");
                    }
                });

                Processor myCustomProcessor = new Processor() {
                    public void process(Exchange exchange) throws IOException {
                        // implement your custom processing
//                        List<dee> d = (List)exchange.getIn().getBody();

                        ObjectMapper mapper = new ObjectMapper();

                        String json = exchange.getIn().getBody(String.class);
                        List<dee> ppl2 = Arrays.asList(mapper.readValue(json, dee[].class));
                        log.info("s");

                        exchange.getIn().setBody(ppl2);
                    }
                };
                context.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {

//                        from("direct:start")
//                                .setHeader("CamelHttpMethod", constant("GET"))
//                                .to("http:localhost:8080/camel/dee")
//                                .log("${body}");
                        restConfiguration().host("localhost").port(8080).producerComponent("http");
//                        from("timer:hello?period=2000")
                        from("direct:start")
                                .setHeader("id", simple("${random(1,3)}"))
                                .to("rest:get:camel/dee")
//                                .log("${body}")
//                                .marshal().json(JsonLibrary.Jackson)
                                .process(myCustomProcessor)
                                .to("class:com.common.camel.model.HttpResponseBean?method=processResponse");
//                                .process(exchange -> {
//                                    log.info("Body = [{}]", exchange.getIn().getBody());
//                                });

//                        from("jetty:http://localhost:8080/camel/dee")
//                                .process(exchange -> {
//                                    log.info("Body = [{}]", exchange.getIn().getBody());
//                                });

//                                .bean("class:com.common.camel.model.HttpResponseBean?method=processResponse");

//                    restConfiguration()
//                            .host("localhost").port(8081)
//                            .component("jetty");
////                            .producerComponent("http")
////                            .bindingMode(RestBindingMode.json);
//                    from("rest:get:hello/{me}")
//                            .setHeader(Exchange.HTTP_PATH, constant("/camel/dee"))
//                            .to("http:localhost:8080");
//                            .to("rest:get:?host=localhost:8080/camel/dee&bridgeEndpoint=true");
//                                .transform().simple("Hi ${header.me}")
//                                .to("jetty:http://localhost:8080/camel/dee?bridgeEndpoint=true");
//                                .to("mock:results");
//                                .toD("seda:camel/dee");
                    }
                });
                context.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // producer template
//		ProducerTemplate producerTemplate = context.createProducerTemplate();
//		producerTemplate.sendBody("direct:start", "This message sent by producer");

            // consumer template
//            ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
//            String message = consumerTemplate.receiveBody("seda:end", String.class);
//
//            System.out.println(message);

//
//		Processor myProcessor = new Processor() {
//			public void process(Exchange exchange) {
//				log.debug("Called with exchange: " + exchange);
//			}
//		};
//
//		RouteBuilder builder = new RouteBuilder() {
//			public void configure() {
//				from("direct:start").to("https://www.google.ru").process(myProcessor);;
//			}
//		};
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            return false;
        }
    }


    public void sss(){
        try {
            ProducerTemplate producerTemplate = context.createProducerTemplate();
            // set the defautlEndPoint
            producerTemplate.setDefaultEndpointUri("direct:start");
            producerTemplate.requestBody("s");


//            ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
//            String c = consumerTemplate.receiveBody("http:localhost:8080/camel/dee", String.class);
//            log.info("message = [{}]", c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List <RouteBuilder> getListContext(){
        return camelContexts;
    }
}
