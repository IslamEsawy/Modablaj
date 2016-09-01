package com.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragments.MainTabFragment;
import com.fragments.NewSongsTabFragment;
import com.fragments.SingersTabFragment;
import com.fragments.SongsTabFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		Fragment fragment;
		Bundle args;
		switch (position) {
		case 0:
			fragment = new NewSongsTabFragment();
			args = new Bundle();
			args.putInt(NewSongsTabFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		case 1:
			fragment = new MainTabFragment();
			args = new Bundle();
			args.putInt(MainTabFragment.ARG_SECTION_NUMBER, position + 2);
			fragment.setArguments(args);
			return fragment;
		case 2:
			fragment = new SingersTabFragment();
			args = new Bundle();
			args.putInt(SingersTabFragment.ARG_SECTION_NUMBER, position + 3);
			fragment.setArguments(args);
			return fragment;
		case 3:
			fragment = new SongsTabFragment();
			args = new Bundle();
			args.putInt(SongsTabFragment.ARG_SECTION_NUMBER, position + 4);
			fragment.setArguments(args);
			return fragment;
		}

		return null;

	}

	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "√€«‰Ì ÃœÌœ…";
		case 1:
			return "√‘Â— «·„€‰ÌÌ‰";
		case 2:
			return "ﬂ· «·„€‰ÌÌ‰";
		case 3:
			return "ﬂ· «·√€«‰Ì";
		}
		return null;
	}
}