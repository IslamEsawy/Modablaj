package com.utils;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.TextView;

import com.modablaj.R;
import com.modablaj.SingerActivity;
import com.modablaj.SongActivity;
import com.model.Singer;
import com.model.Song;

@SuppressLint("ViewHolder")
public class SearchListAdapter extends ArrayAdapter<Object> {
	private List<Object> list;
	private int resource;
	private Activity activity;

	public SearchListAdapter(Activity activity, int resource, List<Object> m) {
		super(activity, resource);
		this.resource = resource;
		this.activity = activity;
		this.setList(m);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.indexOf(getItem(position));
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		convertView = mInflater.inflate(resource, parent, false);
		TextView name = (TextView) convertView.findViewById(R.id.nameTV);
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		Log.e("gogo", "" + getList().size());
		if (getList().get(position) instanceof Song) {
			Song song = (Song) getList().get(position);
			name.setText(song.getName());
			img.setImageResource(R.drawable.song_icon);
		}
		if (getList().get(position) instanceof Singer) {
			Singer singer = (Singer) getList().get(position);
			name.setText(singer.getName());
			img.setImageResource(R.drawable.singer_icon);
		}

		View ll = (LinearLayout) convertView.findViewById(R.id.nameLL);
		ll.setFocusable(true);
		ll.setSelected(true);
		ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (list.get(position) instanceof Singer) {
					Log.e("location2", "Singer");
					Singer singer = (Singer) list.get(position);
					Intent runProfile = new Intent(activity,
							SingerActivity.class);
					Bundle b = new Bundle();
					b.putString("singerId", String.valueOf(singer.getId()));
					runProfile.putExtras(b); // Put your id to your next Inte
					activity.startActivity(runProfile);
				} else if (list.get(position) instanceof Song) {
					Log.e("location2", "Song");
					Song song = (Song) list.get(position);
					Intent runProfile = new Intent(activity, SongActivity.class);
					Bundle b = new Bundle();
					b.putString("songId", String.valueOf(song.getId()));
					runProfile.putExtras(b); // Put your id to your next Inte
					activity.startActivity(runProfile);
				}
			}
		});
		return convertView;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

}
