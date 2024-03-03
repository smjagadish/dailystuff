package org.ServerModel;

import lombok.Data;
import org.ServerInf.Radio;

@Data
public class lteRadio implements Radio {
    private boolean service_state;
    private int bw;
    public lteRadio()
    {
        this.service_state = true;
        this.bw = 10;
    }
    @Override
    public boolean getAggregator() {
        return false;
    }

    @Override
    public void lock() {
        this.service_state = false;

    }

    @Override
    public void unlock() {
        this.service_state = true;

    }

    @Override
    public int getBw() {
        return this.bw;
    }
}
