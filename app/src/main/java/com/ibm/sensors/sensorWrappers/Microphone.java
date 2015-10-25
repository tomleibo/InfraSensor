package com.ibm.sensors.sensorWrappers;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.ibm.sensors.EventWrappers.AudioRecordingEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by nexus on 22/10/2015.
 */
public class Microphone extends AbstractSensorWrapper{

	public AudioRecord mAudioRecord;
	public int mSamplesRead; //how many samples read
	public int mBuffersizebytes;
	public int mBuflen;
	public int mChannelConfiguration;
	public int mAudioEncoding;// = AudioFormat.ENCODING_PCM_16BIT;
	public short[] mBuffer; //+-32767
	public static final int SAMPPERSEC=8000; //samp per sec 8000, 11025, 22050 44100 or 48000
	private static final ScheduledExecutorService worker =
			Executors.newSingleThreadScheduledExecutor();
	public Microphone(EventHandler handler,int channelConf, int audioEncoding) {
		super(handler);
		this.mChannelConfiguration=channelConf;
		this.mAudioEncoding=audioEncoding;
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Sensors.TYPE_SENSOR_MICROPHONE;
	}
	private MediaRecorder mRecorder;
	@Override
	public boolean register(int delayMillis, Object o) {
		mBuffersizebytes = AudioRecord.getMinBufferSize(SAMPPERSEC,mChannelConfiguration,mAudioEncoding); //4096 on ion
		mBuffer = new short[mBuffersizebytes];
		mBuflen=mBuffersizebytes/2;
		mAudioRecord = new AudioRecord(android.media.MediaRecorder.AudioSource.MIC,SAMPPERSEC,
				mChannelConfiguration,mAudioEncoding,mBuffersizebytes);
		try {
			mAudioRecord.startRecording();
			mSamplesRead = mAudioRecord.read(mBuffer, 0, mBuffersizebytes);
			mAudioRecord.stop();
		} catch (Throwable t) {
		 Log.e("AudioRecord", "Recording Failed");
			return false;
		}
		worker.schedule(new Runnable() {
			public void run() {
				unregister(null);
			}
		}, delayMillis, TimeUnit.MILLISECONDS);
		return true;
	}


	@Override
	public boolean unregister(Object o) {
		mAudioRecord.release();
		mHandler.handleEvent(new AudioRecordingEvent(System.currentTimeMillis(),this,this.mBuffer));
		return true;
	}

	@Override
	public boolean isRegistered() {
		return true;
	}
}
