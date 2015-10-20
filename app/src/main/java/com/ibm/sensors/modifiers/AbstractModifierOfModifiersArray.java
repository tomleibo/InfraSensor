package com.ibm.sensors.modifiers;

import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;
import com.ibm.sensors.modifiers.abstracts.Modifier;

/**
 * Created by nexus on 19/10/2015.
 */
public abstract class AbstractModifierOfModifiersArray<IN,OUT> extends AbstractSingleValueModifier<IN[],OUT[]>{
	private Modifier[] mModifiers;
	public AbstractModifierOfModifiersArray(Modifier[] modifiers){
		this.mModifiers=modifiers;
	}

	@Override
	public OUT[] modify() {
		Object[] data= new Object[this.mModifiers.length];
		for (int i=0;i<this.mModifiers.length;i++){
			data[i]=this.mModifiers[i].modify();
		}
		return (OUT[]) data;
	}

	@Override
	public void aggregate(IN[] input) {
		for (int i=0; i<Math.min(this.mModifiers.length,input.length);i++){
			mModifiers[i].aggregate(input[i]);
		}
	}
}
