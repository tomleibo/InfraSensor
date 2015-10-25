package com.ibm.sensors;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.MainConfigurations.LinearAccelerometerDTWTest;
import com.ibm.sensors.MainConfigurations.MainConfInterface;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.utils.MultiGenericObservable;


public class MainActivity extends Activity implements GenericObserver<EventWrapper> {
   // private final String TAG = "main activity";
   // private SensorManager mSensorManager;
    private MainConfInterface conf;
    //private static final String SERVER_URL = "http://10.0.0.4:8080/SensorDataServer/SensorListener";

    private TextView tv=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conf = new LinearAccelerometerDTWTest();
        conf.Main(new Env(this.getApplicationContext()));
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
        conf.update(object,data);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
