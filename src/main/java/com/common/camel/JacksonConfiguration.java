package com.common.camel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfiguration {

    private static ZoneOffset zoneOffset;

    static {
        Instant instant = Instant.now();
        ZoneId zoneId = TimeZone.getTimeZone("Europe/Moscow").toZoneId();
        zoneOffset = zoneId.getRules().getOffset(instant);
    }

    private static OffsetDateTime getOffsetWithTimeZone(OffsetDateTime offsetDateTime) {
        return offsetDateTime.withOffsetSameInstant(zoneOffset);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer offsetDateTimeCustomMapper() {
        return builder -> {
            builder.serializerByType(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
                @Override
                public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(getOffsetWithTimeZone(value)));
                }
            });
            builder.deserializerByType(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
                @Override
                public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
                    return OffsetDateTime.parse(parser.getText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                }
            });
        };
    }

}
