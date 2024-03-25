package org.clientModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class siteData {

    private int sid;
    // the annotation below has no effect , so i have commented it
    // when using @jsoncreator , all the annotation looks to be handled at the constructor. so add @jsonDeserialize for the constructor param if you need this
    // @JsonDeserialize(using = siteGeoDeser.class)
    private String geo;
    private int capacity;
    private siteType.Type type;
}
