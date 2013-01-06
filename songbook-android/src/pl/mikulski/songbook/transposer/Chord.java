package pl.mikulski.songbook.transposer;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a single chord (major or minor), with possible pre- and
 * postfixes
 * 
 * @author KM
 */
public class Chord {

	// accept chords in common Polish notation
	public static final Pattern PATTERN = Pattern
			.compile("(\\W*)([CDEFGABHcdefgabh](is)?)([\\W\\d]*)");

	private static final Note[] NOTES = Note.values();

	private Note note;
	private boolean isMajor;
	private String modifierLeft;
	private String modifierRight;

	/**
	 * Factory method for extraction of a single chord from a given token
	 * 
	 * @param token
	 *            String to extract chord from
	 * @return a chord represented by the token or null if unable to parse
	 */
	public static Chord findChord(String token) {
		Matcher m = PATTERN.matcher(token);
		if (!m.find() || !m.group().equals(token)) {
			return null;
		}
		String base = m.group(2);
		// only ascii case change, so it's safe to use default locale
		Note note = Note.valueOf(base.toLowerCase(Locale.getDefault()));
		boolean isMajor = Character.isUpperCase(base.charAt(0));
		String modifierLeft = m.group(1);
		String modifierRight = m.group(4);
		return new Chord(note, isMajor, modifierLeft, modifierRight);
	}

	/**
	 * Private constructor to enforce the use of factory method
	 * 
	 * @param note
	 * @param isMajor
	 * @param modifierLeft
	 * @param modifierRight
	 */
	private Chord(Note note, boolean isMajor, String modifierLeft, String modifierRight) {
		this.note = note;
		this.isMajor = isMajor;
		this.modifierLeft = modifierLeft;
		this.modifierRight = modifierRight;
	}

	/**
	 * Transpose this chord by altering the base note
	 * 
	 * @param interval
	 *            number of semitones by which to transpose
	 */
	public void transpose(int interval) {
		while (interval < 0) {
			// avoid negative indices in the next step
			interval += NOTES.length;
		}
		note = NOTES[(note.ordinal() + interval) % NOTES.length];
	}

	public Note getNote() {
		return note;
	}

	public boolean isMajor() {
		return isMajor;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(note);
		if (isMajor) {
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
