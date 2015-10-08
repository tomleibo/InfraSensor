package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.AddFloatAccumulator;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
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
		modifiers.add(new Pair<Integer, Modifier>(EventCreatorFactory.LINEAR_ACCELERATION, new AddFloatAccumulator()));
		mMotionSensorEventWrapper = null;
	}


	public LinearVelocityVirtualSensor(Env env) {
		super(env, new ImmidiateStrategy());
		modifiers = new ArrayList<>();
		modifiers.add(new Pair<Integer, Modifier>(EventCreatorFactory.LINEAR_ACCELERATION, new AddFloatAccumulator()));
		mMotionSensorEventWrapper = null;
	}

	@Override
	public void dispatch() {
		ArrayList<Float> values=new ArrayList();
		for (Pair<Integer, Modifier> p : modifiers) {
			values = (ArrayList) p.value.modify();

		}
		env.getEventHandler().handleEvent(new MotionSensorEventWrapper(this, EventCreatorFactory.TYPE_LINEAR_VELOCITY_CHANGE_EVENT, new Float[]{values.get(0),values.get(1),values.get(2)}, System.currentTimeMillis(), 0));
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
