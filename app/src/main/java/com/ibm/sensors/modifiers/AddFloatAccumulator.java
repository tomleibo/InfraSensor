package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractListAccumulator;

public class AddFloatAccumulator extends AbstractListAccumulator<Float> {

    public AddFloatAccumulator() {
        super();
    }

    @Override
    public java.lang.Float operator(java.lang.Float t1, java.lang.Float t2) {
        return t1+t2;
    }
}
