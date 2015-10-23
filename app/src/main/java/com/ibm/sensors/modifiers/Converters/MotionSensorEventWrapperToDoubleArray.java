package com.ibm.sensors.modifiers.Converters;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;

/**
 * Created by nexus on 20/10/2015.
 */
public class MotionSensorEventWrapperToDoubleArray extends AbstractSingleValueModifier<MotionSensorEventWrapper,Double[]>{
	private Double[] mData;
	@Override
	public Double[] modify() {
		return this.mData;
	}

	@Override
	public void aggregate(MotionSensorEventWrapper input) {
		Float[] sData = input.getData();
		this.mData= new Double[sData.length];
		for (int i=0;i<sData.length;i++) {
			this.mData[i]=new Double(sData[i].doubleValue());
		}
	}
}
