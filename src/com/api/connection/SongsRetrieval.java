package com.api.connection;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import com.model.Song;

public class SongsRetrieval {
	private List<Song> songs;

	public SongsRetrieval() {
		songs = new ArrayList<Song>();
	}

	@SuppressLint("NewApi")
	public boolean GetMainAPI(String response) {
		try {
			JSONArray songsArray = new JSONArray(response);
			for (int i = 0; i < songsArray.length(); i++) {
				Song song = new Song();
				JSONObject songObject = songsArray.getJSONObject(i);
				song.setId(Long.valueOf(songObject.getString("id")));
				song.setName(songObject.getString("name"));
				String letter = songObject.getString("letter");
				if (!letter.isEmpty())
					song.setLetter(songObject.getString("letter").charAt(0));
				songs.add(song);
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

	public void setSongs(List<Song> newSongs) {
		this.songs = newSongs;
	}

}
