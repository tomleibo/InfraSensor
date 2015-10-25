package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 25/10/2015.
 */
public class AudioRecordingEvent extends AbstractEventWrapper<short[]>{
	private short[] mBuffer;
	public AudioRecordingEvent(long timestamp, EventCreator sensor,short[] buffer) {
		super(timestamp, sensor);
		this.mBuffer=buffer;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.AUDION_RECORDING_EVENT;
	}

	@Override
	public short[] getData() {
		return this.mBuffer;
	}
}
