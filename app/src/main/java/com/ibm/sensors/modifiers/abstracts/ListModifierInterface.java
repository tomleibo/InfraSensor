package com.ibm.sensors.modifiers.abstracts;

import java.util.List;

public interface ListModifierInterface<IN,OUT> extends Modifier<List<IN>,List<OUT>>{

    @Override
    public List<OUT> modify();

    @Override
    public void aggregate(List<IN> input);

    @Override
    public int clear();
}


