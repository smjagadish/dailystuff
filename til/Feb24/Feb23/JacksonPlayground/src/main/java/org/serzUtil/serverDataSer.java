package org.serzUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ServerModel.serverData;

import java.io.IOException;

public class serverDataSer extends StdSerializer<serverData> {
    public serverDataSer()
    {
        this(null);
    }
    protected serverDataSer(Class<serverData> s)
    {
        super(s);
    }
    @Override
    public void serialize(serverData serverData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("test","pass");
        jsonGenerator.writeFieldName("fwItem");
        jsonGenerator.writePOJO(serverData.getItem());
        jsonGenerator.writeEndObject();

    }
}
