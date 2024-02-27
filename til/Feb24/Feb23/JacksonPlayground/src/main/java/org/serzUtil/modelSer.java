package org.serzUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class modelSer extends StdSerializer<String> {
    public modelSer()
    {
        this(null);
    }

    protected modelSer(Class<String> s)
    {
        super(s);
    }
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
             jsonGenerator.writeString(s+"i have added additional content from a custom serializer");
    }
}
