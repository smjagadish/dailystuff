package org.deserzUtil;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class abstimeDeser extends StdDeserializer<ZonedDateTime> {
    protected abstimeDeser()
    {
        this(null);
    }
    protected abstimeDeser(Class<?> vc) {
        super(vc);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String time_in_text = jsonParser.getValueAsString();
        LocalDateTime lt = LocalDateTime.parse(time_in_text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime zt = ZonedDateTime.of(lt, ZoneId.of("America/New_York"));
        System.out.println(zt.toString());
        return zt;
    }
}
