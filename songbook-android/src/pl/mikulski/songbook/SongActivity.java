package pl.mikulski.songbook;

import pl.mikulski.songbook.db.Song;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_compressed);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean showChords = prefs.getBoolean(Constants.PREFERENCES_SHOW_CHORDS, false);
		int fontSize = Integer.parseInt(prefs.getString(Constants.PREFERENCES_FONT_SIZE, "0"));

		Intent intent = this.getIntent();
		Song song = (Song) intent.getSerializableExtra(Song.DATA_NAME);

		TextView content = (TextView) this.findViewById(R.id.song_content);

		((TextView) this.findViewById(R.id.song_title)).setText(song.getTitle());
		content.setText(song.getContent());

		TextView chords = null;
		
		if (showChords) {
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
					1.0f);
			chords = new TextView(this);
			chords.setText(song.getChords());
			chords.setPadding(10, 0, 0, 0);
			content.setHorizontallyScrolling(true);
			content.setEllipsize(TruncateAt.END);
			content.setLayoutParams(layoutParams);
			((ViewGroup) this.findViewById(R.id.content_wrapper)).addView(chords);
		}
		
		if(fontSize > 0) {
			content.setTextSize(content.getTextSize() * 1.2f);
			if(chords != null) {
				chords.setTextSize(content.getTextSize());
			}
		} else if(fontSize < 0) {
			content.setTextSize(content.getTextSize() * 0.8f);
			if(chords != null) {
				chords.setTextSize(content.getTextSize());
			}
		}

	}

}
