package com.example.datacollectionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class setup_criteria extends AppCompatActivity {

    private CriteriaListAdapter criteria_adapter;
    //variables for storing useful data
    private String act_name;
    private String start_time_hr;
    private String start_time_min;
    private String duration;
    private String week_day; //in the form of 0010010

    private TextView act_name_view;

    private int[] checkStatus = new int[6];

    private AtomPayment item_data;

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

    public void checkTracker(View v) { //if one of the weekday was pressed
        AtomPayment condition = (AtomPayment) v.getTag();

        if(condition.getCheck()==false) {
            condition.setChecked(true);
            checkStatus[Integer.parseInt(condition.getID())] = 1;
        }
        else {
            condition.setChecked(false);
            checkStatus[Integer.parseInt(condition.getID())] = 0;
        }

        //record the current status of the weekdays
    }

    private void set_up_view_items(){
        add_view_item("You are walking","0");
        add_view_item("You are taking a shower","1");
        add_view_item("You are cooking","2");
        add_view_item("You are holding the phone","3");
        add_view_item("You are looking at the phone","4");
        add_view_item("The phone has not moved for a while","5");


        criteria_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself

    }

    private void add_view_item(String item_name, String id) { // add a list item

        AtomPayment mAtomPayment = new AtomPayment(item_name,0);
        mAtomPayment.setID(id);

        item_data = (AtomPayment) getIntent().getSerializableExtra("push_data");
        if(item_data.getConditions()!=null) {
            String[] condtion_input = item_data.getConditions().split(",");
            if(condtion_input[Integer.parseInt(id)].replaceAll("\\[","").replaceAll("\\]","").trim().equals("1")) {
                mAtomPayment.setChecked(true);
                checkStatus[Integer.parseInt(id)] = 1;
            }
        }

        criteria_adapter.add(mAtomPayment);// add an entry to the end of the array adapter

//
    }

    public void save_setting_data(View v) //onClick function associated with button "save"
    {
        finish();
    }

    @Override
    public void onBackPressed() { //if the "back" key was pressed...

        finish();

    }

    public void cancel_edit(View v) { //if the "cancel" button has been pressed
        cancel();
    }


    public void finish( ) {
        Intent data = new Intent();
        System.out.println(Arrays.toString(checkStatus));
        data.putExtra("checked", Arrays.toString(checkStatus));
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
