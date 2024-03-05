package org.ServerModel;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.ServerInf.Radio;
import org.serzUtil.modelSer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    // pass the zoneddatetime in custom format w/o the tz name
    private ZonedDateTime local_time_with_tz;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    // pass the localdate time in a custom format
    private LocalDateTime local_time;
    @JsonProperty
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    // pass the instant time(utc) stored in a custom format
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SS", timezone = "UTC")
    private Instant time_utc;
    public serverData()
    {
        properties = new HashMap<>();
        this.local_time_with_tz= ZonedDateTime.now();
        this.local_time = LocalDateTime.now();
        this.time_utc = Instant.now();
    }
    public serverData(int val)
    {
        this();
        this.value = val;
        this.radio = new gen5Radio();
    }
    // will serialize the properties hashmap in addition to the class members
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
