package pl.mikulski.songbook;

import pl.mikulski.songbook.db.Song;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class SongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		int fontSize = Integer.parseInt(prefs
				.getString(SettingsActivity.PREFERENCES_FONT_SIZE, "0"));
		int displayMode = Integer.parseInt(prefs.getString(
				SettingsActivity.PREFERENCES_DISPLAY_MODE, "0"));

		Intent intent = this.getIntent();
		Song song = (Song) intent.getSerializableExtra(Song.DATA_NAME);

		switch (displayMode) {
		case 0:
			setContentView(R.layout.song_text_only);
			break;
		case 1:
			setContentView(R.layout.song_chords_compressed);
			break;
		case 2:
			setContentView(R.layout.song_chords_scrollable);
			break;
		default:
			setContentView(R.layout.song_text_only);
			break;
		}

		TextView title = (TextView) this.findViewById(R.id.song_title);
		TextView content = (TextView) this.findViewById(R.id.song_content);
		TextView chords = null;

		title.setText(song.getTitle());
		content.setText(song.getContent());

		if (displayMode > 0) {
			chords = (TextView) this.findViewById(R.id.song_chords);
			chords.setText(song.getChords());
		}

		if (fontSize > 0) {
			content.setTextSize(content.getTextSize() * 1.2f);
			if (chords != null) {
				chords.setTextSize(chords.getTextSize() * 1.2f);
			}
		} else if (fontSize < 0) {
			content.setTextSize(content.getTextSize() * 0.8f);
			if (chords != null) {
				chords.setTextSize(chords.getTextSize() * 0.8f);
			}
		}

	}

}
