package org.serzUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ServerModel.fwItem;

import java.io.IOException;

public class fwItemSer extends StdSerializer<fwItem> {
    public fwItemSer()
    {
        this(null);
    }
    protected fwItemSer(Class<fwItem> item)
    {
        super(item);
    }

    @Override
    public void serialize(fwItem fwItem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("dummy", fwItem.getModel());
        jsonGenerator.writeEndObject();
    }




}
