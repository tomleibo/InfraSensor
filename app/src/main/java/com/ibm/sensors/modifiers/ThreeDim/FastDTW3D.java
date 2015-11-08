package com.ibm.sensors.modifiers.ThreeDim;


import DTW.dtw.TimeWarpInfo;
import com.ibm.sensors.modifiers.AbstractModifierOfModifiersArray;
import com.ibm.sensors.modifiers.FastDTW;

import DTW.timeseries.TimeSeries;
import DTW.util.DistanceFunction;

/**
 * Created by nexus on 18/10/2015.
 */
public class FastDTW3D extends AbstractModifierOfModifiersArray<TimeSeries,Double> {
	private int[] mRadiuses;
	private DistanceFunction[] mDistFunctions;
	private TimeSeries[] mTemplateSeries;
	private Double[] mResults;

	public FastDTW3D(FastDTW[] fastDTWs,DistanceFunction[] distFunctions, int[] radiuses){
		super(fastDTWs);
		this.mDistFunctions=distFunctions;
		this.mRadiuses=radiuses;
		this.mResults=new Double[3];

	}


	@Override
	public Double[] modify() {
		return this.mResults;
	}

	@Override
	public void aggregate(TimeSeries[] input) {
		for (int i=0;i<3;i++){
			TimeWarpInfo info = DTW.dtw.FastDTW.getWarpInfoBetween(this.mTemplateSeries[i], input[i], this.mRadiuses[i], this.mDistFunctions[i]);
			this.mResults[i] = new Double(info.getDistance());
		}
	}
}
