package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;

/**
 * Created by nexus on 13/10/2015.
 */
public class inRange extends AbstractSingleChangingValueModifier<Comparable,Boolean>{
	private Comparable mLow,mHigh;
	public inRange(Comparable low, Comparable high){
		super();
		this.mLow=low;
		this.mHigh=high;
	}

	@Override
	public Boolean modify() {
		if (this.getValue().compareTo(this.mHigh) >= -1 && this.getValue().compareTo(this.mLow) <=1){
			return true;
		}
		return false;
	}
}
