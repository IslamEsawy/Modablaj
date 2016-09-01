package com.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.api.connection.Connection;
import com.api.connection.MainRetrieval;
import com.modablaj.R;
import com.model.Song;
import com.services.DownloadResultReceiver;
import com.services.DownloadService;
import com.utils.MyListAdapter;
import com.widget.listview.IndexableListView;

public class NewSongsTabFragment extends Fragment implements
		DownloadResultReceiver.Receiver {
	private View rootView;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public final static String TAG = NewSongsTabFragment.class.getSimpleName();
	private IndexableListView songs;
	private DownloadResultReceiver mReceiver;
	private ProgressBar load;
	
	public NewSongsTabFragment() {
		// TODO Auto-generated constructor stub
	}

	public static NewSongsTabFragment newInstance() {
		return new NewSongsTabFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.new_songs_tab_fragment, container,
				false);
		load = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		load.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
		load.setVisibility(View.VISIBLE);
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
			List<Song> newSongs = songsRetrieval.getNewSongs();
			String mSections = handleSectionSong(newSongs);

			char[] ar = mSections.toCharArray();
			Arrays.sort(ar);
			mSections = String.valueOf(ar);

			for (int i = 0; i < mSections.length(); i++) {
				Song virtual = new Song();
				virtual.setName(String.valueOf(mSections.charAt(i)));
				newSongs.add(virtual);
			}

			Collections.sort(newSongs);
			List<Object> objectList = new ArrayList<Object>(newSongs);
			ArrayAdapter<Object> adapter = new MyListAdapter(getActivity(),
					R.layout.listrow_user, objectList, mSections);

			load.setVisibility(View.GONE);
			songs = (IndexableListView) rootView.findViewById(R.id.listview);
			songs.setAdapter(adapter);
			songs.setFastScrollEnabled(true);
		}
	}

	private String handleSectionSong(List<Song> items) {
		String mSections = "";
		int g = 0;
		for (int i = 0; i < items.size(); i++) {
			Song song = (Song) items.get(i);
			char currentLetter = song.getName().charAt(0);
			if ((currentLetter >= 'a' && currentLetter <= 'z')
					|| (currentLetter >= 'A' && currentLetter <= 'Z')) {
				if (!mSections.contains("" + currentLetter)) {
					mSections += currentLetter;
				}
			} else if (g == 0) {
				mSections += "#";
				g = 1;
			}
		}
		return mSections;
	}

}
