package com.services;

import com.api.connection.Connection;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class DownloadService extends IntentService {
	public static final int STATUS_RUNNING = 0;
	public static final int STATUS_FINISHED = 1;
	public static final int STATUS_ERROR = 2;

	private static final String TAG = "DownloadService";

	public DownloadService() {
		super(DownloadService.class.getName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d(TAG, "Service Started!");

		final ResultReceiver receiver = intent.getParcelableExtra("receiver");

		Bundle bundle = new Bundle();

		/* Update UI: Download Service is Running */
		receiver.send(STATUS_RUNNING, Bundle.EMPTY);

		String url = intent.getStringExtra("url");
		try {
			String response = Connection.getJSONfromURL(url);
			bundle.putString("result", response);
			receiver.send(STATUS_FINISHED, bundle);
		} catch (Exception e) {
			/* Sending error message back to activity */
			bundle.putString(Intent.EXTRA_TEXT, e.toString());
			receiver.send(STATUS_ERROR, bundle);
		}

		this.stopSelf();
	}

}
