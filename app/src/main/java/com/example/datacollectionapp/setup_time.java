package com.example.datacollectionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.webianks.library.scroll_choice.ScrollChoice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class setup_time extends AppCompatActivity {


    List<String> datasHour = new ArrayList<>();
    List<String> datasMins = new ArrayList<>();

    int[] weekday = new int[] {0,0,0,0,0,0,0};
    //variables for making the scroll clock interface
    ScrollChoice scrollChoiceHour;
    ScrollChoice scrollChoiceMins;

    //variables for storing useful data
    private String act_name;

    private SeekBar seek_bar;
    private TextView seek_view;

    Calendar currentTime;

    private String hour;
    private String mins;
    private String repeats;
    private String duration;
    private String weekdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_time);

        AtomPayment item_data = (AtomPayment) getIntent().getSerializableExtra("push_data");

        iniViews();

        loadDatas();

        set_seek_bar(item_data);

        //if no save_file is detected
        int default_hour;
        int default_min;

        if(item_data.getHour()!=null) { //if the saved data exist
            default_hour = Integer.parseInt(item_data.getHour());
            default_min = Integer.parseInt(item_data.getMins());
        }
        else {
            currentTime = Calendar.getInstance();
            default_hour= currentTime.get(Calendar.HOUR_OF_DAY);
            default_min = currentTime.get(Calendar.MINUTE);
        }

        hour = Integer.toString(default_hour);
        mins = Integer.toString(default_min);

        scrollChoiceHour.addItems(datasHour,default_hour); //set the default hour to the current hour
        scrollChoiceHour.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                hour = name;
            }
        });

        scrollChoiceMins.addItems(datasMins,default_min); //set the defualt minute to the current minute
        scrollChoiceMins.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                //do some action please
                mins = name;
            }
        });

        weekdata = item_data.getRepeats();

        setup_the_week();

    }

    public void set_seek_bar(AtomPayment item_data) {
        seek_bar = (SeekBar)findViewById(R.id.seekBar2);
        int progress = 50;

        if(item_data.getDuration()!=null) {
            progress = 10*Integer.parseInt(item_data.getDuration());
        }

        seek_bar.setProgress(progress);  //set the defualt value of the seebar view to be 50

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
        setup_weekday("Monday",(Button)findViewById(R.id.mon1),1);
        setup_weekday("Tuesday",(Button)findViewById(R.id.tue2),2);
        setup_weekday("Wednesday",(Button)findViewById(R.id.wed3),3);
        setup_weekday("Thursday",(Button)findViewById(R.id.th4),4);
        setup_weekday("Friday",(Button)findViewById(R.id.fri5),5);
        setup_weekday("Saturday",(Button)findViewById(R.id.sat6),6);
        setup_weekday("Sunday",(Button)findViewById(R.id.sun7),0);
    }

    private void setup_weekday(String weekday2,View v,int id) {
        Weekday mWeekday = new Weekday(weekday2,false,id);

        System.out.println("Week in: " + weekdata);

        if(weekdata!=null) {
            String[] weekinput = weekdata.split(",");
            if(weekinput[id].replaceAll("\\[","").replaceAll("\\]","").trim().equals("1")) {
                v.setBackgroundResource(R.drawable.redroundedbutton);
                mWeekday.setStatus(true);
                weekday[id] = 1;
            }
        }
        v.setTag(mWeekday);
    }

    public class Weekday{
        private String name;
        private boolean isPressed = false;
        private int id;

        public Weekday(String name,boolean isPressed,int id) {
            this.setName(name);
            this.setStatus(isPressed);
            this.setID(id);
        }

        public String getName() {return name;}
        public boolean getStatus(){return isPressed;}

        public void setStatus(boolean isPressed) {this.isPressed = isPressed;}
        public void setName(String name) {this.name = name;}

        public int getID() {return id;}
        public void setID(int id) {this.id = id;}
    }


    public void WeekdayPressed(View v) { //if one of the weekday was pressed
       Weekday mWeekday = (Weekday) v.getTag();
       String name = mWeekday.getName();
       boolean isPressed = mWeekday.getStatus();

            if(isPressed==false) {
                mWeekday.setStatus(true);
                v.setBackgroundResource(R.drawable.redroundedbutton);
                weekday[mWeekday.getID()] = 1;
            }
            else {
                mWeekday.setStatus(false);
                v.setBackgroundResource(R.drawable.whiteroundedbutton);
                weekday[mWeekday.getID()] = 0;
            }

            //record the current status of the weekdays
        }

    @Override
    public void onBackPressed() { //if the "back" key was pressed...

//        ready_to_cancel_activity();
        cancel();

    }

    public void finish_edit(View v) { //if the "cancel" button has been pressed
//        ready_to_cancel_activity();
        finish();
    }


    public void finish( ) {
        Intent data = new Intent();
        duration = seek_view.getText().toString();

        System.out.println("return week: " + Arrays.toString(weekday));

        data.putExtra("Hour",hour);
        data.putExtra("Mins",mins);
//        data.putExtra("Repeats",repeats);
        data.putExtra("Duration",duration);
        data.putExtra("Repeats",Arrays.toString(weekday));
        setResult(RESULT_OK,data);
        super.finish();
    }

    public boolean load_setting(){
        AtomPayment item_data = (AtomPayment) getIntent().getSerializableExtra("Entry_data");
        if(item_data!=null) {return true;}
        return false;
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

//        data.putExtra("comment",comment);
        setResult(RESULT_CANCELED,data);
        super.finish();
    }
}
