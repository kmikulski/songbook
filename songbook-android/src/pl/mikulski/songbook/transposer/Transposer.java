package pl.mikulski.songbook.transposer;

import java.util.Scanner;

/**
 * Utility class containing methods for chord transposition
 * 
 * @author KM
 */
public class Transposer {

	private static final String SEPARATOR = System.getProperty("line.separator");

	/**
	 * Parse the argument string for chords, transpose them and return the
	 * reconstructed string containing the transposition
	 * 
	 * @param chords
	 * @param interval
	 * @return
	 */
	public static String transpose(String chords, int interval) {
		StringBuilder result = new StringBuilder();
		// split input into separate lines to allow proper reconstruction later
		String[] lines = chords.split("\n");
		for (String line : lines) {
			Scanner s = new Scanner(line);
			while (s.hasNext()) {
				String token = s.next();
				// try to extract chord from token
				Chord c = Chord.findChord(token);
				if (c != null) {
					// chord found
					c.transpose(interval);
					result.append(c);
				} else {
					// no chord found, rewrite token verbatim
					result.append(token);
				}
				if (s.hasNext()) {
					result.append(' ');
				}
			}
			result.append(SEPARATOR);
			// scanners must be closed to avoid resource leak!
			s.close();
		}
		return result.toString();
	}

	/**
	 * Retrieve the initial chord from a chord string
	 * 
	 * @param chords
	 * @return initial chord or null if not found
	 */
	public static Chord getInitialChord(String chords) {
		Scanner s = new Scanner(chords);
		Chord c = null;
		if (s.hasNext(Chord.PATTERN)) {
			c = Chord.findChord(s.next(Chord.PATTERN));
		}
		s.close();
		return c;
	}

}
