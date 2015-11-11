package com.ibm.sensors.sensorWrappers;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

import com.ibm.sensors.EventWrappers.AudioRecordingEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by nexus on 22/10/2015.
 */
public class Microphone extends AbstractSensorWrapper{
	SensorConfiguration sc;
	public AudioRecord audioRecord;
	public int mSamplesRead; //how many samples read
	public int buffersizebytes;
	public int buflen;
	public int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	public int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	public short[] buffer; //+-32767
	public static final int SAMPPERSEC = 8000; //samp per sec 8000, 11025, 22050 44100 or 48000
	private Thread recordingThread;
	Microphone instance;
	public Microphone(Env env) {
		super(env);
		instance=this;
		buffersizebytes = AudioRecord.getMinBufferSize(SAMPPERSEC,channelConfiguration,audioEncoding); //4096 on ion
		buffer = new short[buffersizebytes];
		buflen=buffersizebytes/2;
		audioRecord = new AudioRecord(android.media.MediaRecorder.AudioSource.MIC,SAMPPERSEC,
				channelConfiguration,audioEncoding,buffersizebytes); //constructor
	}

	public void trigger(){
		acquire();
		//dump();
	}

	public void acquire(){

		recordingThread = new Thread(new Runnable() {
			Queue<ByteBuffer> qArray;
			@Override
			public void run() {
			//	qArray=new LinkedTransferQueue<>();
				ByteBuffer bData = ByteBuffer.allocate(buffersizebytes);
				byte[] bbarray = new byte[bData.remaining()];
				bData.get(bbarray);
				audioRecord.startRecording();
				for (int i=0;i<30;i++) {

					int result = audioRecord.read(bbarray, 0, buffersizebytes);
					System.out.println("READ DATA");
					if (result > 0) {
					//	qArray.add(bData);
						bData = ByteBuffer.allocate(buffersizebytes);
					} else if (result == AudioRecord.ERROR_INVALID_OPERATION) {
						Log.e("Recording", "Invalid operation error");
						break;
					} else if (result == AudioRecord.ERROR_BAD_VALUE) {
						Log.e("Recording", "Bad value error");
						break;
					} else if (result == AudioRecord.ERROR) {
						Log.e("Recording", "Unknown error");
						break;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						break;
					}
				}
			}

		},"AudioRecorder Thread");

		recordingThread.start();

	}

	public void dump(){
		/*TextView tv = new TextView(this);
		setContentView(tv);
		tv.setTextColor(Color.WHITE);
		tv.setText("buffersizebytes "+buffersizebytes+"\n");
		for(int i = 0; i < 256; i++){
			tv.append(" "+buffer[i]);
		}
		tv.invalidate();
	*/
	}
	@Override
	public int getType() {
		return EventCreatorFactory.Sensors.TYPE_SENSOR_MICROPHONE;
	}

	@Override
	public boolean register(SensorConfiguration s) throws Exception{
		this.sc=s;
		trigger();
		return true;
		/*
		if (s.getString(SensorConfiguration.DELAY)==null){
			throw new Exception("missing "+SensorConfiguration.DELAY+" in configuration");
		}
		if (s.getString(SensorConfiguration.REPEAT)==null){
			throw new Exception("missing "+SensorConfiguration.REPEAT+" in configuration");
		}
		if (s.getString(SensorConfiguration.DURATION)==null){
			throw new Exception("missing "+SensorConfiguration.DURATION+" in configuration");
		}

		return false;
	*/
	}

	@Override
	public boolean unregister() {
		return false;
	}

	@Override
	public boolean isRegistered() {
		return false;
	}


}


