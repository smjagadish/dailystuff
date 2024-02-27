package org.ServerModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ServerInf.Radio;


public class gen5Radio implements Radio {
    @Getter @Setter
    private boolean service_state;
    private int bw;
    public gen5Radio()
    {
        this.service_state = true;
        this.bw = 20;
    }
    @Override
    public boolean getAggregator() {
        return true;
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
