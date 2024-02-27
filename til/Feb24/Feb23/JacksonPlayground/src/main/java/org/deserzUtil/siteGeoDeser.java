package org.deserzUtil;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class siteGeoDeser extends StdDeserializer<String> {

    public siteGeoDeser()
    {
        this(null);
    }
    protected siteGeoDeser(Class<String> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String out = jsonParser.getText();
        System.out.println(out);
        return out+" custom string deser added data is injected";
    }
}
