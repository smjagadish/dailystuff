package org.clientInf;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ClientModel.eastZone;
import org.ClientModel.northZone;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY , property = "zone")
@JsonSubTypes({@JsonSubTypes.Type(name = "east",value = eastZone.class),
@JsonSubTypes.Type(name ="north",value = northZone.class)})
public interface zone {
    boolean expandable();
    void configure();
    int cell_count();
}
