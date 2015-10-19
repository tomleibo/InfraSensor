package com.ibm.sensors.rules;

import android.location.LocationProvider;

import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSLocationChangedEventWrapper;
import com.ibm.sensors.EventWrappers.Simple.EventDouble;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.FastDTW;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.EventCountStrategy;
import com.ibm.sensors.utils.Pair;
import com.util.DistanceFunctionFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nexus on 18/10/2015.
 */
public class RuleCheck3dDTWProximity extends Rule{
	private Integer mSensorID;

	public RuleCheck3dDTWProximity(Env env, int sensorID,int eventID, String[] templateFileName,int[] r) {//TODO: change eventfactoryshit to subgroups and add isingroup
		super(env, new EventCountStrategy(
				sensorID,1));
		modifiers=new ArrayList<>();
		this.mSensorID=new Integer(sensorID);
		//for ()
		//final boolean euclideanDistance = modifiers.add(new Pair<>(eventID,
	//			new FastDTW(templateFileName,false,false,false,DistanceFunctionFactory.getDistFnByName("EuclideanDistance"),',','\n',r)));
	}

	@Override
	public void dispatch() {
	

	}

	@Override
	public Collection<Integer> getSensorTypes() {
		return new ArrayList<>(this.mSensorID);
	}

	@Override
	public int getType() {
		return 0;
	}
}
