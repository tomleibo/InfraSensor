package com.ibm.sensors.rules;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by nexus on 13/10/2015.
 */
public class getLastGoodGPSPoint extends Rule{
	public getLastGoodGPSPoint(Env env, RuleStrategy strategy) {
		super(env, strategy);
	}

	public getLastGoodGPSPoint(Env env){
		this(env,new ImmidiateStrategy());
	}

	@Override
	public void dispatch() {

	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return Arrays.asList(EventCreatorFactory.TYPE_EVENT_GPS_LOCATION,EventCreatorFactory.TYPE_EVENT_GPS_ACCURACY_CHANGED);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.TYPE_RULE_LAST_GOOD_GPS_POINT;
	}
}
