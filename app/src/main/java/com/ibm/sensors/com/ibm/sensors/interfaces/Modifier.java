package com.ibm.sensors.com.ibm.sensors.interfaces;

/**
 * Modifier – receives an event and modifies it. Its goal is to normalize data.
 * @param <IN> input type.
 * @param <OUT> output type.
 */
public interface Modifier <IN,OUT> {
    public OUT modify (IN input);
    public Modifier build(CollectionStrategy strategy);

    /**
     Rule can collect events before modifying data. This collection can be implemented in various strategies. For instance:
     1.	No wait – modify and decide on each new event.
     2.	Cyclic barrier – wait for an event of each type.
     3.	Custom.

     */
    public enum CollectionStrategy{
        NO_WAIT,CYCLIC,CUSTOM;
    }
}


