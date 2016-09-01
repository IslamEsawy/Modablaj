package com.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.api.connection.Connection;
import com.api.connection.MainRetrieval;
import com.modablaj.R;
import com.modablaj.SingerActivity;
import com.modablaj.SongActivity;
import com.model.Singer;
import com.model.Song;
import com.services.DownloadResultReceiver;
import com.services.DownloadService;

public class MainTabFragment extends Fragment implements
		DownloadResultReceiver.Receiver, OnClickListener {

	private TextView[] mores;
	private TextView[] singers;
	private ImageView[] singer1SongsImg;
	private ImageView[] singer2SongsImg;
	private ImageView[] singer3SongsImg;
	private ImageView[] singer4SongsImg;
	private ImageView[] singer5SongsImg;
	private ImageView[] singer6SongsImg;
	private ImageView[] singer7SongsImg;
	private ImageView[] singer8SongsImg;
	private TextView[] singer1Songs;
	private TextView[] singer2Songs;
	private TextView[] singer3Songs;
	private TextView[] singer4Songs;
	private TextView[] singer5Songs;
	private TextView[] singer6Songs;
	private TextView[] singer7Songs;
	private TextView[] singer8Songs;
	private List<Singer> popularSingers;
	private List<Song> popularSingersSongs;
	private ProgressBar load;
	private ScrollView scrollView;
	
	public final static String TAG = MainTabFragment.class.getSimpleName();
	View rootView;
	public static final String ARG_SECTION_NUMBER = "section_number";
	private DownloadResultReceiver mReceiver;

	public MainTabFragment() {
		// TODO Auto-generated constructor stub
	}

	public static MainTabFragment newInstance() {
		return new MainTabFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG, "heree1");
		rootView = inflater.inflate(R.layout.main_tab_fragment, container,
				false);

		load = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		load.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
		load.setVisibility(View.VISIBLE);
		scrollView = (ScrollView) rootView.findViewById(R.id.scroll);
		scrollView.setVisibility(View.INVISIBLE);
		mores = new TextView[8];
		singers = new TextView[8];
		singers[0] = (TextView) rootView.findViewById(R.id.singer1);
		singers[1] = (TextView) rootView.findViewById(R.id.singer2);
		singers[2] = (TextView) rootView.findViewById(R.id.singer3);
		singers[3] = (TextView) rootView.findViewById(R.id.singer4);
		singers[4] = (TextView) rootView.findViewById(R.id.singer5);
		singers[5] = (TextView) rootView.findViewById(R.id.singer6);
		singers[6] = (TextView) rootView.findViewById(R.id.singer7);
		singers[7] = (TextView) rootView.findViewById(R.id.singer8);

		mores[0] = (TextView) rootView.findViewById(R.id.more1);
		mores[1] = (TextView) rootView.findViewById(R.id.more2);
		mores[2] = (TextView) rootView.findViewById(R.id.more3);
		mores[3] = (TextView) rootView.findViewById(R.id.more4);
		mores[4] = (TextView) rootView.findViewById(R.id.more5);
		mores[5] = (TextView) rootView.findViewById(R.id.more6);
		mores[6] = (TextView) rootView.findViewById(R.id.more7);
		mores[7] = (TextView) rootView.findViewById(R.id.more8);
		for (int i = 0; i < 8; i++) {
			mores[i].setOnClickListener(this);
			singers[i].setOnClickListener(this);
		}

		initializeSongsImageViews();
		initializeSongsTextViews();

		for (int i = 0; i < 40; i++) {
			if (i >= 0 && i <= 4)
				singer1Songs[i].setOnClickListener(this);
			if (i >= 5 && i <= 9)
				singer2Songs[i % 5].setOnClickListener(this);
			if (i >= 10 && i <= 14)
				singer3Songs[i % 5].setOnClickListener(this);
			if (i >= 15 && i <= 19)
				singer4Songs[i % 5].setOnClickListener(this);
			if (i >= 20 && i <= 24)
				singer5Songs[i % 5].setOnClickListener(this);
			if (i >= 25 && i <= 29)
				singer6Songs[i % 5].setOnClickListener(this);
			if (i >= 30 && i <= 34)
				singer7Songs[i % 5].setOnClickListener(this);
			if (i >= 35 && i <= 39)
				singer8Songs[i % 5].setOnClickListener(this);
		}

		/* Starting Download Service */
		if (Connection.isConnectedToInternet(getActivity()
				.getApplicationContext())) {
			mReceiver = new DownloadResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(),
					DownloadService.class);

			/* Send optional extras to Download IntentService */
			intent.putExtra("url", "http://modablaj.com/app/android/main");
			intent.putExtra("receiver", mReceiver);
			intent.putExtra("requestId", 101);
			getActivity().startService(intent);
		}

		return rootView;
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
		MainRetrieval songsRetrieval = new MainRetrieval();
		if (songsRetrieval.GetMainAPI(response)) {
			load.setVisibility(View.GONE);
			scrollView.setVisibility(View.VISIBLE);
			popularSingers = songsRetrieval.getSingers();
			popularSingersSongs = songsRetrieval.getSingersSongs();
			for (int i = 0; i < popularSingers.size(); i++) {
				mores[i].setText(R.string.more);
				singers[i].setText(popularSingers.get(i).getName());
			}
			for (int i = 0; i < popularSingersSongs.size(); i++) {
				if (i >= 0 && i <= 4) {
					singer1SongsImg[i].setImageResource(R.drawable.song_icon24);
					singer1Songs[i].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 5 && i <= 9) {
					singer2SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer2Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 10 && i <= 14) {
					singer3SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer3Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 15 && i <= 19) {
					singer4SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer4Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 20 && i <= 24) {
					singer5SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer5Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 25 && i <= 29) {
					singer6SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer6Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 30 && i <= 34) {
					singer7SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer7Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
				if (i >= 35 && i <= 39) {
					singer8SongsImg[i % 5]
							.setImageResource(R.drawable.song_icon24);
					singer8Songs[i % 5].setText(popularSingersSongs.get(i)
							.getName());
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (!Connection.isConnectedToInternet(getActivity()
				.getApplicationContext())) {
			new AlertDialog.Builder(getActivity())
					.setTitle("Oops")
					.setMessage("No Internet Connection!")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							}).show();
			return;
		}

		if (v.getId() == R.id.singer1 || v.getId() == R.id.more1) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(0).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer2 || v.getId() == R.id.more2) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(1).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer3 || v.getId() == R.id.more3) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(2).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer4 || v.getId() == R.id.more4) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(3).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer5 || v.getId() == R.id.more5) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(4).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer6 || v.getId() == R.id.more6) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(5).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer7 || v.getId() == R.id.more7) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(6).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}
		if (v.getId() == R.id.singer8 || v.getId() == R.id.more8) {
			Intent runProfile = new Intent(getActivity(), SingerActivity.class);
			Bundle b = new Bundle();
			b.putString("singerId",
					String.valueOf(popularSingers.get(7).getId()));
			runProfile.putExtras(b);
			startActivity(runProfile);
		}

		for (int i = 0; i < 40; i++) {
			int id = -1;
			if (i >= 0 && i <= 4 && v.getId() == singer1Songs[i].getId()) {
				id = i;
			}
			if (i >= 5 && i <= 9 && v.getId() == singer2Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 10 && i <= 14 && v.getId() == singer3Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 15 && i <= 19 && v.getId() == singer4Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 20 && i <= 24 && v.getId() == singer5Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 25 && i <= 29 && v.getId() == singer6Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 30 && i <= 34 && v.getId() == singer7Songs[i % 5].getId()) {
				id = i;
			}
			if (i >= 35 && i <= 39 && v.getId() == singer8Songs[i % 5].getId()) {
				id = i;
			}
			if (id != -1) {
				Intent runProfile = new Intent(getActivity(),
						SongActivity.class);
				Bundle b = new Bundle();
				b.putString("songId",
						String.valueOf(popularSingersSongs.get(id).getId()));
				runProfile.putExtras(b);
				startActivity(runProfile);
			}
		}
	}

	public void initializeSongsImageViews() {
		singer1SongsImg = new ImageView[5];
		singer1SongsImg[0] = (ImageView) rootView.findViewById(R.id.song1img);
		singer1SongsImg[1] = (ImageView) rootView.findViewById(R.id.song2img);
		singer1SongsImg[2] = (ImageView) rootView.findViewById(R.id.song3img);
		singer1SongsImg[3] = (ImageView) rootView.findViewById(R.id.song4img);
		singer1SongsImg[4] = (ImageView) rootView.findViewById(R.id.song5img);
		singer2SongsImg = new ImageView[5];
		singer2SongsImg[0] = (ImageView) rootView.findViewById(R.id.song6img);
		singer2SongsImg[1] = (ImageView) rootView.findViewById(R.id.song7img);
		singer2SongsImg[2] = (ImageView) rootView.findViewById(R.id.song8img);
		singer2SongsImg[3] = (ImageView) rootView.findViewById(R.id.song9img);
		singer2SongsImg[4] = (ImageView) rootView.findViewById(R.id.song10img);
		singer3SongsImg = new ImageView[5];
		singer3SongsImg[0] = (ImageView) rootView.findViewById(R.id.song11img);
		singer3SongsImg[1] = (ImageView) rootView.findViewById(R.id.song12img);
		singer3SongsImg[2] = (ImageView) rootView.findViewById(R.id.song13img);
		singer3SongsImg[3] = (ImageView) rootView.findViewById(R.id.song14img);
		singer3SongsImg[4] = (ImageView) rootView.findViewById(R.id.song15img);
		singer4SongsImg = new ImageView[5];
		singer4SongsImg[0] = (ImageView) rootView.findViewById(R.id.song16img);
		singer4SongsImg[1] = (ImageView) rootView.findViewById(R.id.song17img);
		singer4SongsImg[2] = (ImageView) rootView.findViewById(R.id.song18img);
		singer4SongsImg[3] = (ImageView) rootView.findViewById(R.id.song19img);
		singer4SongsImg[4] = (ImageView) rootView.findViewById(R.id.song20img);
		singer5SongsImg = new ImageView[5];
		singer5SongsImg[0] = (ImageView) rootView.findViewById(R.id.song21img);
		singer5SongsImg[1] = (ImageView) rootView.findViewById(R.id.song22img);
		singer5SongsImg[2] = (ImageView) rootView.findViewById(R.id.song23img);
		singer5SongsImg[3] = (ImageView) rootView.findViewById(R.id.song24img);
		singer5SongsImg[4] = (ImageView) rootView.findViewById(R.id.song25img);
		singer6SongsImg = new ImageView[5];
		singer6SongsImg[0] = (ImageView) rootView.findViewById(R.id.song26img);
		singer6SongsImg[1] = (ImageView) rootView.findViewById(R.id.song27img);
		singer6SongsImg[2] = (ImageView) rootView.findViewById(R.id.song28img);
		singer6SongsImg[3] = (ImageView) rootView.findViewById(R.id.song29img);
		singer6SongsImg[4] = (ImageView) rootView.findViewById(R.id.song30img);
		singer7SongsImg = new ImageView[5];
		singer7SongsImg[0] = (ImageView) rootView.findViewById(R.id.song31img);
		singer7SongsImg[1] = (ImageView) rootView.findViewById(R.id.song32img);
		singer7SongsImg[2] = (ImageView) rootView.findViewById(R.id.song33img);
		singer7SongsImg[3] = (ImageView) rootView.findViewById(R.id.song34img);
		singer7SongsImg[4] = (ImageView) rootView.findViewById(R.id.song35img);
		singer8SongsImg = new ImageView[5];
		singer8SongsImg[0] = (ImageView) rootView.findViewById(R.id.song36img);
		singer8SongsImg[1] = (ImageView) rootView.findViewById(R.id.song37img);
		singer8SongsImg[2] = (ImageView) rootView.findViewById(R.id.song38img);
		singer8SongsImg[3] = (ImageView) rootView.findViewById(R.id.song39img);
		singer8SongsImg[4] = (ImageView) rootView.findViewById(R.id.song40img);

	}

	public void initializeSongsTextViews() {
		singer1Songs = new TextView[5];
		singer1Songs[0] = (TextView) rootView.findViewById(R.id.song1);
		singer1Songs[1] = (TextView) rootView.findViewById(R.id.song2);
		singer1Songs[2] = (TextView) rootView.findViewById(R.id.song3);
		singer1Songs[3] = (TextView) rootView.findViewById(R.id.song4);
		singer1Songs[4] = (TextView) rootView.findViewById(R.id.song5);
		singer2Songs = new TextView[5];
		singer2Songs[0] = (TextView) rootView.findViewById(R.id.song6);
		singer2Songs[1] = (TextView) rootView.findViewById(R.id.song7);
		singer2Songs[2] = (TextView) rootView.findViewById(R.id.song8);
		singer2Songs[3] = (TextView) rootView.findViewById(R.id.song9);
		singer2Songs[4] = (TextView) rootView.findViewById(R.id.song10);
		singer3Songs = new TextView[5];
		singer3Songs[0] = (TextView) rootView.findViewById(R.id.song11);
		singer3Songs[1] = (TextView) rootView.findViewById(R.id.song12);
		singer3Songs[2] = (TextView) rootView.findViewById(R.id.song13);
		singer3Songs[3] = (TextView) rootView.findViewById(R.id.song14);
		singer3Songs[4] = (TextView) rootView.findViewById(R.id.song15);
		singer4Songs = new TextView[5];
		singer4Songs[0] = (TextView) rootView.findViewById(R.id.song16);
		singer4Songs[1] = (TextView) rootView.findViewById(R.id.song17);
		singer4Songs[2] = (TextView) rootView.findViewById(R.id.song18);
		singer4Songs[3] = (TextView) rootView.findViewById(R.id.song19);
		singer4Songs[4] = (TextView) rootView.findViewById(R.id.song20);
		singer5Songs = new TextView[5];
		singer5Songs[0] = (TextView) rootView.findViewById(R.id.song21);
		singer5Songs[1] = (TextView) rootView.findViewById(R.id.song22);
		singer5Songs[2] = (TextView) rootView.findViewById(R.id.song23);
		singer5Songs[3] = (TextView) rootView.findViewById(R.id.song24);
		singer5Songs[4] = (TextView) rootView.findViewById(R.id.song25);
		singer6Songs = new TextView[5];
		singer6Songs[0] = (TextView) rootView.findViewById(R.id.song26);
		singer6Songs[1] = (TextView) rootView.findViewById(R.id.song27);
		singer6Songs[2] = (TextView) rootView.findViewById(R.id.song28);
		singer6Songs[3] = (TextView) rootView.findViewById(R.id.song29);
		singer6Songs[4] = (TextView) rootView.findViewById(R.id.song30);
		singer7Songs = new TextView[5];
		singer7Songs[0] = (TextView) rootView.findViewById(R.id.song31);
		singer7Songs[1] = (TextView) rootView.findViewById(R.id.song32);
		singer7Songs[2] = (TextView) rootView.findViewById(R.id.song33);
		singer7Songs[3] = (TextView) rootView.findViewById(R.id.song34);
		singer7Songs[4] = (TextView) rootView.findViewById(R.id.song35);
		singer8Songs = new TextView[5];
		singer8Songs[0] = (TextView) rootView.findViewById(R.id.song36);
		singer8Songs[1] = (TextView) rootView.findViewById(R.id.song37);
		singer8Songs[2] = (TextView) rootView.findViewById(R.id.song38);
		singer8Songs[3] = (TextView) rootView.findViewById(R.id.song39);
		singer8Songs[4] = (TextView) rootView.findViewById(R.id.song40);
	}

}
