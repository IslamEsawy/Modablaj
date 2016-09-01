package com.model;

import java.util.List;

public class Song extends Object implements Comparable<Song>{
	private long id;
	private String name, arabicName;
	private char letter; 
	private List<Line> lyrics;
	private List<Song> otherSongs;
	private List<Song> singer1OtherSongs;
	private List<Song> singer2OtherSongs;
	private Singer singer1;
	private Singer singer2;
	
	
	public Song(long id, String name, String arabicName, String lyrics,
			String arabicLyrics) {
		super();
		this.id = id;
		this.name = name;
		this.arabicName = arabicName;
	}
	public Song() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArabicName() {
		return arabicName;
	}
	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public List<Line> getLyrics() {
		return lyrics;
	}
	public void setLyrics(List<Line> lyrics) {
		this.lyrics = lyrics;
	}
	public List<Song> getOtherSongs() {
		return otherSongs;
	}
	public void setOtherSongs(List<Song> otherSongs) {
		this.otherSongs = otherSongs;
	}
	public List<Song> getSinger1OtherSongs() {
		return singer1OtherSongs;
	}
	public void setSinger1OtherSongs(List<Song> singer1OtherSongs) {
		this.singer1OtherSongs = singer1OtherSongs;
	}
	public List<Song> getSinger2OtherSongs() {
		return singer2OtherSongs;
	}
	public void setSinger2OtherSongs(List<Song> singer2OtherSongs) {
		this.singer2OtherSongs = singer2OtherSongs;
	}
	public Singer getSinger1() {
		return singer1;
	}
	public void setSinger1(Singer singer1) {
		this.singer1 = singer1;
	}
	public Singer getSinger2() {
		return singer2;
	}
	public void setSinger2(Singer singer2) {
		this.singer2 = singer2;
	}
	@Override
	public int compareTo(Song another) {
		return this.getName().compareTo(another.getName());
	}
	
	
	
	
}
