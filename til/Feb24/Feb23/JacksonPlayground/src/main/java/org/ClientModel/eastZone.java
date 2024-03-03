package org.ClientModel;

import lombok.Data;
import org.clientInf.zone;
@Data
public class eastZone implements zone {
    private boolean site_exp_allowed;
    private String license_key;
    private int max_cells;
    private String core_zone;
    public eastZone()
    {
        this.site_exp_allowed = false;
        this.license_key = null;
        this.max_cells = 0;
        this.core_zone = null;
    }
    public eastZone(boolean exp, String key , int cells , String core_zone)
    {
        this.site_exp_allowed = exp;
        this.license_key = key;
        this.max_cells = cells;
        this.core_zone = core_zone;
    }
    @Override
    public boolean expandable() {
        return false;
    }

    @Override
    public void configure() {

    }

    @Override
    public int cell_count() {
        return 0;
    }
}
