package com.model;

public class Line {
	private long songId;
	private String lyrics;
	private String translatedLyrics;
	
	public Line() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Line(long songId, String lyrics, String translatedLyrics) {
		super();
		this.songId = songId;
		this.lyrics = lyrics;
		this.translatedLyrics = translatedLyrics;
	}



	public long getSongId() {
		return songId;
	}
	public void setSongId(long songId) {
		this.songId = songId;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public String getTranslatedLyrics() {
		return translatedLyrics;
	}
	public void setTranslatedLyrics(String translatedLyrics) {
		this.translatedLyrics = translatedLyrics;
	}
	
	
}
