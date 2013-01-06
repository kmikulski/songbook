package pl.mikulski.songbook;

import pl.mikulski.songbook.db.Song;
import pl.mikulski.songbook.transposer.Transposer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SongActivity extends Activity {

	private Song song;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		int fontSize = Integer.parseInt(prefs
				.getString(SettingsActivity.PREFERENCES_FONT_SIZE, "0"));
		int displayMode = Integer.parseInt(prefs.getString(
				SettingsActivity.PREFERENCES_DISPLAY_MODE, "0"));

		Intent intent = this.getIntent();
		song = (Song) intent.getSerializableExtra(Song.DATA_NAME);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.song, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.menu_transpose:
			new TransposeDialog(this, Transposer.getInitialChord(song.getChords())).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void transpose(int interval) {
		if (song != null) {
			String transposed = Transposer.transpose(song.getChords(), interval);
			song.setChords(transposed);
			TextView view = (TextView) this.findViewById(R.id.song_chords);
			if (view != null) {
				view.setText(transposed);
			}
		}
	}

}
