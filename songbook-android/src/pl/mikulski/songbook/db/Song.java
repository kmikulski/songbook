package pl.mikulski.songbook.db;

import java.io.Serializable;

/**
 * DTO bean class representing a song
 * 
 * @author KM
 */
public class Song implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_CHORDS = "chords";
	// to identify data in intents
	public static final String DATA_NAME = Song.class.getName();

	private String title;
	private String content;
	private String chords;

	public Song(String title, String content, String chords) {
		this.title = title;
		this.content = content;
		this.chords = chords;
	}

	public Song() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChords() {
		return chords;
	}

	public void setChords(String chords) {
		this.chords = chords;
	}

}
