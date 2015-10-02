package com.ibm.sensors.rules.ruleStrategies;

/**
 * Created by thinkPAD on 9/12/2015.
 */
public interface RuleStrategy {
    boolean shouldDispatchOnEventArrival(int type);
    void reset();
}
