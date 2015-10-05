package com.ibm.sensors.modifiers;

/**
 * Created by nexus on 05/10/2015.
 */
public class FloatAccumilator extends ArrayAccumilator<Float>{

	@Override
	protected Float method(Float oldDataOrNULL, Float newData) {
		if (null==oldDataOrNULL) return newData;
		else  return  oldDataOrNULL+newData;
	}
}
