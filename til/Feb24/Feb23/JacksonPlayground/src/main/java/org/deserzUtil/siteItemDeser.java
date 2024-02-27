package org.deserzUtil;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.ClientModel.siteItem;

import java.io.IOException;

public class siteItemDeser extends StdDeserializer<siteItem> {
    public siteItemDeser()
    {
        this(null);
    }
    protected siteItemDeser(Class<siteItem> vc) {
        super(vc);
    }

    @Override
    public siteItem deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = new ObjectMapper();
        siteItem item = mapper.readValue(jsonParser, siteItem.class);
        // overriding in the custom deser
        item.setSiteGeo("east");
        return item;
    }
}
