package com.example.datacollectionapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class AtomPayListAdapter extends ArrayAdapter<AtomPayment> {

	protected static final String LOG_TAG = AtomPayListAdapter.class.getSimpleName();

	private List<AtomPayment> items;
	private int layoutResourceId;
	private Context context;
	private GestureDetector mDetector;
	private boolean retract = false;
	private AtomPaymentHolder globalHolder = null;

	public AtomPayListAdapter(Context context, int layoutResourceId, List<AtomPayment> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AtomPaymentHolder holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new AtomPaymentHolder();
		holder.atomPayment = items.get(position);
		holder.removePaymentButton = (ImageButton)row.findViewById(R.id.atomPay_removePay);
		holder.removePaymentButton.setTag(holder.atomPayment); //The tag of a view can store data

		holder.editButton = (ImageButton)row.findViewById(R.id.edit_button);
		holder.editButton.setTag(holder.atomPayment);

		holder.switchButton = (Switch)row.findViewById(R.id.switch2);

		holder.name = (TextView)row.findViewById(R.id.atomPay_name);

		//setNameTextChangeListener(holder);
//		holder.value = (TextView)row.findViewById(R.id.atomPay_value);
		//setValueTextListeners(holder);

		row.setTag(holder);

		setupItem(holder);
		swipeListener(holder);

		Log.i("TAG", "getView() has been called");

//		//intialize all the view to the retracted state
		initial_pos(holder);
		holder.atomPayment.setFalse();

		return row;
	}

	private void setupItem(AtomPaymentHolder holder) {
		holder.name.setText(holder.atomPayment.getName());
//		holder.value.setText(String.valueOf(holder.atomPayment.getValue()));
	}

	public static class AtomPaymentHolder {// this is just a data structure
		AtomPayment atomPayment;
		TextView name;
		TextView value;
		ImageButton removePaymentButton;
		ImageButton editButton;
		Switch switchButton;
	}

	private void swipeListener(final AtomPaymentHolder holder){
		//mDetector = new GestureDetector(context, new MyGestureListener());
		holder.name.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//Log.d("TAG", holder.name.getText() + "Has been touched");
				//return mDetector.onTouchEvent(event);
				if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
					//if(holder.atomPayment.getExtend()==true) retract_view(holder);
					Log.d("TAG", "Fell outside");

				}
				if(event.getAction() == MotionEvent.ACTION_DOWN){

					// Do what you want
					animate_view(holder);
					return true;
				}
				return false;
			}

		});
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent event) {
//			Log.d("TAG","onDown: ");

			// don't return false here or else none of the other
			// gestures will work
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
//			Log.i("TAG", "onSingleTapConfirmed: ");
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.i("TAG", "onLongPress: ");
			//activate a function here
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
		//	Log.i("TAG", "onDoubleTap: ");
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
								float distanceX, float distanceY) {
//			Log.i("TAG", "onScroll: ");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent,
							   float velocityX, float velocityY) {
//			Log.d("TAG", "onFling: ");

			boolean result = false;
			float diffY = moveEvent.getY() - downEvent.getY();
			float diffX = moveEvent.getX() - downEvent.getX();

//			if(Math.abs(diffX)>Math.abs(diffY)) {
//				if(Math.abs(diffX)>50 && Math.abs(velocityX)>20) {
//					if(diffX>0){
//						Log.d("TAG", "Right Swipe");
//						retract = true;
//						//hide view for the three buttons
//					} else {
//						Log.d("TAG", "Left Swipe");
//						retract = false;
//						//reveal view for the three buttons
//					}
//				}
//			} else {
//				Log.d("TAG", "Upper swipe detected");
//			}

			return result;
		}
	}

	private void animate_view(final AtomPaymentHolder holder) {

		if(holder.atomPayment.getExtend()==false) {
			extend_view(holder);
			holder.atomPayment.setTrue();

		} else {
			retract_view(holder);
			holder.atomPayment.setFalse();
		}

	}

	private void initial_pos(final AtomPaymentHolder holder){
		holder.name.setTranslationX(200);// move right
		holder.switchButton.setVisibility(View.INVISIBLE);
		holder.editButton.setVisibility(View.INVISIBLE);
		holder.removePaymentButton.setVisibility(View.INVISIBLE);
	}


	private void extend_view(final AtomPaymentHolder holder) {
		holder.name.animate(). translationX(-200);// move right
		holder.switchButton.setVisibility(View.VISIBLE);
		holder.editButton.setVisibility(View.VISIBLE);
		holder.removePaymentButton.setVisibility(View.VISIBLE);
	}

	private void retract_view(final AtomPaymentHolder holder) {
		holder.name.animate(). translationX(200);// move right
		holder.switchButton.setVisibility(View.INVISIBLE);
		holder.editButton.setVisibility(View.INVISIBLE);
		holder.removePaymentButton.setVisibility(View.INVISIBLE);
	}

	private void setNameTextChangeListener(final AtomPaymentHolder holder) {
		holder.name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				holder.atomPayment.setName(s.toString());
				globalHolder = holder;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) { }
		});
	}

	private void setValueTextListeners(final AtomPaymentHolder holder) {
		holder.value.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try{
					holder.atomPayment.setValue(Double.parseDouble(s.toString()));
				}catch (NumberFormatException e) {
					Log.e(LOG_TAG, "error reading double value: " + s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) { }
		});
	}
}
