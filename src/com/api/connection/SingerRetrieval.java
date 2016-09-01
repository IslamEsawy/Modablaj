package com.api.connection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Singer;
import com.model.Song;

public class SingerRetrieval {
	private Singer singer;

	public SingerRetrieval() {
		singer = new Singer();
	}

	public boolean GetMainAPI(String response) {
		try {
			JSONArray singerArray = new JSONArray(response);
			JSONObject singerObject = singerArray.getJSONObject(0);
			singer.setId(Long.valueOf(singerObject.getString("id")));
			singer.setName(singerObject.getString("name"));
			singer.setArabicName(singerObject.getString("nameInArabic"));

			List<Song> singersSongs = new ArrayList<Song>();
			List<Song> suggestion = new ArrayList<Song>();

			JSONArray singersSongsArray = new JSONArray();
			if (!singerObject.isNull("songs"))
				singersSongsArray = singerObject.getJSONArray("songs");
			for (int i = 0; i < singersSongsArray.length(); i++) {
				JSONObject songObject = singersSongsArray.getJSONObject(i);
				Song song = new Song();
				song.setId(Long.valueOf(songObject.getString("id")));
				song.setName(songObject.getString("name"));
				singersSongs.add(song);
			}

			JSONArray singersSongsSuggestionsArray = new JSONArray();
			if (!singerObject.isNull("suggestion"))
				singersSongsSuggestionsArray = singerObject
						.getJSONArray("suggestion");
			for (int i = 0; i < singersSongsSuggestionsArray.length(); i++) {
				JSONObject songObject = singersSongsSuggestionsArray
						.getJSONObject(i);
				Song song = new Song();
				song.setId(Long.valueOf(songObject.getString("id")));
				song.setName(songObject.getString("name"));
				suggestion.add(song);
			}

			singer.setSingersSongs(singersSongs);
			singer.setSuggestion(suggestion);
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}

}
