package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.R;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.Converters.DoubleToTimeSeries;
import com.ibm.sensors.modifiers.FastDTW;
import com.ibm.sensors.rules.RuleFastDTW;
import com.ibm.sensors.rules.RuleTimeSeriesCreator;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.utils.Converters;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.TimeSeriesWithJSON;
import com.util.DistanceFunctionFactory;

import org.json.JSONException;

/**
 * Created by nexus on 25/10/2015.
 */
public class LinearAccelerometerDTWTest extends AbstractMainActivityConf{


	private Env mEnv;


	@Override
	public void Main(Env env) {
		this.mEnv=env;
		TextView tv = (TextView) ((Activity) env.getContext()).getWindow().getDecorView().findViewById(R.id
				.textView);
		TimeSeriesWithJSON a = new TimeSeriesWithJSON(3);
		try {
			a.fromJsonArray(Converters.fileToJSONArray("/TimeSeries", "myData.txt"));
			FastDTW myDTW = new FastDTW(a, DistanceFunctionFactory.getDistFnByName("EuclideanDistance"),100);

			RuleFastDTW my = new RuleFastDTW(env, new ImmidiateStrategy(),myDTW);
			DoubleToTimeSeries tmp = new DoubleToTimeSeries(100,false,3);

			RuleTimeSeriesCreator timeSeriesSaver = new RuleTimeSeriesCreator(env, EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION,tmp);
			if (!env.getEventHandler().subscribe(EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION, timeSeriesSaver)) {
				tv.setText("subscription failed");
			}
			if (!env.getEventHandler().subscribe(EventCreatorFactory.Rules.RuleTimeSeriesCreator, my)) {
				tv.setText("subscription failed");
			}
			if (!env.getEventHandler().subscribe(-2, this)) {
				tv.setText("subscription failed");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}



	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		if (mEnv!=null){
			TextView tv = (TextView) ((Activity) mEnv.getContext()).getWindow().getDecorView().findViewById(R.id
				.textView);
		}
	}
}
