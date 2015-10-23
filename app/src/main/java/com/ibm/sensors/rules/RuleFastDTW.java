package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.BlankEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.FastDTW;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.utils.TimeSeriesWithJSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by nexus on 22/10/2015.
 */
public class RuleFastDTW extends Rule{;

	public RuleFastDTW(Env env, RuleStrategy strategy, FastDTW modifier) {
		super(env, strategy);

		this.modifiers = new HashMap<>();
		this.modifiers.put(EventCreatorFactory.Rules.RuleTimeSeriesCreator,modifier);
	}

	@Override
	public void dispatch() {
		env.getEventHandler().handleEvent(new BlankEventWrapper<TimeSeriesWithJSON>(System
				.currentTimeMillis(), this, -2, (TimeSeriesWithJSON) this.modifiers.get(EventCreatorFactory.Rules.RuleTimeSeriesCreator)
				.modify()));
	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return new ArrayList<>(EventCreatorFactory.Rules.RuleTimeSeriesCreator);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Rules.RuleFastDTW;
	}
}
