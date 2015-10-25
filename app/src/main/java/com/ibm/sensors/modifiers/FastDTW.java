package com.ibm.sensors.modifiers;

import com.dtw.TimeWarpInfo;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;
import com.timeseries.TimeSeries;
import com.util.DistanceFunction;

/**
 * Created by nexus on 19/10/2015.
 */
public class FastDTW extends AbstractSingleValueModifier<TimeSeries,Double>{

	protected TimeSeries mTemplate;
	protected int mRadius;
	protected Double mResult;
	protected DistanceFunction mDistFunc;

	public FastDTW(TimeSeries template , DistanceFunction distFunctions, int radius){
		this.mRadius=radius;
		this.mTemplate=template;
		this.mDistFunc=distFunctions;
		this.mResult = new Double(Double.MAX_VALUE);
	}

	@Override
	public Double modify() {
		return this.mResult;
	}

	@Override
	public void aggregate(TimeSeries input) {
		TimeWarpInfo info = com.dtw.FastDTW.getWarpInfoBetween(this.mTemplate, input, this.mRadius, this.mDistFunc);
		this.mResult = new Double(info.getDistance());
	}
}
