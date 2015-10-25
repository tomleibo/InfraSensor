package com.ibm.sensors;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.modifiers.Converters.DoubleToTimeSeries;
import com.ibm.sensors.modifiers.FastDTW;
import com.ibm.sensors.rules.RuleFastDTW;
import com.ibm.sensors.rules.RuleTimeSeriesCreator;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.utils.Converters;
import com.ibm.sensors.utils.GeneralUtils;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.TimeSeriesWithJSON;
import com.util.DistanceFunctionFactory;


public class MainActivity extends Activity implements GenericObserver<EventWrapper> {
    private final String TAG = "main activity";
    private SensorManager mSensorManager;
    private static final String SERVER_URL = "http://10.0.0.4:8080/SensorDataServer/SensorListener";

    private TextView tv=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tv=(TextView)findViewById(R.id.textView);
            Env env = new Env(this);
            /*if (!env.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_SCREEN_ON_OFF, this)) {
                tv.setText("subscription failed");
            }
            if (!env.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION, this)) {
                tv.setText("subscription failed");
            }*/
            TimeSeriesWithJSON a = new TimeSeriesWithJSON(3);
            a.fromJsonArray(Converters.fileToJSONArray("/TimeSeries","myData.txt"));
            FastDTW myDTW = new FastDTW(a, DistanceFunctionFactory.getDistFnByName("EuclideanDistance"),100);

            RuleFastDTW my = new RuleFastDTW(env, new ImmidiateStrategy(),myDTW);
            DoubleToTimeSeries tmp = new DoubleToTimeSeries(100,false,3);

            RuleTimeSeriesCreator timeSeriesSaver = new RuleTimeSeriesCreator(env,EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION,tmp);
            if (!env.getEventHandler().subscribe(EventCreatorFactory.Sensors.TYPE_SENSOR_LINEAR_ACCELERATION, timeSeriesSaver)) {
                tv.setText("subscription failed");
            }
            if (!env.getEventHandler().subscribe(EventCreatorFactory.Rules.RuleTimeSeriesCreator, my)) {
                tv.setText("subscription failed");
            }
            if (!env.getEventHandler().subscribe(-2, this)) {
                tv.setText("subscription failed");
            }
/*
            DbHandler db = new DbHandler(this);
            LocationTable loc = new LocationTable("home","sweet home",1,1,1,1,1,1);
            WifiTable wifi  = new WifiTable("ssid1","macmac",123,1234,-1);
            loc.addWifi(wifi);
            long l = loc.insert(db.getWritableDatabase());
            LocationTable locationFromDb = db.getLocation(" where t1." + LocationTable.ID + "="+l, 0, 0);
            tv.setText(locationFromDb.toString());
*/

        }
        catch (Throwable e) {
            GeneralUtils.logException(TAG, e);
        }
        //CommunicationHandler.build(SERVER_URL);

        //logActiveSensors();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
       /* TimeSeriesWithJSON asJSONArray = (TimeSeriesWithJSON) data.getData();
        if (asJSONArray.size()==99){

                File root = android.os.Environment.getExternalStorageDirectory();
                File dir = new File (root.getAbsolutePath() + "/TimeSeries");
                dir.mkdirs();
                File file = new File(dir, "myData.txt");
                try {
                    FileOutputStream f = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(f);
                    pw.println(asJSONArray.toJSON().toString());

                    pw.flush();
                    pw.close();
                    f.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i(TAG, "******* File not found. Did you" +
                            " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

      /*  try {
            //Log.d("ggg", Converters.fileToJSONArray("/TimeSeries","myData.txt").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
