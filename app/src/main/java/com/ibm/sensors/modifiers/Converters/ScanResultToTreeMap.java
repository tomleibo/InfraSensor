package com.ibm.sensors.modifiers.Converters;

import android.net.wifi.ScanResult;

import com.ibm.sensors.modifiers.abstracts.Modifier;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by nexus on 18/10/2015.
 */
public class ScanResultToTreeMap implements Modifier<List<ScanResult>,Map<String,Integer>> {
	TreeMap<String,Integer> mData;
	@Override
	public Map<String, Integer> modify() {
		return mData;
	}

	@Override
	public void aggregate(List<ScanResult> input) {
		mData=new TreeMap<String,Integer>();
		for (int i=0;i<input.size();i++){
			mData.put(input.get(i).BSSID,input.get(i).level);
		}
	}

	@Override
	public int clear() {
		int size = this.mData.size();
		this.mData= new TreeMap<String,Integer>();
		return size;
	}
}
