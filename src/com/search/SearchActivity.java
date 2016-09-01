package com.search;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.api.connection.Connection;
import com.api.connection.SearchRetrieval;
import com.modablaj.R;
import com.utils.SearchListAdapter;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class SearchActivity extends Activity implements
		SearchView.OnQueryTextListener {

	private ListView lv;
	private SearchView search_view;
	private List<Object> list;
	private Search search;
	private SearchListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the status bar.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_search);

		lv = (ListView) findViewById(R.id.list_view);
		search_view = (SearchView) findViewById(R.id.search_view);

		search_view.setOnQueryTextListener(this);

	}

	@Override
	public boolean onQueryTextChange(String newText) {
		if (search != null)
			search.cancel(true);
		search = new Search();
		search.execute(newText.replaceAll(" ", "%20"));

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	class Search extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			String response = Connection
					.getJSONfromURL("http://modablaj.com/app/android/search?q="
							+ args[0]);
			SearchRetrieval searchRetrieval = new SearchRetrieval();
			if (searchRetrieval.GetMainAPI(response)) {
				Log.e("resuuu", response);
				List<Object> songsList = new ArrayList<Object>(
						searchRetrieval.getSongs());
				List<Object> singersList = new ArrayList<Object>(
						searchRetrieval.getSingers());
				list = songsList;
				list.addAll(singersList);
				return "go";
			}
			return null;
		}

		protected void onPostExecute(String action) {
			if (action != null) {
				Log.e("gogo1", "" + list.size());
				adapter = new SearchListAdapter(SearchActivity.this,
						R.layout.search_item, list);
				lv.setAdapter(adapter);
			}
		}
	}

}
