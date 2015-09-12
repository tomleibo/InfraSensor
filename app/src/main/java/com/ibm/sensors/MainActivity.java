package com.ibm.sensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.utils.MultiGenericObservable;


public class MainActivity extends Activity implements GenericObserver<EventWrapper> {
    private String TAG = "main activity";
    private SensorManager mSensorManager;
    private static final String SERVER_URL = "http://10.0.0.4:8080/SensorDataServer/SensorListener";

    private TextView tv=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tv=(TextView)findViewById(R.id.textView);

            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            EventHandler handler = EventHandler.build(mSensorManager);
            if (!handler.subscribe(SensorAndRuleFactory.RULE_EXTREME_MOVE,this)) {
                tv.setText("subscription failed");
            }
        }
        catch (Exception e) {

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
        tv.setText("max speed:  "+data.getData().toString());
    }
}
