package com.example.datacollectionapp;

import android.os.Parcelable;

import java.io.Serializable;

public class activity_entry implements Serializable {

    private String name = "";
    private String id = "not assigned";
    private double value = 0;
    private boolean extension = false;

    public activity_entry(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }
    public String getID() {return id;}

    public void setName(String name) {
        this.name = name;
    }
    public void setID(String id) {this.id = id;}

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean getExtend() {return extension;}

    public void setTrue() {extension = true;}

    public void setFalse() {extension = false;}
}
