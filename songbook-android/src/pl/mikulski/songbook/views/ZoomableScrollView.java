package pl.mikulski.songbook.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ScrollView;

public class ZoomableScrollView extends ScrollView {

	private ScaleGestureDetector mScaleDetector;
	
	public ZoomableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){

			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				Log.e("bz", "STARTING SCALE: " + detector.getScaleFactor());
				return true;
			}

			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				Log.e("bz", "ENDING SCALE: " + detector.getScaleFactor());
			}
			
			
			
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return mScaleDetector.onTouchEvent(ev);
	}

}
