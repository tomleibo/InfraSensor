package com.ibm.sensors.modifiers.Converters;

import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;
import com.ibm.sensors.modifiers.abstracts.Modifier;

/**
 * Created by nexus on 18/10/2015.
 */
public class ArraySelectValue extends AbstractSingleChangingValueModifier<Object[],Object>{
	private int mIndex;
	public ArraySelectValue(int index){
		this.mIndex=index;
	}
	@Override
	public Object modify() {
		if (this.getValue()==null || this.getValue().length<=mIndex){return null;}
		return this.getValue()[this.mIndex];
	}
}
