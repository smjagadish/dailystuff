package org.ServerInf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ServerModel.gen5Radio;
import org.ServerModel.lteRadio;

// use the annotation below to include type info in the serialized object
// type info included as a property with the name 'className' and its value will be the class type of the underlying object
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
// a simpler way of including type info is as captured below. here defaults are applied for the prop name
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
// including the type info as wrapper object instead of simple property
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS , include = JsonTypeInfo.As.WRAPPER_OBJECT)
// including the type info as wrapper array instead of simple property
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS , include =JsonTypeInfo.As.WRAPPER_ARRAY)
// type info included as the custom type name as opposed to class name . it is included as wrapper object
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({@JsonSubTypes.Type(value= gen5Radio.class,name = "gen5"),
@JsonSubTypes.Type(value = lteRadio.class,name="lte")})
public interface Radio {
    default void print()
    {
        // do nothing impl
    }
    static void display()
    {
        // do nothing
    }
    // unless i add @JsonIgnore annotation , a json property called 'aggregator' gets included in the serialized response
    // if i change method name to some arbitrary content w/o prefix of get,is etc. the prop doesnt get included even if no @jsonignore is present
    boolean getAggregator();
    void lock();
    void unlock();
    int getBw();
}
