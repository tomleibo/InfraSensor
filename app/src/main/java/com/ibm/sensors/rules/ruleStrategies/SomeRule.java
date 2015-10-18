package com.ibm.sensors.rules.ruleStrategies;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.Rule;
import com.ibm.sensors.utils.MultiGenericObservable;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by nexus on 03/10/2015.
 */
public class SomeRule extends Rule{
	EventWrapper data;

	public SomeRule(Env env, RuleStrategy strategy) {
		super(env, strategy);
	}

	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		this.data=data;
		dispatch();
	}

	@Override
	public void dispatch() {
		env.getEventHandler().handleEvent(data);
	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return Arrays.asList(EventCreatorFactory.Rules.TYPE_RULE_EXTREME_MOVE);
	}

	@Override
	public int getType() {
		return 0;
	}
}
