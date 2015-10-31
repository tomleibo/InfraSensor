package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.BlankEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.Converters.DoubleToTimeSeries;
import com.ibm.sensors.modifiers.Converters.MotionSensorEventWrapperToDoubleArray;
import com.ibm.sensors.modifiers.abstracts.ModifierDecorator;
import com.ibm.sensors.rules.ruleStrategies.EventCountStrategy;
import com.timeseries.TimeSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by nexus on 18/10/2015.
 */
public class RuleTimeSeriesCreator extends Rule{
	private Integer mSensorID;

	public class myDecorator extends ModifierDecorator{
		public myDecorator(DoubleToTimeSeries dts){
			super(new MotionSensorEventWrapperToDoubleArray(),dts);
		}
	}

	public RuleTimeSeriesCreator(Env env, int sensorID, DoubleToTimeSeries converter) {//TODO: change eventfactoryshit to subgroups and add isingroup
		super(env, new EventCountStrategy(
				sensorID,1));
		this.modifiers = new HashMap<>();
		this.mSensorID=new Integer(sensorID);
		this.modifiers.put(this.mSensorID,new myDecorator(converter));
	}

	@Override
	public void dispatch() {
		env.getEventHandler().handleEvent(new BlankEventWrapper<TimeSeries>(System
				.currentTimeMillis(), this, -1, (TimeSeries) this.modifiers.get(this.mSensorID).modify()));
	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return new ArrayList<>(this.mSensorID);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Rules.RuleTimeSeriesCreator;
	}
}
