package org.clientModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class jacksonClientData {
    private int id;
    private String item;
    private siteData site;
    private siteZone baseData;
    private Instant quote_time;
    private String absolute_time;
}
