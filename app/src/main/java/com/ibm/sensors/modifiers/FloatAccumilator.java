package com.ibm.sensors.modifiers;

import com.ibm.sensors.EventWrappers.EventWrapper;

import java.util.ArrayList;

/**
 * Created by nexus on 05/10/2015.
 */
//TODO: fix this to more than 3
public class FloatAccumilator extends AbstractArrayAccumilator<Float>{
	public FloatAccumilator(){
		this.mData=new ArrayList<Float>();
		mData.add(new Float(0));
		mData.add(new Float(0));
		mData.add(new Float(0));
	}

	@Override
	protected Float method(Float oldData, Float newData) {
		Float data= null;
		if (null==oldData) data = newData;
		if (null==newData) data =  oldData;
		if (null!=oldData && null!= newData){
			return  oldData+newData;
		}
		return data;
	}
	@Override
	public void aggregate(EventWrapper<Float[]> input) {
		for (int i=0;i<input.getData().length;i++){
			this.mData.set(i,method(this.mData.get(i),input.getData()[i]));
		}
	}
}
