package com.common.camel.model;

import com.common.camel.controller.dee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class HttpResponseBean {

    @Min(3)
    private Integer ааа;

    public void processResponse(Exchange exchange) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String json = exchange.getIn().getBody(String.class);
        List<dee> ppl2 = Arrays.asList(mapper.readValue(json, dee[].class));

    }
}

