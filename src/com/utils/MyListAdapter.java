package com.utils;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.api.connection.Connection;
import com.modablaj.R;
import com.modablaj.SingerActivity;
import com.modablaj.SongActivity;
import com.model.Singer;
import com.model.Song;

@SuppressLint("ResourceAsColor")
public class MyListAdapter extends ArrayAdapter<Object> implements
		SectionIndexer {
	List<Object> items;
	private String mSections = "";
	private int resource;
	private Activity activity;
	private int type = 0;

	public MyListAdapter(Activity activity, int resource, List<Object> m,
			String mSections) {
		super(activity, resource, m);
		this.resource = resource;
		this.activity = activity;
		this.mSections = mSections;
		this.items = m;
		
		if (m!=null && !m.isEmpty() && m.get(0) instanceof Singer) {
			type = 1;
		}
		if (m!=null && !m.isEmpty() && m.get(0) instanceof Song) {
			type = 2;
		}
		Log.e("mSection", mSections);
	}

	@SuppressLint({ "ResourceAsColor", "InflateParams" })
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(resource, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.song);
			holder.name = (TextView) convertView.findViewById(R.id.nameTV);
			holder.headingLL = (LinearLayout) convertView
					.findViewById(R.id.headingLL);
			holder.headingTV = (TextView) convertView
					.findViewById(R.id.headingTV);
			holder.nameLL = (LinearLayout) convertView
					.findViewById(R.id.nameLL);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (type == 1) {
			handleSingerView(position, holder);
		} else if (type == 2) {
			handleSongView(position, holder);
		}

		return convertView;
	}

	private void handleSingerView(int position, ViewHolder holder) {
		if (position < items.size()) {

			final Singer singer = (Singer) items.get(position);
			final String singerName = singer.getName();
			if (singerName != null && (singerName.length() == 1)) {
				holder.nameLL.setVisibility(View.GONE);
				holder.headingLL.setVisibility(View.VISIBLE);
				holder.headingTV.setText(singerName);
				holder.headingLL
						.setBackgroundColor(android.R.color.transparent);
			} else {
				holder.nameLL.setVisibility(View.VISIBLE);
				holder.headingLL.setVisibility(View.GONE);
				holder.name.setText(singerName);
				holder.img.setImageResource(R.drawable.singer_icon);
				View ll = (LinearLayout) holder.name.getParent();
				ll.setFocusable(true);
				ll.setSelected(true);
				ll.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (Connection.isConnectedToInternet(activity
								.getApplicationContext())) {
							Intent runProfile = new Intent(v.getContext(),
									SingerActivity.class);
							Bundle b = new Bundle();
							b.putString("singerId",
									String.valueOf(singer.getId()));
							runProfile.putExtras(b);
							v.getContext().startActivity(runProfile);
						} else {
							new AlertDialog.Builder(activity)
									.setTitle("Oops")
									.setMessage("No Internet Connection!")
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
												}
											}).show();
						}
					}
				});
			}
		}
	}

	private void handleSongView(int position, ViewHolder holder) {
		if (position < items.size()) {

			final Song song = (Song) items.get(position);
			final String songName = song.getName();
			if (songName != null && (songName.length() == 1)) {
				holder.nameLL.setVisibility(View.GONE);
				holder.headingLL.setVisibility(View.VISIBLE);
				holder.headingTV.setText(songName);
				holder.headingLL
						.setBackgroundColor(android.R.color.transparent);
			} else {
				holder.nameLL.setVisibility(View.VISIBLE);
				holder.headingLL.setVisibility(View.GONE);
				holder.name.setText(songName);
				holder.img.setImageResource(R.drawable.song_icon);

				View ll = (LinearLayout) holder.name.getParent();
				ll.setFocusable(true);
				ll.setSelected(true);
				ll.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (Connection.isConnectedToInternet(activity
								.getApplicationContext())) {
							Intent runProfile = new Intent(v.getContext(),
									SongActivity.class);
							Bundle b = new Bundle();
							b.putString("songId", String.valueOf(song.getId()));
							runProfile.putExtras(b);
							v.getContext().startActivity(runProfile);
						} else {
							new AlertDialog.Builder(activity)
									.setTitle("Oops")
									.setMessage("No Internet Connection!")
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
												}
											}).show();
						}
					}
				});
			}
		}
	}

	private class ViewHolder {
		ImageView img;
		TextView name, headingTV;
		LinearLayout nameLL, headingLL;
	}

	@Override
	public int getPositionForSection(int section) {
		// If there is no item for current section, previous section will be
		// selected
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (type == 1) {
					Singer singer = (Singer) getItem(j);
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if ((String.valueOf(singer.getName().charAt(0))
									.equals(String.valueOf(k))))
								return j;
						}
					} else {
						if (String.valueOf(singer.getName().charAt(0)).equals(
								String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
				if (type == 2) {
					Song song = (Song) getItem(j);
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if ((String.valueOf(song.getName().charAt(0))
									.equals(String.valueOf(k))))
								return j;
						}
					} else {
						if (String.valueOf(song.getName().charAt(0)).equals(
								String.valueOf(mSections.charAt(i))))
							return j;
					}
				}

			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}
}
