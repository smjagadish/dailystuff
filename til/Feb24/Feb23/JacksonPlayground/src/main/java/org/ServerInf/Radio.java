package org.ServerInf;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Radio {
    default void print()
    {
        // do nothing impl
    }
    static void display()
    {
        // do nothing
    }
    // unless i add @JsonIgnore annotation , a json property called 'aggregator' gets included in the serialized response
    // if i change method name to some arbitrary content w/o prefix of get,is etc. the prop doesnt get included even if no @jsonignore is present
    boolean getAggregator();
    void lock();
    void unlock();
    int getBw();
}
