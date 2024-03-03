package org.ClientModel;

import lombok.Data;
import org.clientInf.zone;

@Data
public class northZone implements zone {
    private boolean site_exp_allowed;
    private String license_key;
    private int max_cells;
    private String epc_zone;
    public northZone()
    {
        this.site_exp_allowed = false;
        this.license_key = null;
        this.max_cells = 0;
        this.epc_zone = null;
    }
    public northZone(boolean exp, String key , int cells , String epc_zone)
    {
        this.site_exp_allowed = exp;
        this.license_key = key;
        this.max_cells = cells;
        this.epc_zone = epc_zone;
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
