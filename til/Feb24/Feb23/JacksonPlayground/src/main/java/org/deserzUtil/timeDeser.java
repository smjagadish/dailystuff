package org.deserzUtil;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class timeDeser extends StdDeserializer<ZonedDateTime> {
    protected timeDeser()
    {
        this(null);
    }
    protected timeDeser(Class<Instant> vc) {
        super(vc);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        System.out.println("inside time dse");
        String it = jsonParser.getText();
        Instant time_in_text = jsonParser.readValueAs(Instant.class);
        //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ss").withZone(ZoneId.of("UTC"));
        //Instant iTime = Instant.from(df.parse(time_in_text));
        ZonedDateTime zt = ZonedDateTime.ofInstant(time_in_text,ZoneId.of("America/New_York"));
        return zt;
    }
}
