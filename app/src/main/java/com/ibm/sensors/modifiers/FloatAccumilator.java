package com.ibm.sensors.modifiers;

/**
 * Created by nexus on 05/10/2015.
 */
public class FloatAccumilator extends AbstractArrayAccumilator<Float>{

	@Override
	protected Float method(Float oldData, Float newData) {
		if (null==oldData) return newData;
		if (null==newData) return oldData;
		return  oldData+newData;
	}
}
