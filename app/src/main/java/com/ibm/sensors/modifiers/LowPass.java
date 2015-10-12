package com.ibm.sensors.modifiers;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.modifiers.abstracts.Modifier;

/**
 * Created by nexus on 07/10/2015.
 */
public class LowPass implements Modifier<EventWrapper<Float[]>,Float[]> {
	private Float mThreshold;
	private Float[] mResult;
	public LowPass(Float threshold){
		this.mThreshold=threshold;
	}

	@Override
	public Float[] modify() {
		for (int i=0;i<mResult.length;i++){
			if (mResult[i]<mThreshold){
				mResult[i]=Float.valueOf(0);
			}
		}
		return mResult;
	}

	@Override
	public void aggregate(EventWrapper<Float[]> input) {
		this.mResult=input.getData();
	}

	@Override
	public int clear() {
		return 0;
	}
}
