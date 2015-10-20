package com.ibm.sensors.utils;

/**
 * Created by nexus on 19/10/2015.
 */
public class Converters {
	public static Double[] D2D(double[] data){
		Double[] res = new Double[data.length];
		for (int i=0;i<data.length;i++){
			res[i]=new Double(data[i]);
		}
		return res;
	}

	public static double[] D2D(Double[] data){
		double[] res = new double[data.length];
		for (int i=0;i<data.length;i++){
			res[i]=data[i].doubleValue();
		}
		return res;
	}
}
