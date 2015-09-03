package com.ibm.sensors.com.ibm.sensorWrappers;

import com.ibm.sensors.com.ibm.EventWrappers.FileSizeChangedEvent;
import com.ibm.sensors.com.ibm.core.EventHandler;
import com.ibm.sensors.com.ibm.sensors.interfaces.SensorWrapper;
import com.ibm.sensors.com.ibm.utils.SensorTypes;

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

    public FileSizeChecker(EventHandler handler) {
        this.handler = handler;
        this.thread = new Thread();
    }

    @Override
    public int getType() {
        return SensorTypes.FILE_SIZE_CHECKER;
    }

    @Override
    public boolean register(int delayMillis, File f) {
        if (file!=null) {
            return false;
        }
        this.delay=delay;
        this.file=f;
        thread.run();
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
        return false;
    }

    @Override
    public void run() {
        while(!shouldStop) {
            try {
                if (file.length() != prevSize) {
                    handler.handleEvent(new FileSizeChangedEvent(file,file.length()));
                }
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                //ignore. unless it is implictly stopped.
            }
        }
    }
}
