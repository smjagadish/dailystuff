package org.ServerModel;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.ServerInf.Radio;
import org.serzUtil.modelSer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// uncomment the below to have a custom serialization for the serverData POJO
// if using the custom serialization , the prop name and value can be overridden . infact properties cam be added , removed and what not
//@JsonSerialize(using = serverDataSer.class)
@JsonPropertyOrder({"sZone","sID"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class serverData {
    @Getter @Setter

    private int sID;
    @Getter @Setter
    @JsonSerialize(using = modelSer.class)
    private String sZone;
    @Getter @Setter
    @JsonProperty("set_item")
    // including th @jsonunwrapped annotation will 'flatten' the fwItem object contents during the serialization
    // @JsonUnwrapped
    private fwItem item;
    private Map<String,String> properties ;

    @Getter
    @JsonIgnore
    private int value;
    @Getter @Setter
    private Radio radio;
    @JsonManagedReference
    @Getter @Setter
    private serverManager manager;
    public serverData()
    {
        properties = new HashMap<>();
    }
    public serverData(int val)
    {
        this();
        this.value = val;
        this.radio = new gen5Radio();
    }
    // will de-serialize the properties hashmap in addition to the class members
    @JsonAnyGetter
    public Map<String,String> getProperties()
    {
        return this.properties;
    }
    public void addProperties(String key, String value)
    {
        this.properties.put(key, value);
    }



    public void setMyZone(String s)
    {

        this.sZone = s + "hello";
    }

}
