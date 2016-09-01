package com.model;

import java.util.List;

public class Singer extends Object implements Comparable<Singer> {
	private long id;
	private String name;
	private String arabicName;
	private char letter;
	private List<Song> singersSongs;
	private List<Song> suggestion;

	public List<Song> getSingersSongs() {
		return singersSongs;
	}

	public void setSingersSongs(List<Song> singersSongs) {
		this.singersSongs = singersSongs;
	}

	public List<Song> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Song> suggestion) {
		this.suggestion = suggestion;
	}

	public Singer(long id, String name, String arabicName, char letter) {
		super();
		this.id = id;
		this.name = name;
		this.arabicName = arabicName;
		this.letter = letter;
	}

	public Singer() {
	}

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
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

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	@Override
	public int compareTo(Singer another) {
		return this.getName().compareTo(another.getName());
	}

}
