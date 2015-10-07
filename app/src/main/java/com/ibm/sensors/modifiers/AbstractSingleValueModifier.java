package com.ibm.sensors.modifiers;

/**
 * Created by thinkPAD on 10/7/2015.
 */
public abstract class AbstractSingleValueModifier<IN,OUT> implements Modifier<IN,OUT> {
    private IN singleValue=null;

    public IN getValue() {
        return singleValue;
    }

    public void setValue(IN in) {
        singleValue = in;
    }

    /**
     * Clear makes the aggregated value null.
     * @return singleValue==null?0:1
     */
    @Override
    public int clear() {
        int res = (singleValue==null)?0:1;
        singleValue=null;
        return res;
    }
}
