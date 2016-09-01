package com.modablaj;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.api.connection.Connection;
import com.fragments.WebViewFragment;
import com.search.SearchActivity;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private static String songRequestURL = "http://www.modablaj.com/request";
	private static String translateSongURL = "http://www.modablaj.com/add";
	private static String facebookURL = "https://www.facebook.com/Modablaj";
	private static String twitterURL = "http://twitter.com/modablaj";
	private static String contactUsURL = "http://www.modablaj.com/contact";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		getActionBar().setIcon(R.drawable.logo);
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add items to the ListView
		// mDrawerList.setAdapter(new
		// ArrayAdapter<String>(this,R.layout.drawer_list_item, mDrawerItmes));

		NavDrawerItem[] array = new NavDrawerItem[6];
		array[0] = new NavDrawerItem(R.drawable.home_icon, "«·—∆Ì”Ì…");
		array[1] = new NavDrawerItem(R.drawable.music_icon, "ÿ·» √€‰Ì…");
		array[2] = new NavDrawerItem(R.drawable.tr_icon,
				" —Ã„ √€‰Ì…");
		array[3] = new NavDrawerItem(R.drawable.facebook_icon,
				"’›Õ ‰« ⁄·Ï ›Ì”»Êﬂ");
		array[4] = new NavDrawerItem(R.drawable.twitter_icon,
				"’›Õ ‰« ⁄·Ï  ÊÌ —");
		array[5] = new NavDrawerItem(R.drawable.mail_icon, "√ ’· »‰«");
		// Set the OnItemClickListener so something happens when a
		// user clicks on an item.

		mDrawerList.setAdapter(new NavDrawerAdapter(this,
				R.layout.drawer_list_item2, array));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (!Connection.isConnectedToInternet(getApplicationContext())) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Oops")
					.setMessage("No Internet Connection!")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							}).show();

		}

		if (savedInstanceState == null) {
			navigateTo(0);
		}

	}

	/*
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.v(TAG, "ponies");
			navigateTo(position);
		}

	}

	private void navigateTo(int position) {
		Log.v(TAG, "List View Item: " + position);

		switch (position) {
		case 0:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, TabbedActivity.newInstance(),
							TabbedActivity.TAG).commit();
			break;
		case 1:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame,
							WebViewFragment.newInstance(songRequestURL),
							WebViewFragment.TAG).commit();
			break;
		case 2:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame,
							WebViewFragment.newInstance(translateSongURL),
							WebViewFragment.TAG).commit();
			break;
		case 3:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame,
							WebViewFragment.newInstance(facebookURL),
							WebViewFragment.TAG).commit();
			break;
		case 4:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame,
							WebViewFragment.newInstance(twitterURL),
							WebViewFragment.TAG).commit();
			break;
		case 5:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame,
							WebViewFragment.newInstance(contactUsURL),
							WebViewFragment.TAG).commit();
			break;

		}
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_search:
			Intent run = new Intent(MainActivity.this, SearchActivity.class);
			startActivity(run);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class NavDrawerAdapter extends ArrayAdapter<NavDrawerItem> {
		private final Context context;
		private final int layoutResourceId;
		private NavDrawerItem data[] = null;

		public NavDrawerAdapter(Context context, int layoutResourceId,
				NavDrawerItem[] data) {
			super(context, layoutResourceId, data);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.data = data;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(layoutResourceId, parent, false);

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.navDrawerImageView);
			TextView textView = (TextView) convertView
					.findViewById(R.id.navDrawerTextView);

			NavDrawerItem choice = data[position];

			imageView.setImageResource(choice.icon);
			textView.setText(choice.name);

			return convertView;
		}
	}

	public class NavDrawerItem {
		public int icon;
		public String name;

		public NavDrawerItem(int icon, String name) {
			this.icon = icon;
			this.name = name;
		}
	}
}
