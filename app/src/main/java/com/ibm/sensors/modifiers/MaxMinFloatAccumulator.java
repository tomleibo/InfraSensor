package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractListAccumulator;

/**
 * Created by thinkPAD on 10/9/2015.
 */
public class MaxMinFloatAccumulator extends AbstractListAccumulator<Float> {
    public enum Type {
        MAX,MIN
    }
    Type type;

    public MaxMinFloatAccumulator(Type type) {
        this.type=type;
    }

    @Override
    public Float operator(Float t1, Float t2) {
        switch(this.type) {
            case MAX:
                return Math.max(t1,t2);
            case MIN:
                return Math.min(t1,t2);
            //this never happens.
            default:
                return 0f;
        }
    }
}
