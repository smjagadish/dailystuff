package org.ServerModel;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
// uncomment below to have custom serialization for the fwItem POJO
// all of what is mentioned below in terms of properties, getter(json) etc can be side-stepped in entirety
//@JsonSerialize(using = fwItemSer.class)
public class fwItem {

    @Setter @Getter
    private int itemID;
    @Setter @Getter
    private String model;
    //use property name max_bw
    @JsonProperty("max_bw") @Getter @Setter
    private float max_thput;
    @Setter @Getter
    private float min_thput;
    // use a property name min_bw
    // will override the lombok generated getter
    @JsonGetter("min_bw")
    public float getMin_thput()
    {
        return min_thput;
    }


}
