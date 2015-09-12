package com.ibm.sensors.sensorWrappers;

import com.ibm.sensors.EventWrappers.FileSizeChangedEvent;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.SensorAndRuleFactory;

import java.io.File;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public class FileSizeChecker implements SensorWrapper<File>,Runnable{

    private EventHandler handler;
    private Thread thread;
    private boolean shouldStop = false;
    private File file;
    private int delay;
    private int prevSize=-1;
    boolean isRegistered=false;

    public FileSizeChecker(EventHandler handler) {
        this.handler = handler;
        this.thread = new Thread();
    }

    @Override
    public int getType() {
        return SensorAndRuleFactory.FILE_SIZE_CHECKER;
    }

    @Override
    public boolean register(int delayMillis, File f) {
        if (file!=null) {
            return false;
        }
        this.delay=delay;
        this.file=f;
        thread.run();
        isRegistered=true;
        return true;
    }

    @Override
    public boolean unregister(File f) {
        if (f.equals(this.file)) {
            file=null;
            shouldStop = true;
            thread.interrupt();
            return true;
        }
        isRegistered=false;
        return false;
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

    @Override
    public void run() {
        while(!shouldStop) {
            try {
                if (file.length() != prevSize) {
                    handler.handleEvent(new FileSizeChangedEvent(this,file,file.length()));
                }
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                //ignore. unless it is implictly stopped.
            }
        }
    }
}