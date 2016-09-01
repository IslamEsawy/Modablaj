package com.api.connection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.model.Line;
import com.model.Singer;
import com.model.Song;

public class SongRetrieval {
	private Song theSong;

	public Song getTheSong() {
		return theSong;
	}

	public void setTheSong(Song theSong) {
		this.theSong = theSong;
	}

	public SongRetrieval() {
		theSong = new Song();
	}

	public boolean GetMainAPI(String response) {
		try {
			JSONObject songObject = new JSONObject(response);
			theSong.setId(Long.valueOf(songObject.getString("id")));
			theSong.setName(songObject.getString("name"));
			theSong.setArabicName(songObject.getString("nameInArabic"));
			theSong.setLetter(songObject.getString("name").charAt(0));
			Singer singer1 = new Singer();
			singer1.setId(Long.valueOf(songObject.getString("singerId")));
			singer1.setName(songObject.getString("singerName"));
			singer1.setArabicName(songObject.getString("singerNameInArabic"));
			singer1.setLetter(songObject.getString("singerName").charAt(0));

			List<Line> lyr = new ArrayList<Line>();
			JSONObject lyricsObject = songObject.getJSONObject("lyrics");
			JSONArray linesArray = lyricsObject.getJSONArray("line");
			
			JSONObject lyricsObject2 = songObject.getJSONObject("lyricsTranslated");
			JSONArray linesArray2 = lyricsObject2.getJSONArray("line");
			int size1 = linesArray.length();
			int size2 = linesArray2.length();
			
			
			for (int i = 0; i < Math.min(size1, size2); i++) {
				Line line = new Line();
				line.setSongId(theSong.getId());
				line.setLyrics(linesArray.getString(i));
				line.setTranslatedLyrics(linesArray2.getString(i));
				lyr.add(line);
			}
		
			if (size1 > size2){
				for (int i = size2; i < size1 ; i++) {
					Line line = new Line();
					line.setSongId(theSong.getId());
					line.setLyrics(linesArray.getString(i));
					line.setTranslatedLyrics("");
					lyr.add(line);
				}
			}
			if (size1 < size2){
				for (int i = size1; i < size2 ; i++) {
					Line line = new Line();
					line.setSongId(theSong.getId());
					line.setLyrics("");
					line.setTranslatedLyrics(linesArray2.getString(i));
					lyr.add(line);
				}
			}

			List<Song> singer1OtherSongs = new ArrayList<Song>();
			JSONObject singerOtherSongsObject;
			JSONArray singerOtherSongsArray; 
			if (songObject.has("singerOtherSongs")){
				singerOtherSongsObject = songObject
						.getJSONObject("singerOtherSongs");
				singerOtherSongsArray = singerOtherSongsObject
						.getJSONArray("songs");
				for (int i = 0; i < singerOtherSongsArray.length(); i++) {
					Song song = new Song();
					song.setId(Long.valueOf(singerOtherSongsArray.getJSONObject(i)
							.getString("id")));
					song.setName(singerOtherSongsArray.getJSONObject(i).getString(
							"name"));
					singer1OtherSongs.add(song);
				}
			}
			

			Singer singer2 = new Singer();
			List<Song> singer2OtherSongs = new ArrayList<Song>();
			if (songObject.has("singerId2")) {

				singer2.setId(Long.valueOf(songObject.getString("singerId2")));
				singer2.setName(songObject.getString("singerName2"));
				singer2.setArabicName(songObject
						.getString("singerNameInArabic2"));
				singer2.setLetter(songObject.getString("singerName2").charAt(0));

				
				if (songObject.has("singerOtherSongs2")){
					singerOtherSongsObject = songObject
							.getJSONObject("singerOtherSongs2");
					singerOtherSongsArray = singerOtherSongsObject
							.getJSONArray("songs");
					for (int i = 0; i < singerOtherSongsArray.length(); i++) {
						Song song = new Song();
						song.setId(Long.valueOf(singerOtherSongsArray
								.getJSONObject(i).getString("id")));
						song.setName(singerOtherSongsArray.getJSONObject(i)
								.getString("name"));
						singer2OtherSongs.add(song);
					}
				}
				
			}

			List<Song> otherSongs = new ArrayList<Song>();
			JSONArray otherS = songObject.getJSONArray("otherSongs");
			for (int i = 0; i < otherS.length(); i++) {
				JSONObject object = otherS.getJSONObject(i);
				Song song = new Song();
				song.setId(Long.valueOf(object.getString("id")));
				song.setName(object.getString("name"));
				otherSongs.add(song);
			}

			theSong.setLyrics(lyr);
			theSong.setSinger1(singer1);
			theSong.setSinger2(singer2);
			theSong.setOtherSongs(otherSongs);
			theSong.setSinger1OtherSongs(singer1OtherSongs);
			theSong.setSinger2OtherSongs(singer2OtherSongs);
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
