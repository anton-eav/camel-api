package com.common.camel.bean;

import com.common.camel.controller.dee;
import com.common.camel.model.HttpResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                log.info(json);
                List<dee> ppl2 = new ArrayList<>(Arrays.asList(mapper.readValue(json, dee[].class)));
                ppl2.add(new dee("55", "66"));
                exchange.getOut().setBody(mapper.writeValueAsString(ppl2));
            }
        };

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("jetty:http://0.0.0.0:8081/600rr??matchOnUriPrefix=true")
                            .to("jetty:http://localhost:8080/camel/dee?bridgeEndpoint=true")
                            .process(myCustomProcessor);
                }
            });

//            route>
//    <from uri="jetty:http://0.0.0.0:8080/myapp?matchOnUriPrefix=true"/>
//    <to uri="jetty:http://realserverhostname:8090/myapp?bridgeEndpoint=true&amp;throwExceptionOnFailure=false"/>
//  </route>
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
