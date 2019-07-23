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

    private final int request_time= 1;
    private final int request_location= 2;
    private final int request_constraint= 3;

    private TextView act_name_view;
    private View itemView;

    private AtomPayment item_data;

    String time_view_name = "Specify a time and date";
    String location_view_name = "Specify a location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_activity_improved);

        setupListViewAdapter();
        prepopulate();
        set_up_view_items();

        Log.d("TAG", "config_improved has been called");
    }

    private void setupListViewAdapter() {
        config_adapter = new ConfigListAdapter(config_activity_improved.this, R.layout.list_item_config, new ArrayList<AtomPayment>());
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList2);
        atomPaysListView.setAdapter(config_adapter);
    }

    private void set_up_view_items(){
        add_view_item(time_view_name,"time");
        add_view_item(location_view_name,"location");
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
        read_data(itemToEdit); //copy data from saved data structure
        itemView = v;

        if(itemToEdit.getID()=="time") {
           Intent intent = new Intent("com.example.datacollectionapp.setup_time" );
            intent.putExtra("push_data",itemToEdit);
            startActivityForResult(intent,request_time);
            Log.d("TAG", "time");
        }
        else if(itemToEdit.getID()=="location") {
            Intent intent = new Intent("com.example.datacollectionapp.setup_map");
            intent.putExtra("push_data",itemToEdit);
            startActivityForResult(intent,request_location);
            Log.d("TAG", "location");
        }
        else if(itemToEdit.getID()=="constraint") {
            Intent intent = new Intent("com.example.datacollectionapp.setup_criteria"  );
            intent.putExtra("push_data",itemToEdit);
            startActivityForResult(intent,request_constraint);
            Log.d("TAG", "constraint");
        }
    }

    public void save_setting_data(View v) //onClick function associated with button "save"
    {
        act_name = act_name_view.getText().toString();

        System.out.println("Print the name: " + act_name);

        if(act_name.trim().length()<=0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have not named the activity yet!")
                    .setCancelable(false)
                    .setPositiveButton("Exit without saving", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancel();
                        }
                    })
                    .setNegativeButton("Continue editing", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel(); //do nothing if "no" is pressed
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else
        {
            item_data.setName(act_name);
            finish();
        }

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
        data.putExtra("return_data",item_data);
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
        data.putExtra("return_data",item_data);
//        data.putExtra("comment",comment);
        setResult(RESULT_CANCELED,data);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == request_time) {
            AtomPayment itemToEdit = (AtomPayment) itemView.getTag(); //find that particular view
            String disp_time = data.getExtras().getString("Hour")+" : "+data.getExtras().getString("Mins")+"  "+getWeek(data.getExtras().getString("Repeats"));
            itemToEdit.setName(disp_time);
            update_data_time(data);

//            config_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself

        }else if (resultCode == RESULT_OK && requestCode == request_location) {
            AtomPayment itemToEdit = (AtomPayment) itemView.getTag(); //find that particular view
            String disp_time = data.getExtras().getString("address");
            System.out.println("note this has been reached" + disp_time);
            itemToEdit.setName(disp_time);
            update_data_Location(data);

//            config_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself
        }
        else if (resultCode == RESULT_OK && requestCode == request_constraint) {
            AtomPayment itemToEdit = (AtomPayment) itemView.getTag(); //find that particular view
            String disp_time = data.getExtras().getString("checked");
            update_data_conditions(data);
        }


        config_adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself
    }

    private String getWeek(String weekdaystr)
    {
        String[] week = new String[]{"Su","Mon","Tu","We","Th","Fr","Sa"};
        String mWeek = " ";
        String[] weekday = weekdaystr.split(",");

        System.out.println("Weehday: " + weekdaystr);

        for(int i=0;i<7;i++)
        {
            if(weekday[i].replaceAll("\\[","").replaceAll("\\]","").trim().equals("1")) {
////                System.out.println("Weekday: " + week[i]);
               mWeek = mWeek + " " + week[i];
            }
        }

        return mWeek;
    }

    private void update_data_time(Intent data){
        item_data.setHour(data.getExtras().getString("Hour"));
        item_data.setMins(data.getExtras().getString("Mins"));
        item_data.setDuration(data.getExtras().getString("Duration"));
        item_data.setRepeats(data.getExtras().getString("Repeats"));

    }

    private void update_data_Location(Intent data){
        item_data.setLoation(data.getExtras().getString("address"));
    }

    private void update_data_conditions(Intent data){
        item_data.setConditions(data.getExtras().getString("checked"));
    }

    private void read_data(AtomPayment data_push) {
        data_push.setHour(item_data.getHour());
        data_push.setMins(item_data.getMins());
        data_push.setDuration(item_data.getDuration());
        data_push.setRepeats(item_data.getRepeats());
        data_push.setLoation(item_data.getLocation());
        data_push.setConditions(item_data.getConditions());

    }

    private void prepopulate() {
        item_data = (AtomPayment) getIntent().getSerializableExtra("push_data");
        act_name_view = (TextView) findViewById(R.id.act_name);

        if(item_data.getName()!=null) {act_name_view.setText(item_data.getName());}
        if(item_data.getHour()!=null) {time_view_name = item_data.getHour()+ " : " + item_data.getMins();}
        if(item_data.getRepeats()!=null) {time_view_name = time_view_name + getWeek(item_data.getRepeats());}
        if(item_data.getLocation()!=null) {location_view_name = item_data.getLocation();}

    }


}
