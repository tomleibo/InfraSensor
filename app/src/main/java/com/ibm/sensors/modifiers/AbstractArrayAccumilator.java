package com.ibm.sensors.modifiers;

/**
 * Created by nexus on 05/10/2015.
 */
public abstract class AbstractArrayAccumilator<T> implements Modifier<T[],T[]>{
	protected T[] mData;

	@Override
	public T[] modify() {
		return mData;
	}

	protected abstract T method(T oldDataOrNULL, T newData);

	@Override
	public void aggregate(T[] input) {
		if (input.length>mData.length){
			Object[] tmp = new Object[input.length];
			for (int i=0;i<input.length;i++){
				if (i<mData.length){
					mData[i] = method(this.mData[i],input[i]);
				}
				else{
					mData[i] = method(null,input[i]);
				}
			}
		}
	}
}
