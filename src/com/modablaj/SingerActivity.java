package com.modablaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.api.connection.Connection;
import com.api.connection.SingerRetrieval;
import com.model.Singer;
import com.model.Song;
import com.services.DownloadResultReceiver;
import com.services.DownloadService;
import com.utils.MyListAdapter;
import com.widget.listview.IndexableListView;

@SuppressLint("NewApi")
public class SingerActivity extends Activity implements
		DownloadResultReceiver.Receiver {
	private String id;
	private IndexableListView songs;
	private DownloadResultReceiver mReceiver;
	private ProgressBar load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singer);
		Bundle b = this.getIntent().getExtras();
		id = b.getString("singerId");

		load = (ProgressBar) findViewById(R.id.progressBar1);
		load.getIndeterminateDrawable().setColorFilter(0xFFFF0000,
				android.graphics.PorterDuff.Mode.MULTIPLY);
		load.setVisibility(View.VISIBLE);
		getActionBar().setTitle("");
		if (Connection.isConnectedToInternet(getApplicationContext())) {
			/* Starting Download Service */
			mReceiver = new DownloadResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
					DownloadService.class);

			/* Send optional extras to Download IntentService */
			intent.putExtra("url", "http://modablaj.com/app/android/singer/"
					+ id);
			intent.putExtra("receiver", mReceiver);
			intent.putExtra("requestId", 101);
			startService(intent);
		} else {
			new AlertDialog.Builder(SingerActivity.this)
					.setTitle("Oops")
					.setMessage("No Internet Connection!")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							}).show();

		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
		case DownloadService.STATUS_RUNNING:
			break;
		case DownloadService.STATUS_FINISHED:
			String response = resultData.getString("result");
			Log.e("respooo", response);
			intializeProcess(response);
			break;
		case DownloadService.STATUS_ERROR:
			/* Handle the error */

			break;
		}
	}

	private void intializeProcess(String response) {
		SingerRetrieval singerRetrieval = new SingerRetrieval();
		if (singerRetrieval.GetMainAPI(response)) {
			load.setVisibility(View.GONE);
			Singer singer = singerRetrieval.getSinger();
			getActionBar().setTitle(singer.getName());
			getActionBar().setIcon(R.drawable.singer_icon);
			String mSections = handleSectionSong(singer.getSingersSongs());

			char[] ar = mSections.toCharArray();
			Arrays.sort(ar);
			mSections = String.valueOf(ar);

			for (int i = 0; i < mSections.length(); i++) {
				Song virtual = new Song();
				virtual.setName(String.valueOf(mSections.charAt(i)));
				singer.getSingersSongs().add(virtual);
			}
			Collections.sort(singer.getSingersSongs());
			List<Object> objectList = new ArrayList<Object>(
					singer.getSingersSongs());
			ArrayAdapter<Object> adapter = new MyListAdapter(this,
					R.layout.listrow_user, objectList, mSections);
			
			songs = (IndexableListView) findViewById(R.id.listview);
			songs.setAdapter(adapter);
			songs.setFastScrollEnabled(true);
		} else {
			new AlertDialog.Builder(this)
					.setTitle("Oops")
					.setMessage("No Internet Connection!")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							}).show();
			finish();
		}
	}

	private String handleSectionSong(List<Song> items) {
		String mSections = "";
		char prev = ',';
		int g = 0;
		for (int i = 0; i < items.size(); i++) {
			Song song = (Song) items.get(i);
			char currentLetter = song.getName().charAt(0);
			if ((currentLetter >= 'a' && currentLetter <= 'z')
					|| (currentLetter >= 'A' && currentLetter <= 'Z')) {
				if (currentLetter != prev) {
					mSections += currentLetter;
					prev = currentLetter;
				}
			} else if (g == 0) {
				mSections += "#";
				g = 1;
			}
		}
		return mSections;
	}
}
