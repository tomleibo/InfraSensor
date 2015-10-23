package com.ibm.sensors.utils;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

	public static JSONArray fileToJSONArray(String path, String fileName) throws JSONException {
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard.getAbsolutePath()+path,fileName);

//Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			br.close();
		}
		catch (IOException e) {
			//You'll need to add proper error handling here
		}

		return new JSONArray(text.toString());
	}
}
