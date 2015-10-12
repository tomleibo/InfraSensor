package com.ibm.sensors.modifiers;


import com.dtw.TimeWarpInfo;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.timeseries.TimeSeries;
import com.util.DistanceFunction;

/**
 * Created by nexus on 02/10/2015.
 */
public class FastDTW implements Modifier<String[],Double> {
	private final String mTemplateName;
	private final TimeSeries mTemplateSeries;
	private final int mRadius;
	private Double mDistance;
	private final char mDelimiterSeries;
	private final boolean mIsLabeledSeries;
	public void setmDistFunc(DistanceFunction mDistFunc) {
		this.mDistFunc = mDistFunc;
	}

	private DistanceFunction mDistFunc;
	public FastDTW(String FileName , boolean isFirstColTime, boolean isLabeledTemplate , boolean isLabeledSeries , DistanceFunction distFunc ,char delimiterTemplate, char delimiterSeries, int radius){
		this.mTemplateName=FileName;
		this.mDistFunc=distFunc;

		this.mTemplateSeries = new TimeSeries(this.mTemplateName,isFirstColTime,isLabeledTemplate,delimiterTemplate);
		if (radius > mTemplateSeries.size()){
			radius = mTemplateSeries.size()-1;
		}
		this.mRadius=radius;
		this.mDistance=new Double(Double.MAX_VALUE);
		this.mDelimiterSeries=delimiterSeries;
		this.mIsLabeledSeries = isLabeledSeries;
	}

	@Override
	public Double modify() {
		return mDistance;
	}

	@Override
	public void aggregate(String[] input) {
		TimeSeries ts = new TimeSeries(input,false,mIsLabeledSeries,mDelimiterSeries);
		final TimeWarpInfo info = com.dtw.FastDTW.getWarpInfoBetween(this.mTemplateSeries, ts, new Integer(mRadius), mDistFunc);
		mDistance = new Double(info.getDistance());
	}

	@Override
	public int clear() {
		return 0;
	}

}
