package com.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.api.connection.Connection;
import com.api.connection.SearchRetrieval;
import com.modablaj.R;
import com.model.Singer;
import com.model.Song;

public class SearchCustomAdapter extends BaseAdapter implements Filterable {
	private Context context;
	private List<Object> list;
	private ValueFilter valueFilter;
	private List<Object> mStringFilterList;

	public SearchCustomAdapter(Context context, List<Object> list) {
		this.context = context;
		this.list = list;
		mStringFilterList = list;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		convertView = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.search_item, parent, false);
			TextView name = (TextView) convertView.findViewById(R.id.nameTV);
			Log.e("gogo", "" + list.size());
			if (list.get(position) instanceof Song) {
				Song song = (Song) list.get(position);
				name.setText(song.getName());
			}
			if (list.get(position) instanceof Singer) {
				Singer singer = (Singer) list.get(position);
				name.setText(singer.getName());
			}
		}
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (valueFilter == null)
			valueFilter = new ValueFilter();
		return valueFilter;
	}

	private class ValueFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				String constraint1 = constraint.toString();
				String response = Connection
						.getJSONfromURL("http://modablaj.com/app/android/search?q=" + constraint1.replaceAll(" ", "%20"));
				SearchRetrieval searchRetrieval = new SearchRetrieval();
				searchRetrieval.GetMainAPI(response);
				Log.e("resuuu", response);
				List<Object> songsList = new ArrayList<Object>(
						searchRetrieval.getSongs());
				List<Object> singersList = new ArrayList<Object>(
						searchRetrieval.getSingers());
				list = songsList;
				list.addAll(singersList);
				results.count = list.size();
				results.values = list;
			} else {
				results.count = mStringFilterList.size();
				results.values = mStringFilterList;
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			list = (ArrayList<Object>) results.values;
			notifyDataSetChanged();
		}

	}

}
