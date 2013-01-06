package pl.mikulski.songbook;

import java.util.ArrayList;

import pl.mikulski.songbook.transposer.Chord;
import pl.mikulski.songbook.transposer.Chord.Note;
import pl.mikulski.songbook.transposer.Chord.NoteMajor;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Dialog for selecting transposition interval in SongActivity
 * 
 * @author KM
 */
public class TransposeDialog extends Dialog {

	// list of values for use by spinner adapter
	private static final ArrayList<String> VALUES_MAJOR = new ArrayList<String>();
	private static final ArrayList<String> VALUES_MINOR = new ArrayList<String>();

	static {
		for (Note note : Chord.Note.values()) {
			VALUES_MINOR.add(note.toString());
		}
		for (NoteMajor note : Chord.NoteMajor.values()) {
			VALUES_MAJOR.add(note.toString());
		}
	}

	// activity calling the dialog
	private SongActivity host;
	// initial chord of the current song
	private Chord initialChord;

	public TransposeDialog(SongActivity host, Chord initialChord) {
		super(host);
		this.host = host;
		this.initialChord = initialChord;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// do not display title bar on dialog
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_transpose);
		final Spinner spinner = (Spinner) findViewById(R.id.transpose_spinner);
		// populate the spinner and set it for correct chord
		spinner.setAdapter(new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_spinner_dropdown_item,
				initialChord.isMajor() ? VALUES_MAJOR : VALUES_MINOR));
		spinner.setSelection(initialChord.getNote().ordinal());
		((Button) findViewById(R.id.transpose_ok)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String selection = ((TextView) spinner.getSelectedView()).getText().toString();
				Chord selectedChord = Chord.findChord(selection);
				int interval = selectedChord.getNote().ordinal() - initialChord.getNote().ordinal();
				host.transpose(interval);
				TransposeDialog.this.dismiss();
			}
		});
		((Button) findViewById(R.id.transpose_cancel))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						TransposeDialog.this.dismiss();
					}
				});
	}
}
