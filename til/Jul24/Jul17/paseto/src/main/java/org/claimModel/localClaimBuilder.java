package org.claimModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class localClaimBuilder {
    private int app_version;
    private String app_Name;
    private String app_Zone;

}
