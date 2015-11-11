package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.content.Context;

import com.ibm.sensors.env.Env;


/**
 * Created by nexus on 25/10/2015.
 */
public abstract class AbstractMainActivityConf implements MainConfInterface{
	protected Env mEnv;
	protected Activity mAc;
	protected abstract void _Main(Env env, Activity ac);
	public void Main(Env env , Activity ac){
		this.mEnv=env;
		this.mAc=ac;
		_Main(env,ac);
	}


}
