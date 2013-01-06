package pl.mikulski.songbook;

import java.util.Map;

import pl.mikulski.songbook.db.DatabaseHelper;
import pl.mikulski.songbook.db.Song;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main application activity representing the list of available songs
 * 
 * @author KM
 */
public class MainActivity extends ListActivity {

	private DatabaseHelper db;
	private ArrayAdapter<String> adapter;

	private Map<String, Integer> songs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Handler handler = new Handler();
		setContentView(R.layout.main);

		// delegate database operations to a background thread
		new Thread() {
			public void run() {
				if (db == null) {
					db = new DatabaseHelper(MainActivity.this);
				}
				songs = db.getAllSongs();
				// after retrieving data from db, post list update
				// for execution in UI thread
				handler.post(new Runnable() {
					public void run() {
						String[] items = songs.keySet().toArray(new String[0]);
						// using ArrayAdapter instead of CursorAdapter allows
						// for filtering list without requerying the db
						adapter = new ArrayAdapter<String>(MainActivity.this,
								android.R.layout.simple_list_item_1, items);
						setListAdapter(adapter);
					}
				});
			}
		}.start();

		setSearchListener();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String title = ((TextView) v).getText().toString();
		Integer songId = songs.get(title);
		if (songId == null) {
			// should never happen, but just to be on the safe side...
			Toast.makeText(this, getString(R.string.error_title_not_found, title),
					Toast.LENGTH_SHORT).show();
			return;
		}
		Song song = db.getSong(songId);
		Intent intent = new Intent(this, SongActivity.class);
		intent.putExtra(Song.DATA_NAME, song);
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * register search textfield for list filtering
	 */
	private void setSearchListener() {
		EditText search = (EditText) this.findViewById(R.id.search);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (adapter != null) {
					adapter.getFilter().filter(s);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// do nothing
			}

		});
	}

}
