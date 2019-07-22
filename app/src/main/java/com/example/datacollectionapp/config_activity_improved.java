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

public class config_activity_improved extends AppCompatActivity {

    private ConfigListAdapter config_adapter;
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
        setContentView(R.layout.activity_config_activity_improved);

        act_name_view = (TextView) findViewById(R.id.act_name);

        setupListViewAdapter();
        set_up_view_items();

        Log.d("TAG", "config_improved has been called");
    }

    private void setupListViewAdapter() {
        config_adapter = new ConfigListAdapter(config_activity_improved.this, R.layout.list_item_config, new ArrayList<AtomPayment>());
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList2);
        atomPaysListView.setAdapter(config_adapter);
    }

    private void set_up_view_items(){
        add_view_item("Specify a time and date","time");
        add_view_item("Specify a location","location");
        add_view_item("Other criteria","constraint");

        config_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself

    }

    private void add_view_item(String item_name, String id) { // add a list item
        AtomPayment mAtomPayment = new AtomPayment(item_name,0);
        mAtomPayment.setID(id);
        config_adapter.add(mAtomPayment);// add an entry to the end of the array adapter
    }

    public void open_edit_page(View v) {
        AtomPayment itemToEdit = (AtomPayment) v.getTag(); //find that particular view

        final int request_time= 1;
        final int request_location= 2;
        final int request_constraint= 3;

        if(itemToEdit.getID()=="time") {
           Intent intent = new Intent("com.example.datacollectionapp.setup_time" );
           startActivityForResult(intent,request_time);
            Log.d("TAG", "time");
        }
        else if(itemToEdit.getID()=="location") {
//            Intent intent = new Intent("com.example.datacollectionapp.config_activity_improved" );
//            startActivityForResult(intent,request_time);
            Log.d("TAG", "location");
        }
        else if(itemToEdit.getID()=="constraint") {
            Intent intent = new Intent("com.example.datacollectionapp.setup_criteria"  );
            startActivityForResult(intent,request_constraint);
            Log.d("TAG", "constraint");
        }
    }

    public void save_setting_data(View v) //onClick function associated with button "save"
    {
        act_name = act_name_view.getText().toString();
        finish();
    }

    @Override
    public void onBackPressed() { //if the "back" key was pressed...

        ready_to_cancel_activity();

    }

    public void cancel_edit(View v) { //if the "cancel" button has been pressed
        ready_to_cancel_activity();
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
