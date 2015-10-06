package com.ibm.sensors.rules;

import com.google.gson.Gson;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.FloatAccumilator;
import com.ibm.sensors.modifiers.MaxAccelerometerSpeed;
import com.ibm.sensors.modifiers.Modifier;
import com.ibm.sensors.rules.Rule;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.sensorWrappers.EventCreator;
import com.ibm.sensors.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by nexus on 05/10/2015.
 */
public class LinearVelocityVirtualSensor extends Rule
{
	private MotionSensorEventWrapper mMotionSensorEventWrapper;
	public LinearVelocityVirtualSensor(Env env, RuleStrategy strategy) {
		super(env, strategy);
		modifiers = new ArrayList<>();
		modifiers.add(new Pair<Integer, Modifier>(EventCreatorFactory.LINEAR_ACCELERATION, new FloatAccumilator()));
		mMotionSensorEventWrapper = null;
	}


	public LinearVelocityVirtualSensor(Env env) {
		super(env, new ImmidiateStrategy());
		modifiers = new ArrayList<>();
		modifiers.add(new Pair<Integer, Modifier>(EventCreatorFactory.LINEAR_ACCELERATION, new FloatAccumilator()));
		mMotionSensorEventWrapper = null;
	}

	@Override
	public void dispatch() {
		Float[] values=new Float[3];
		for (Pair<Integer, Modifier> p : modifiers) {
			values=(Float[])p.value.modify();
		}
		env.getEventHandler().handleEvent(new MotionSensorEventWrapper(this,EventCreatorFactory.TYPE_LINEAR_VELOCITY_CHANGE_EVENT,values,System.currentTimeMillis(),0));
	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return Arrays.asList(EventCreatorFactory.LINEAR_ACCELERATION);
	}

	@Override
	public int getType() {
		return 0;
	}
}
