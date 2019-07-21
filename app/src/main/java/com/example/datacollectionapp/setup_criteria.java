package com.example.datacollectionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class setup_criteria extends AppCompatActivity {

    private CriteriaListAdapter criteria_adapter;
    //variables for storing useful data
    private String act_name;
    private String start_time_hr;
    private String start_time_min;
    private String duration;
    private String week_day; //in the form of 0010010

    private TextView act_name_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_criteria);

        act_name_view = (TextView) findViewById(R.id.act_name);

        setupListViewAdapter();
        set_up_view_items();

        Log.d("TAG", "setup_criteria has been called");
    }

    private void setupListViewAdapter() {
        criteria_adapter = new CriteriaListAdapter(setup_criteria.this, R.layout.list_item_criteria, new ArrayList<AtomPayment>());
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList3);
        atomPaysListView.setAdapter(criteria_adapter);
    }

    private void set_up_view_items(){
        add_view_item("The user is travelling","Travel");
        add_view_item("The user is taking a shower","Shower");
        add_view_item("The user is cooking","Cooking");
        add_view_item("The user is not doing anything","Nothing");

        criteria_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself

    }

    private void add_view_item(String item_name, String id) { // add a list item
        AtomPayment mAtomPayment = new AtomPayment(item_name,0);
        mAtomPayment.setID(id);
        criteria_adapter.add(mAtomPayment);// add an entry to the end of the array adapter
    }

    public void save_setting_data(View v) //onClick function associated with button "save"
    {
        act_name = act_name_view.getText().toString();
        finish();
    }

    @Override
    public void onBackPressed() { //if the "back" key was pressed...

        cancel();

    }

    public void cancel_edit(View v) { //if the "cancel" button has been pressed
        cancel();
    }


    public void finish( ) {
        Intent data = new Intent();
        data.putExtra("act_name",act_name);
        setResult(RESULT_OK,data);
        super.finish();
    }

    public void ready_to_cancel_activity() { //if the "back" key was pressed...

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit without saving?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); //do nothing if "no" is pressed
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void cancel() {
        Intent data = new Intent();
        data.putExtra("act_name",act_name);
//        data.putExtra("comment",comment);
        setResult(RESULT_CANCELED,data);
        super.finish();
    }
}
