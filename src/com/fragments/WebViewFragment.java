package com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.modablaj.R;

public class WebViewFragment extends Fragment {

	public final static String TAG = WebViewFragment.class.getSimpleName();
	private String url;

	public WebViewFragment(String url) {
		this.url = url;
	}

	public static WebViewFragment newInstance(String url) {
		return new WebViewFragment(url);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_webview, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		WebView webView = (WebView) view.findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(url);
	}
}
