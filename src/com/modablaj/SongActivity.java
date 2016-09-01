package com.modablaj;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.api.connection.Connection;
import com.api.connection.SongRetrieval;
import com.model.Line;
import com.model.Song;
import com.services.DownloadResultReceiver;
import com.services.DownloadService;

public class SongActivity extends Activity implements
		DownloadResultReceiver.Receiver {

	private String id;
	private DownloadResultReceiver mReceiver;
	private ListView lines;
	private TextView songName, singerName;
	private ProgressBar load;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song);
		getActionBar().hide();

		Bundle b = this.getIntent().getExtras();
		id = b.getString("songId");

		
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.setVisibility(View.INVISIBLE);
		load = (ProgressBar) findViewById(R.id.progressBar1);
		load.getIndeterminateDrawable().setColorFilter(0xFFFF0000,
				android.graphics.PorterDuff.Mode.MULTIPLY);
		load.setVisibility(View.VISIBLE);
		
		songName = (TextView) findViewById(R.id.songName);
		singerName = (TextView) findViewById(R.id.singerName);

		if (Connection.isConnectedToInternet(getApplicationContext())) {
			/* Starting Download Service */
			mReceiver = new DownloadResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
					DownloadService.class);

			/* Send optional extras to Download IntentService */
			intent.putExtra("url", "http://modablaj.com/app/android/song/" + id);
			intent.putExtra("receiver", mReceiver);
			intent.putExtra("requestId", 101);
			startService(intent);
		} else {
			new AlertDialog.Builder(SongActivity.this)
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
		SongRetrieval songRetrieval = new SongRetrieval();
		if (songRetrieval.GetMainAPI(response)) {
			load.setVisibility(View.GONE);
			ll.setVisibility(View.VISIBLE);
			Song song = songRetrieval.getTheSong();
			songName.setText(song.getName());
			String firstName = song.getSinger1().getName();
			if (song.getSinger2().getName() != null)
				singerName.setText(firstName + " and "
						+ song.getSinger2().getName());
			else
				singerName.setText(firstName);
			TextView by = (TextView) findViewById(R.id.by);
			by.setText("By");
			ArrayAdapter<Line> adapter = new SongAdapter(song.getLyrics());
			lines = (ListView) findViewById(R.id.lines);
			lines.setAdapter(adapter);
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
		}
	}

	private class SongAdapter extends ArrayAdapter<Line> {

		private List<String> english;
		private List<String> arabic;

		public SongAdapter(List<Line> lyrics) {
			super(SongActivity.this, R.layout.song_line_item, lyrics);
			english = new ArrayList<String>();
			arabic = new ArrayList<String>();
			for (int i = 0; i < lyrics.size(); i++) {
				english.add(lyrics.get(i).getLyrics());
				arabic.add(lyrics.get(i).getTranslatedLyrics());
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null)
				itemView = getLayoutInflater().inflate(R.layout.song_line_item,
						parent, false);

			TextView englishTV = (TextView) itemView
					.findViewById(R.id.englishLine);
			englishTV.setText(english.get(position));

			TextView arabicTV = (TextView) itemView
					.findViewById(R.id.arabicLine);
			arabicTV.setText(arabic.get(position));
			return itemView;
		}
	}
}
