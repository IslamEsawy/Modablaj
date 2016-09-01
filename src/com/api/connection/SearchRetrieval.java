package com.api.connection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Singer;
import com.model.Song;

public class SearchRetrieval {
	private List<Song> songs;
	private List<Singer> singers;

	public SearchRetrieval() {
		songs = new ArrayList<Song>();
		singers = new ArrayList<Singer>();
	}

	public boolean GetMainAPI(String response) {
		try {
			JSONObject resultObject = new JSONObject(response);
			JSONArray results = resultObject.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject object = results.getJSONObject(i);

				String type = object.getString("type");
				if (type.equals("'song'")) {
					Song song = new Song();
					song.setId(Long.valueOf(object.getString("id")));
					song.setName(object.getString("result"));
					song.setLetter(object.getString("result").charAt(0));
					songs.add(song);
				}else if (type.equals("'singer'")) {
					Singer singer = new Singer();
					singer.setId(Long.valueOf(object.getString("id")));
					singer.setName(object.getString("result"));
					singer.setLetter(object.getString("result").charAt(0));
					singers.add(singer);
				}
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public List<Singer> getSingers() {
		return singers;
	}

	public void setSingers(List<Singer> singers) {
		this.singers = singers;
	}


}
