package com.ibm.sensors.rules.ruleStrategies;

/**
 * Created by nexus on 06/10/2015.
 */
public class ImmidiateStrategy implements RuleStrategy{
	@Override
	public boolean shouldDispatchOnEventArrival(int EventType) {
		return true;
	}

	@Override
	public void reset() {

	}
}
