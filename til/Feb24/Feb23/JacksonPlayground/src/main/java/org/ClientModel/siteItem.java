package org.ClientModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.deserzUtil.siteGeoDeser;

@Data
//@NoArgsConstructor
public class siteItem {
    private int siteID;
    // the annotation below has no effect , so i have commented it
    // when using @jsoncreator , all the annotation looks to be handled at the constructor. so add @jsonDeserialize for the constructor param if you need this
   // @JsonDeserialize(using = siteGeoDeser.class)
    private String siteGeo;
    private int siteBW;
    private siteType.Type type;

    // leveraging json creator with custom property names
   @JsonCreator
    public siteItem(@JsonProperty("sid") int id , @JsonProperty("geo") String geo, @JsonProperty("capacity")int bw, @JsonProperty("type")siteType.Type type)
    {
        this.siteID = id;
        this.siteGeo = geo;
        this.siteBW = bw;
        this.type = type;
    }


}
