package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.BlankEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.FastDTW;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.utils.TimeSeriesWithJSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import DTW.timeseries.TimeSeries;
import DTW.util.DistanceFunction;

/**
 * Created by nexus on 22/10/2015.
 */
public class RuleFastDTW extends Rule{;

	public static final String PARAM_TIME_SERIES_TEMPLATE = "TIME_SERIRES_TEMPLATE";
	public static final String PARAM_RADIUS = "RADIUS";
	public static final String PARAM_DISTANCE_FUNC = "DIS_FUNC";

	public RuleFastDTW(Env env) {
		super(env, new ImmidiateStrategy());
	}

	@Override
	public boolean register(SensorConfiguration configuration) {
		Integer radius = (Integer)configuration.getObject(PARAM_RADIUS);
		DistanceFunction df = (DistanceFunction) configuration.getObject(PARAM_DISTANCE_FUNC);
		TimeSeries ts = (TimeSeries) configuration.getObject(PARAM_TIME_SERIES_TEMPLATE);

		FastDTW fastDTW = new FastDTW(ts, df, radius);
		modifiers.put(EventCreatorFactory.Rules.RuleTimeSeriesCreator, fastDTW);
		return true;

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
