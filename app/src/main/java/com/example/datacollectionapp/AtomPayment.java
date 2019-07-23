package com.example.datacollectionapp;

import java.io.Serializable;

public class AtomPayment implements Serializable {
	private static final long serialVersionUID = -5435670920302756945L;
	
	private String name;
	private String id = "not assigned";
	private double value = 0;
	private boolean extension = false;
	private String hour;
	private String minute;
	private String duration;
	private String repeats;
	private String location;
	private String conditions;
	private boolean checked;

	public AtomPayment(String name, double value) {
		this.setName(name);
		this.setValue(value);
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

	public String getHour() {return hour;}
	public void setHour(String hour) {this.hour = hour;}

	public String getMins() {return minute;}
	public void setMins(String minute) {this.minute = minute;}

	public String getDuration() {return duration;}
	public void setDuration(String duration) {this.duration = duration;}

	public String getRepeats() {return repeats;}
	public void setRepeats(String repeats) {this.repeats = repeats;}

	public String getLocation() {return location;}
	public void setLoation(String location) {this.location = location;}

	public boolean getCheck() {return checked;}
	public void setChecked(boolean checked) {this.checked = checked;}

	public String getConditions() {return conditions;}
	public void setConditions(String conditions) {this.conditions = conditions;}
}