package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;

/**
 * Created by nexus on 18/10/2015.
 */
public class WeightSigma extends AbstractSingleValueModifier<float[],Double>{
	private Double mResult;
	private Double[] mWeights;

	public WeightSigma(Double[] weights){
		this.mWeights=weights;
		this.mResult=new Double(0);
	}

	@Override
	public Double modify() {
		return mResult;
	}

	@Override
	public void aggregate(float[] input) {
		Double tmp = new Double(0);
		for (int i=0;i<Math.min(input.length,this.mWeights.length);i++){
			tmp+=(input[i]*this.mWeights[i]);
		}
		this.mResult=tmp;
	}
}
