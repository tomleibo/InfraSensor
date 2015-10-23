package com.ibm.sensors.modifiers.Converters;

import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;
import com.ibm.sensors.utils.TimeSeriesWithJSON;
import com.timeseries.TimeSeries;
import com.timeseries.TimeSeriesPoint;

/**
 * Created by nexus on 19/10/2015.
 */
public class DoubleToTimeSeries extends AbstractSingleValueModifier<Double[],TimeSeriesWithJSON>{
	private int mLimit;
	private boolean mCircular;
	private TimeSeriesWithJSON mTimeSeries;
	private double time;
	public DoubleToTimeSeries(int limit, boolean circular, int numOfDims){
		this.mLimit=limit;
		this.mCircular=circular;
		this.mTimeSeries=new TimeSeriesWithJSON(numOfDims);
		this.time=0;
	}
	@Override
	public TimeSeriesWithJSON modify() {
		return this.mTimeSeries;
	}

	@Override
	public void aggregate(Double[] input) {
		double[] tmp = new double[input.length];
		for (int i=0;i<input.length;i++){
			tmp[i]=input[i].doubleValue();
		}
		TimeSeriesPoint a = new TimeSeriesPoint(tmp);
		time++;
		if (this.mLimit==0){
			this.mTimeSeries.addLast(time,a);
			return;
		}
		if (this.mLimit>0){
			if (this.mLimit > this.mTimeSeries.size()){
				this.mTimeSeries.addLast(time,a);
				return;
			}
			if (this.mCircular){
				this.mTimeSeries.removeFirst();
				this.mTimeSeries.addLast(time,a);
				return;
			}
		}
	}
}
