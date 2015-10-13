package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractListAccumulator;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;

import java.util.List;

/**
 * Created by nexus on 13/10/2015.
 */
public class InRadious extends AbstractSingleChangingValueModifier<Float[],Boolean> {
	private Double mRadious;
	private List<Float> mInitPoint;
	private Float[] mNewPoint;
	public InRadious(List<Float> initPoint, Double R){
		super();
		this.mRadious=Math.pow(R,2);
		this.mInitPoint=initPoint;
		this.mNewPoint=new Float[0];
	}


	@Override
	public Boolean modify() {
		double mySize= 0;
		if (getValue() == null || this.mInitPoint.size()!= getValue().length || this.mInitPoint.size()==0) {
			return false;
		}
		for (int i=0; i<mInitPoint.size();i++){
			mySize += Math.pow((double ) (mInitPoint.get(i).floatValue()-(getValue()[i])),2);
		}
		if (mySize <= this.mRadious.doubleValue()){
			return true;
		}
		return false;
	}
}
