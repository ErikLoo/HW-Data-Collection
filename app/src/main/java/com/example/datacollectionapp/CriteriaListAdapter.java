package com.example.datacollectionapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CriteriaListAdapter extends ArrayAdapter<AtomPayment> {

	protected static final String LOG_TAG = CriteriaListAdapter.class.getSimpleName();

	private List<AtomPayment> items;
	private int layoutResourceId;
	private Context context;
	private GestureDetector mDetector;
	private boolean retract = false;
	private AtomPaymentHolder globalHolder = null;

	public CriteriaListAdapter(Context context, int layoutResourceId, List<AtomPayment> items) {
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

		holder.checkbox = (CheckBox) row.findViewById(R.id.checkBox);
		holder.checkbox.setTag(holder.atomPayment);

		if(holder.atomPayment.getCheck()==true) {holder.checkbox.setChecked(true);}; //check the check box

		holder.name = (TextView)row.findViewById(R.id.criterion);
		holder.name.setTag(holder.atomPayment);

		row.setTag(holder);

		setupItem(holder);

		Log.i("TAG", "getView() has been called");

		return row;
	}

	private void setupItem(AtomPaymentHolder holder) {
		holder.name.setText(holder.atomPayment.getName());
	}

	public static class AtomPaymentHolder {// this is just a data structure
		AtomPayment atomPayment;
		TextView name;
		TextView value;
		ImageButton removePaymentButton;
		ImageButton editButton;
		Switch switchButton;
		CheckBox checkbox;
	}


}
