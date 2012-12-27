package pl.mikulski.songbook.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static String DB_NAME = "songbook.db";
	private static String DB_PATH = "/data/data/pl.mikulski.songbook/databases/" + DB_NAME;

	private final Context context;

	private SQLiteDatabase db;

	public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
		initDatabase();
	}

	/**
	 * Initialise the database field, if necessary create the database and copy
	 * contents from assets
	 * */
	public void initDatabase() {
		try {
			this.db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// means the database hasn't been created yet
			Log.w(this.getClass().getName(), "database not found, will create from assets");
		}
		if (this.db == null) {
			try {
				this.db = this.getReadableDatabase();
				copyDatabaseFromAssets();
			} catch (IOException e) {
				Log.e(this.getClass().getName(), "unable to seed database from assets", e);
			}
		}
	}

	private void copyDatabaseFromAssets() throws IOException {
		InputStream myInput = null;
		OutputStream myOutput = null;
		try {
			myInput = context.getAssets().open(DB_NAME);
			myOutput = new FileOutputStream(DB_PATH);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
		} finally {
			if (myOutput != null) {
				myOutput.flush();
				myOutput.close();
			}
			if (myInput != null) {
				myInput.close();
			}
		}
	}

	@Override
	public synchronized void close() {
		if (db != null) {
			db.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public Cursor getAllSongs() {
		return db.rawQuery("select * from songs order by " + Song.COLUMN_TITLE, null);
	}

	public Song getSong(long id) {
		Cursor cursor = db.query(Song.TABLE_SONGS, new String[] { Song.COLUMN_TITLE,
				Song.COLUMN_CONTENT, Song.COLUMN_CHORDS }, "_id = " + id, null, null, null, null);
		if (!cursor.moveToFirst()) {
			return null;
		}
		String title = cursor.getString(0);
		String contents = cursor.getString(1);
		String chords = cursor.getString(2);
		return new Song(title, contents, chords);
	}
}
