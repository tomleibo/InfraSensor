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
/*
    public static void test(String args[]) {
        AddFloatAccumulator afa = new AddFloatAccumulator();
        Float[] floatArray = {Float.valueOf(1.0f),Float.valueOf(2.0f),Float.valueOf(3.0f)};
        afa.aggregate(Arrays.asList(floatArray));
        afa.list.toString();
        afa.aggregate(Arrays.asList(floatArray));
        afa.list.toString();
    }*/
}
