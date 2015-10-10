package com.ibm.sensors.modifiers.abstracts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nexus on 05/10/2015.
 */
public abstract class AbstractListAccumulator<T> implements ListModifierInterface<T,T> {

	protected List<T> list;

	public AbstractListAccumulator() {
		this.list = new ArrayList<>();
	}

	public abstract T operator(T t1,T t2);

	@Override
	public List<T> modify() {
		return list;
	}

	@Override
	public void aggregate(List<T> input) {
		if (list.size()==0) {
			list=input;
		}
		else {
			for (int i=0;i<Math.min(list.size(),input.size());i++) {
				list.set(i,operator(list.get(i),input.get(i)));
			}
		}
	}

	@Override
	public int clear() {
		int ans = list.size();
		list.clear();
		return ans;
	}
}
