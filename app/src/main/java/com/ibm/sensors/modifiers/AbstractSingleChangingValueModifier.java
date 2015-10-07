package com.ibm.sensors.modifiers;

/**
 Created By Tom.
 Used to save a single value which is overridden on the next aggregate.
 Get the aggregated value by calling getValue and modify it.
 Clear makes the aggregated value null.
 */
public abstract class AbstractSingleChangingValueModifier<IN,OUT>
        extends AbstractSingleValueModifier<IN,OUT> {

    @Override
    public void aggregate(IN input) {
        setValue(input);
    }

}
