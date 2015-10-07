package com.ibm.sensors.modifiers;

/**
 Created By Tom.
 Used to save a single value which doesn't change unless clear is called.
 Get the aggregated value by calling getValue and modify it.
 */
public abstract class AbstractFirstValueModifier<IN,OUT> extends AbstractSingleValueModifier<IN,OUT> {

    @Override
    public void aggregate(IN input) {
        if (getValue()!=null) {
            setValue(input);
        }
    }
}
