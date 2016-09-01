package com.api.connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Singer;
import com.model.Song;

@SuppressWarnings("serial")
public class MainRetrieval implements Serializable{
	private List<Song> newSongs;
	private List<Singer> singers;
	private List<Song> singersSongs;

	public MainRetrieval() {
		// TODO Auto-generated constructor stub
		newSongs = new ArrayList<Song>();
		singers = new ArrayList<Singer>();
		singersSongs = new ArrayList<Song>();
	}

	public boolean GetMainAPI(String response) {
		try {
			JSONObject mainObject = new JSONObject(response);
			JSONArray songsArray = mainObject.getJSONArray("newSongs");
			for (int i = 0 ; i < songsArray.length() ; i++){
				Song song = new Song();
				song.setId(Long.valueOf(songsArray.getJSONObject(i).getString("id")));
				song.setName(songsArray.getJSONObject(i).getString("name"));
				newSongs.add(song);
			}
			JSONArray popularSingers = mainObject.getJSONArray("popularSingers");
			for (int i = 0 ; i < popularSingers.length() ; i++){
				Singer singer = new Singer();
				JSONObject singerObject = popularSingers.getJSONObject(i);
				singer.setId(Long.valueOf(singerObject.getString("id")));
				singer.setName(singerObject.getString("name"));
				singers.add(singer);
				JSONArray singersSongsArray = singerObject.getJSONArray("songs");
				for (int j = 0 ; j < singersSongsArray.length() ; j++){
					Song song = new Song();
					song.setId(Long.valueOf(singersSongsArray.getJSONObject(j).getString("id")));
					song.setName(singersSongsArray.getJSONObject(j).getString("name"));
					singersSongs.add(song);
				}
			}
			return true;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public List<Song> getNewSongs() {
		return newSongs;
	}

	public void setNewSongs(List<Song> newSongs) {
		this.newSongs = newSongs;
	}

	public List<Singer> getSingers() {
		return singers;
	}

	public void setSingers(List<Singer> singers) {
		this.singers = singers;
	}

	public List<Song> getSingersSongs() {
		return singersSongs;
	}

	public void setSingersSongs(List<Song> singersSongs) {
		this.singersSongs = singersSongs;
	}
	
	
	
	
	
}
