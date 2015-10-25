package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.ibm.sensors.core.EventHandler;

/**
 * Created by nexus on 25/10/2015.
 */
public class PhoneCallSensorWrapper extends AbstractSensorWrapper<Boolean>{
	public class PhoneServiceReceiver extends BroadcastReceiver {
		private EventHandler mEventHandler;

		public PhoneServiceReceiver(EventHandler eventHandler) {
			mEventHandler = eventHandler;
		}

		@Override
		public void onReceive(final Context context, Intent intent) {
			TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			telephony.listen(new PhoneStateListener() {
				@Override
				public void onCallStateChanged(int state, String incomingNumber) {

					//if (state == TelephonyManager.CALL_STATE_IDLE)
					super.onCallStateChanged(state, incomingNumber);
					switch (state) {
						case TelephonyManager.CALL_STATE_IDLE:
							break;
				//		case TelephonyManager.CALL_STATE_OFFHOOK:
			//				break;
			//			case TelephonyManager.CALL_STATE_OFFHOOK:
		//					break;
					}
					//mEventHandler.handleEvent(new);
				}
			}, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}
	private PhoneServiceReceiver mSensor;

	public PhoneCallSensorWrapper(EventHandler handler) {
		super(handler);
	}



	@Override
	public int getType() {
		return 0;
	}

	@Override
	public boolean register(int delayMillis, Boolean aBoolean) {
		this.mSensor = new PhoneServiceReceiver(this.mHandler);
		return true;
	}

	@Override
	public boolean unregister(Boolean aBoolean) {
		return false;
	}

	@Override
	public boolean isRegistered() {
		return false;
	}
}
