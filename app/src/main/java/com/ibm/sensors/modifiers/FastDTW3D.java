package com.ibm.sensors.modifiers;

import com.dtw.TimeWarpInfo;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleValueModifier;
import com.timeseries.TimeSeries;
import com.util.DistanceFunction;

/**
 * Created by nexus on 18/10/2015.
 */
public class FastDTW3D extends AbstractSingleValueModifier<String[][],Double[]>{
	private Integer[] mRadiuss;
	private DistanceFunction[] mDistFunctions;
	private TimeSeries mTemplateSeries;
	private Double[] mResults;
	public FastDTW3D(int[] radiuses,DistanceFunction[] distFunctions){
		this.mRadiuss=new Integer[3];
		for (int i=0;i<3;i++){
		this.mRadiuss[i]=new Integer(radiuses[i]);
		}
		this.mDistFunctions=distFunctions;
	}
	public FastDTW3D(int[] radiuses,DistanceFunction[] distFunctions,String[] fileNames,boolean[] isFirstColTime,boolean[] isLabeledTemplate,char[] delimiterTemplate){
		this(radiuses,distFunctions);
		this.mResults=new Double[3];
		for (int i=0;i<3;i++){
			this.mResults[i]=new Double(Double.MAX_VALUE);
			this.mTemplateSeries = new TimeSeries(fileNames[i],isFirstColTime[i],isLabeledTemplate[i],delimiterTemplate[i]);
			if (this.mRadiuss[i] > mTemplateSeries.size()){
				mRadiuss[i] = mTemplateSeries.size()-1;
			}
		}
	}

	public FastDTW3D(int[] radiuses,DistanceFunction[] distFunctions,String[][] series,boolean[] isFirstColTime,boolean[] isLabeledTemplate,char[] delimiterTemplate){
		this(radiuses,distFunctions);
		this.mResults=new Double[3];
		for (int i=0;i<3;i++){
			this.mResults[i]=new Double(Double.MAX_VALUE);
			this.mTemplateSeries = new TimeSeries(series[i],isFirstColTime[i],isLabeledTemplate[i],delimiterTemplate[i]);
			if (this.mRadiuss[i] > mTemplateSeries.size()){
				mRadiuss[i] = mTemplateSeries.size()-1;
			}
		}
	}

	@Override
	public Double[] modify() {
		return this.mResults;
	}

	@Override
	public void aggregate(String[][] input) {
		for (int i=0;i<3;i++){
			TimeSeries ts = new TimeSeries(input[i],false,false,',');
			final TimeWarpInfo info = com.dtw.FastDTW.getWarpInfoBetween(this.mTemplateSeries, ts, this.mRadiuss[i], this.mDistFunctions[i]);
			this.mResults[i] = new Double(info.getDistance());
		}
	}
}
