package com.ibm.sensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.sensors.com.ibm.core.CommunicationHandler;
import com.ibm.sensors.com.ibm.core.EventHandler;


public class MainActivity extends Activity{
    private String TAG = "main activity";
    private SensorManager mSensorManager;
    private static final String SERVER_URL = "http://10.0.0.4:8080/SensorDataServer/SensorListener";

    private TextView tv=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        EventHandler.build(mSensorManager).registerMotionSensors(true);
        CommunicationHandler.build(SERVER_URL);
        //tv=(TextView)findViewById(R.id.textView);

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

}
