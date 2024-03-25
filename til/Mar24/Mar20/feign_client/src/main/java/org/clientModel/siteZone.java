package org.clientModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class siteZone {
    private String zone;
    private boolean size_exp_allowed;
    private String license_key;
    private int max_cells;
    private String epc_zone;


}
