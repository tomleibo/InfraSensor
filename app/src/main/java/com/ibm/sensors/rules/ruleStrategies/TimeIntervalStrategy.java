package com.ibm.sensors.rules.ruleStrategies;

/**
 * Created by thinkPAD on 9/12/2015.
 */
public class TimeIntervalStrategy implements RuleStrategy {
    long startTime;
    final long freq;

    public TimeIntervalStrategy(long frequencyInMilliSeconds) {
        reset();
        freq = frequencyInMilliSeconds;
    }

    @Override
    public boolean shouldDispatchOnEventArrival(int type) {
        return (System.currentTimeMillis()-startTime > freq);
    }

    @Override
    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
