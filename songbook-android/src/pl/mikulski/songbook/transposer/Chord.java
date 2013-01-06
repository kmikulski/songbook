package pl.mikulski.songbook.transposer;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chord {
	
	public static final Pattern PATTERN = Pattern.compile("(\\W*)([CDEFGABHcdefgabh](is)?)([\\W\\d]*)");

	private static final Note[] NOTES = Note.values();
	
	private Note note;
	private boolean isMajor;
	private String modifierLeft;
	private String modifierRight;
	
	public static Chord findChord(String token) {
		Matcher m = PATTERN.matcher(token);
		if(!m.find() || !m.group().equals(token)) {
			return null;
		}
		String base = m.group(2);
		Note note = Note.valueOf(base.toLowerCase(Locale.getDefault()));
		boolean isMajor = Character.isUpperCase(base.charAt(0));
		String modifierLeft = m.group(1);
		String modifierRight = m.group(4);
		return new Chord(note, isMajor, modifierLeft, modifierRight);
	}
	
	private Chord(Note note, boolean isMajor, String modifierLeft, String modifierRight) {
		this.note = note;
		this.isMajor = isMajor;
		this.modifierLeft = modifierLeft;
		this.modifierRight = modifierRight;
	}
	
	public void transpose(int interval) {
		note = NOTES[(note.ordinal() + interval + NOTES.length) % NOTES.length];
	}
	
	public Note getNote() {
		return note;
	}

	public boolean isMajor() {
		return isMajor;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(note);
		if(isMajor) {
			buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
		}
		buffer.insert(0, modifierLeft);
		buffer.append(modifierRight);
		return buffer.toString();
	}

	public enum Note {
		c, cis, d, dis, e, f, fis, g, gis, a, b, h;
	}
	
	public enum NoteMajor {
		C, Cis, D, Dis, E, F, Fis, G, Gis, A, B, H;
	}
	
}
