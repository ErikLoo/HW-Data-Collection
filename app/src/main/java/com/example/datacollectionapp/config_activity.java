package com.example.datacollectionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

public class config_activity extends AppCompatActivity {

    List<String> datasHour = new ArrayList<>();
    List<String> datasMins = new ArrayList<>();

    //variables for making the scroll clock interface
    ScrollChoice scrollChoiceHour;
    ScrollChoice scrollChoiceMins;

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
        setContentView(R.layout.activity_config_activity);

        iniViews();

        loadDatas();

        scrollChoiceHour.addItems(datasHour,0); //save this index somewhere
        scrollChoiceHour.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                //do some action please
                start_time_hr = name;
            }
        });

        scrollChoiceMins.addItems(datasMins,0);
        scrollChoiceMins.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                //do some action please
                start_time_min = name;
            }
        });

        act_name_view = (TextView) findViewById(R.id.act_name);
    }

    public class settingData
    {
        String act_name;
        String comment;
    }

    public void save_setting_data(View v) //onClick function associated with button "save"
    {
        act_name = act_name_view.getText().toString();
        finish();
    }

    public void cancel_edit(View v) {
        cancel();
    }

    private void loadDatas(){

        for(int i=0;i<=23;i++){
            String time = Integer.toString(i);
            if(i<10) time = '0' + time;
             datasHour.add(time);
        }

        for(int i=0;i<=59;i++){
            String time = Integer.toString(i);
            if(i<10) time = '0' + time;
            datasMins.add(time);
        }
    }

    private void iniViews() {
        scrollChoiceHour = (ScrollChoice)findViewById(R.id.scroll_choice_hour);
        scrollChoiceMins = (ScrollChoice)findViewById(R.id.scroll_choice_minute);
    }

    public void finish( ) {
        //put extra data to the intent
        Intent data = new Intent();
        data.putExtra("act_name",act_name);
//        data.putExtra("comment",comment);
        setResult(RESULT_OK,data);
        super.finish();
    }

    public void cancel() {
        Intent data = new Intent();
        data.putExtra("act_name",act_name);
//        data.putExtra("comment",comment);
        setResult(RESULT_CANCELED,data);
        super.finish();
    }
}
