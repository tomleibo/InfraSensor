package com.ibm.sensors.core;

import android.util.Log;

import com.ibm.sensors.EventWrappers.EventWrapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * CONSIDER CONVERTING TO EXECUTOR_SERVICE INSTEAD OF ONE THREAD.
 */
public class CommunicationHandler implements Runnable{
    private static final int CAPACITY = 1000;
    private static final String TAG = "CommunicationHandler";

    private static CommunicationHandler instance=null;
    private final BlockingQueue<EventWrapper> eventQueue;
    private final Thread runningThread;
    private boolean shouldStop = false;
    private final String url;


    private CommunicationHandler(String url) {
        this.eventQueue= new LinkedBlockingDeque<>(CAPACITY);
        this.url=url;
        runningThread = new Thread(this);
        runningThread.start();
    }

    public static CommunicationHandler build(String url) {
        if (instance==null) {
            synchronized (CommunicationHandler.class) {
                if (instance==null) {
                    instance=new CommunicationHandler(url);
                    return instance;
                }
            }
        }
        throw new RuntimeException("instance was initiated twice.");
    }

    public static CommunicationHandler getInstance() {
        return instance;
    }

    public void sendEvent(EventWrapper event) {
        try {
            eventQueue.put(event);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            String json = null;
            try {
                EventWrapper event = eventQueue.take();
                json = EventHandler.get().translateEventToJson(event);
            } catch (InterruptedException e) {
                json="interrupted exception occured.";
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "sending json: " + json.substring(0, Math.min(10, json.length())));
            String response = executePost(url,"json="+json);
            Log.wtf(TAG,"RESPONSE: "+response);
        }
    }

    public void stop() {
        //executor.shutdown();
        shouldStop = true;
        runningThread.interrupt();
    }

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();

            int erCode = connection.getResponseCode();
            Log.wtf(TAG,"error code: "+erCode);
            //Get Response
            InputStream is=null;
            if (erCode == 200) {
                is = connection.getInputStream();
            }
            else {
                is = connection.getErrorStream();
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            wr.close();
            return response.toString();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage()+"\n");
            sb.append(e.getLocalizedMessage()+"\n");
            sb.append(e.getClass().getCanonicalName()+"\n");
            for (StackTraceElement el : e.getStackTrace()) {
                sb.append(el.toString()+"\n");
            }
            return sb.toString();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
