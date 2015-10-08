
package com.ibm.sensors.modifiers.abstracts;

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
public abstract class AbstractCircularListModifier<IN,OUT> implements ListModifierInterface<IN,OUT> {
    Collection<IN> collection;
    public abstract OUT modifySingleValue(IN in);
    public AbstractCircularListModifier(int size) {
        this.collection = new CircularFifoBuffer(size);
    }

    public Collection<IN> getValues() {
        return collection;
    }

    @Override
    public List<OUT> modify() {
        List<OUT> result = new ArrayList<>();
        for (IN in : collection){
            result.add(modifySingleValue(in));
        }
        return result;
    }

    public void aggregate(Collection<IN> input) {
        collection.addAll(input);
    }

    @Override
    public int clear() {
        int res = collection.size();
        collection.clear();
        return res;
    }
}


