package pl.mikulski.songbook;

import pl.mikulski.songbook.db.DatabaseHandler;
import pl.mikulski.songbook.db.Song;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

	private DatabaseHandler db = null;
	private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		setContentView(R.layout.main);

		new Thread() {
			public void run() {
				if (db == null) {
					db = new DatabaseHandler(MainActivity.this);
				}
				final Cursor cursor = db.getAllSongs();
				handler.post(new Runnable() {
					public void run() {
						CursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
								android.R.layout.simple_list_item_1, cursor,
								new String[] { "title" }, new int[] { android.R.id.text1 });
						setListAdapter(adapter);
					}
				});
			}
		}.start();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Song song = db.getSong(id);
		Intent intent = new Intent(this, SongActivity.class);
		intent.putExtra(Song.DATA_NAME, song);
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
