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
import DTW.util.DistanceFunctionFactory;

import org.json.JSONException;

/**
 * Created by nexus on 25/10/2015.
 */
public class LinearAccelerometerDTWTest extends AbstractMainActivityConf{


	private Env mEnv;

	@Override
	protected void _Main(Env env, Activity ac) {
		this.mEnv=env;
		TextView tv = (TextView) this.mAc.getWindow().getDecorView().findViewById(R.id
				.textView);
		tv.setText("hello");
		TimeSeriesWithJSON a = new TimeSeriesWithJSON(3);
		try {
			a.fromJsonArray(Converters.fileToJSONArray("/TimeSeries", "myData.txt"));
			FastDTW myDTW = new FastDTW(a, DistanceFunctionFactory.getDistFnByName("EuclideanDistance"),100);

			RuleFastDTW my = new RuleFastDTW(env, new ImmidiateStrategy(),myDTW);
			DoubleToTimeSeries tmp = new DoubleToTimeSeries(100,false,3);

			//RuleTimeSeriesCreator timeSeriesSaver = new RuleTimeSeriesCreator(env, EventCreatorFactory.Events.TYPE_EVENT_LINEAR_VELOCITY_CHANGE,tmp);
			if (!env.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_LINEAR_VELOCITY_CHANGE, this,null)) {
				tv.setText("subscription failed");
			}
		/*	if (!env.getEventHandler().subscribe(EventCreatorFactory.Rules.RuleTimeSeriesCreator, my)) {
				tv.setText("subscription failed");
			}
			if (!env.getEventHandler().subscribe(-2, this)) {
				tv.setText("subscription failed");
			}*/
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}



	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		TextView tv = (TextView) this.mAc.getWindow().getDecorView().findViewById(R.id
				.textView);
		String data1 = new String();
		for (int i=0;i<3;i++){
			data1+=","+((Float[])data.getData())[i];
		}
		tv.setText(data1);
	}
}
