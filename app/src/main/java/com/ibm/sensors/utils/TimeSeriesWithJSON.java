package com.ibm.sensors.utils;

import com.timeseries.TimeSeries;
import com.timeseries.TimeSeriesPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nexus on 22/10/2015.
 */
public class TimeSeriesWithJSON extends TimeSeries{
	public TimeSeriesWithJSON(int numOfDimensions) {
		super(numOfDimensions);
	}

	public TimeSeriesWithJSON(TimeSeries origTS) {
		super(origTS);
	}

	public TimeSeriesWithJSON(String inputFile, boolean isFirstColTime) {
		super(inputFile, isFirstColTime);
	}

	public TimeSeriesWithJSON(String inputFile, char delimiter) {
		super(inputFile, delimiter);
	}

	public TimeSeriesWithJSON(String inputFile, boolean isFirstColTime, char delimiter) {
		super(inputFile, isFirstColTime, delimiter);
	}

	public TimeSeriesWithJSON(String[] ts, boolean isFirstColTime, boolean isLabeled, char
			delimiter) {
		super(ts, isFirstColTime, isLabeled, delimiter);
	}

	public TimeSeriesWithJSON(String inputFile, boolean isFirstColTime, boolean isLabeled, char
			delimiter) {
		super(inputFile, isFirstColTime, isLabeled, delimiter);
	}

	public TimeSeriesWithJSON(String inputFile, int[] colToInclude, boolean isFirstColTime) {
		super(inputFile, colToInclude, isFirstColTime);
	}

	public TimeSeriesWithJSON(String[] ts, int[] colToInclude, boolean isFirstColTime, boolean
			isLabeled, char delimiter) {
		super(ts, colToInclude, isFirstColTime, isLabeled, delimiter);
	}

	public TimeSeriesWithJSON(String inputFile, int[] colToInclude, boolean isFirstColTime,
	                          boolean isLabeled, char delimiter) {
		super(inputFile, colToInclude, isFirstColTime, isLabeled, delimiter);
	}
	public JSONArray toJSON() throws JSONException {
		JSONArray result= new JSONArray();
		for (int i=0;i<this.size();i++){
			double[] tmp = this.getMeasurementVector(i);
			JSONArray point = new JSONArray();
			for (double val : tmp){
				point.put(val);
			}
			result.put(point);
		}
		return result;
	}

	public boolean fromJsonArray(JSONArray input) throws JSONException {
		for (int i=0;i<input.length();i++){
			JSONArray JSONRow = (JSONArray) input.get(i);
			double [] row = new double[JSONRow.length()];
			for (int j=0;j<JSONRow.length();j++){
				row[j]=(double) JSONRow.get(j);
			}
			this.addLast(i,new TimeSeriesPoint(row));
		}
		return true;
	}
}
