package org.claimModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class claim {
    private String sub;
    private localClaimBuilder claim_info;
}
