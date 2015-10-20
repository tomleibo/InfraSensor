package com.ibm.sensors.modifiers.Converters;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;

/**
 * Created by nexus on 19/10/2015.
 */
public class ThreeDimSensorToDoubleArr extends AbstractSingleValueModifier<MotionSensorEventWrapper,Double[]>{
	private Double[] mData;
	public ThreeDimSensorToDoubleArr(){
		this.mData=new Double[3];
	}
	@Override
	public Double[] modify() {
		return this.mData;
	}

	@Override
	public void aggregate(MotionSensorEventWrapper input) {
		for (int i=0;i<3;i++){
			this.mData[i]=new Double(input.getData()[i]);
		}
	}
}
