package com.example.datacollectionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

public class setup_time extends AppCompatActivity {


    List<String> datasHour = new ArrayList<>();
    List<String> datasMins = new ArrayList<>();

    //variables for making the scroll clock interface
    ScrollChoice scrollChoiceHour;
    ScrollChoice scrollChoiceMins;

    //variables for storing useful data
    private String act_name;

    private SeekBar seek_bar;
    private TextView seek_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_time);

        iniViews();

        loadDatas();

        set_seek_bar();

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

        setup_the_week();

    }

    public void set_seek_bar() {
        seek_bar = (SeekBar)findViewById(R.id.seekBar2);
        seek_view = (TextView)findViewById(R.id.seekView);
       seek_view.setText(Integer.toString(seek_bar.getProgress()/10));
//
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        seek_view.setText(Integer.toString(progress/10));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seek_view.setText(Integer.toString(progress_value/10));
                    }
                }
        );
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

    private void setup_the_week() {
        setup_weekday("Monday",(Button)findViewById(R.id.mon1));
        setup_weekday("Tuesday",(Button)findViewById(R.id.tue2));
        setup_weekday("Wednesday",(Button)findViewById(R.id.wed3));
        setup_weekday("Thursday",(Button)findViewById(R.id.th4));
        setup_weekday("Friday",(Button)findViewById(R.id.fri5));
        setup_weekday("Saturday",(Button)findViewById(R.id.sat6));
        setup_weekday("Sunday",(Button)findViewById(R.id.sun7));
    }

    private void setup_weekday(String weekday,View v) {
        Weekday mWeekday = new Weekday(weekday,false);
        v.setTag(mWeekday);
    }

    public class Weekday{
        private String name;
        private boolean isPressed;

        public Weekday(String name,boolean isPressed) {
            this.setName(name);
            this.setStatus(isPressed);
        }

        public String getName() {return name;}
        public boolean getStatus(){return isPressed;}

        public void setStatus(boolean isPressed) {this.isPressed = isPressed;}
        public void setName(String name) {this.name = name;}
    }


    public void WeekdayPressed(View v) { //if one of the weekday was pressed
       Weekday mWeekday = (Weekday) v.getTag();
       String name = mWeekday.getName();
       boolean isPressed = mWeekday.getStatus();

            if(isPressed==false) {
                mWeekday.setStatus(true);
                v.setBackgroundResource(R.drawable.redroundedbutton);
            }
            else {
                mWeekday.setStatus(false);
                v.setBackgroundResource(R.drawable.whiteroundedbutton);
            }
            //record the current status of the weekdays
        }

    @Override
    public void onBackPressed() { //if the "back" key was pressed...

//        ready_to_cancel_activity();
        cancel();

    }

    public void cancel_edit(View v) { //if the "cancel" button has been pressed
//        ready_to_cancel_activity();
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
