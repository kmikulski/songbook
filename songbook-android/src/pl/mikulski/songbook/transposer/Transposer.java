package pl.mikulski.songbook.transposer;

import java.util.Scanner;


public class Transposer {

	private static final String SEPARATOR = System.getProperty("line.separator");
	
	public static String transpose(String chords, int interval) {
		String[] lines = chords.split("\n");
		StringBuilder result = new StringBuilder();
		for (String line : lines) {
			Scanner s = new Scanner(line);
			while (s.hasNext()) {
				String token = s.next();
				Chord c = Chord.findChord(token);
				if (c != null) {
					c.transpose(interval);
					result.append(c);
				} else {
					result.append(token);
				}
				if (s.hasNext()) {
					result.append(' ');
				}
			}
			result.append(SEPARATOR);
			s.close();
		}
		return result.toString();
	}
	
	public static Chord getInitialChord(String chords) {
		Scanner s = new Scanner(chords);
		Chord c = null;
		if(s.hasNext(Chord.PATTERN)) {
			c = Chord.findChord(s.next(Chord.PATTERN));
		}
		s.close();
		return c;
	}
	
}
