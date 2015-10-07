package com.ibm.sensors.modifiers;

/**
 * Modifier: receives an event and modifies it. Its goal is to normalize data.
 * @param <IN> input type.
 * @param <OUT> output type.
 */
public interface Modifier <IN,OUT> {
    OUT modify ();
    void aggregate(IN input);
    int clear();
}


