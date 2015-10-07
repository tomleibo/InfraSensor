package com.ibm.sensors.modifiers;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tom on 10/7/2015.
 * IN - type to aggregate/modify. OUT - output type of a single modify on IN.
 * modify will return Collection modify.
 * modify
 * Used to aggregate [size] parametres. Old values are overridden by new ones in a fifo way.
 * Use getValues to get the aggregated collection.
 */
public abstract class AbstractCircularArrayModifier<IN,OUT> implements Modifier<IN,Collection<OUT>>{
    Collection<IN> buffer;
    abstract OUT modifySingleValue(IN in);
    public AbstractCircularArrayModifier(int size) {
        this.buffer = new CircularFifoBuffer(size);
    }

    public Collection<IN> getValues() {
        return buffer;
    }

    @Override
    public Collection<OUT> modify() {
        List<OUT> result = new ArrayList<>();
        for (IN in : buffer){
            result.add(modifySingleValue(in));
        }
        return result;
    }

    @Override
    public void aggregate(IN input) {
        buffer.add(input);
    }

    @Override
    public int clear() {
        int res = buffer.size();
        buffer.clear();
        return res;
    }
}
