package pl.mikulski.songbook;

import pl.mikulski.songbook.db.Song;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_scrollable);
		
		Intent intent = this.getIntent();
		Song song = (Song) intent.getSerializableExtra(Song.DATA_NAME);
		
		((TextView)this.findViewById(R.id.song_title)).setText(song.getTitle());
		((TextView)this.findViewById(R.id.song_content)).setText(song.getContent());
		((TextView)this.findViewById(R.id.song_chords)).setText(song.getChords());
	}

}
