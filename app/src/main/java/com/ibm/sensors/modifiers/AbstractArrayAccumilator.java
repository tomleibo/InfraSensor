package com.ibm.sensors.modifiers;

import com.ibm.sensors.EventWrappers.EventWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nexus on 05/10/2015.
 */
public abstract class AbstractArrayAccumilator<T> implements Modifier<EventWrapper<T[]>,List<T>>{

	protected List<T> mData=null;

	@Override
	public List<T> modify() {
		return mData;
	}

	protected abstract T method(T oldDataOrNULL, T newData);





}
