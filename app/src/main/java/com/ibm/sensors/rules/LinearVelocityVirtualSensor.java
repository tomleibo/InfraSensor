package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.AddFloatAccumulator;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by nexus on 05/10/2015.
 */
public class LinearVelocityVirtualSensor extends Rule
{

	public LinearVelocityVirtualSensor(Env env, RuleStrategy strategy) {
		super(env, strategy);
		modifiers = new TreeMap<>();
		modifiers.put(EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION, new AddFloatAccumulator());
	}


	public LinearVelocityVirtualSensor(Env env) {
		super(env, new ImmidiateStrategy());
		modifiers = new TreeMap<>();
		modifiers.put(EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION, new AddFloatAccumulator());
	}

	@Override
	public void dispatch() {
		List<Float> values=new ArrayList();
		for (Map.Entry<Integer, ? extends Modifier> p : modifiers.entrySet()) {
			ArrayList<Object> list = (ArrayList)(p.getValue().modify());
			values = new ArrayList<Float>();
			for (int i=0;i<list.size();i++){
				values.add((Float) list.get(i));
			}
		}
		env.getEventHandler().handleEvent(new MotionSensorEventWrapper(this, EventCreatorFactory.Events.TYPE_EVENT_LINEAR_VELOCITY_CHANGE, new Float[]{values.get(0),values.get(1),values.get(2)}, System.currentTimeMillis(), 0));
	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return Arrays.asList(EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Events.TYPE_EVENT_LINEAR_VELOCITY_CHANGE;
	}
}
