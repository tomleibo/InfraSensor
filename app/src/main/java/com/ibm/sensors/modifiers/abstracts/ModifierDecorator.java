package com.ibm.sensors.modifiers.abstracts;

/**
 * Created by thinkPAD on 10/7/2015.
 */
public class ModifierDecorator<IN,INOUT,OUT,TYPE> implements Modifier<IN,OUT>{

    private Modifier<IN,INOUT> m1;
    private Modifier<INOUT,OUT> m2;

    public ModifierDecorator(Modifier<IN, INOUT> m1, Modifier<INOUT, OUT> m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public OUT modify() {
        INOUT tmp= m1.modify();
        m2.aggregate(tmp);
        OUT out =  m2.modify();
        m2.clear();
        return out;
    }

    @Override
    public void aggregate(IN input) {
        m1.aggregate(input);
    }

    @Override
    public int clear() {
        return m1.clear();
    }
}
