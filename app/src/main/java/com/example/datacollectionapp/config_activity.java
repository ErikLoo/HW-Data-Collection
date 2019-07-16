package com.example.datacollectionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

public class config_activity extends AppCompatActivity {

    List<String> datasHour = new ArrayList<>();
    List<String> datasMins = new ArrayList<>();

    ScrollChoice scrollChoiceHour;
    ScrollChoice scrollChoiceMins;

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
            }
        });

        scrollChoiceMins.addItems(datasMins,0);
        scrollChoiceMins.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                //do some action please
            }
        });
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

}
