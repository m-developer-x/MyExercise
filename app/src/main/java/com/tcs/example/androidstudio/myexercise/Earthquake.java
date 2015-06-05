package com.tcs.example.androidstudio.myexercise;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JAVAXIAN on 24/05/15.
 */
public class Earthquake implements Parcelable {

    //Fields for Earthquake
    private String place;
    private String magnitude;
    private String time;
    private String latitude;
    private String longitude;
    private String depth;

    //Public constructor
    public Earthquake(){

    }

    //Public constructor to invoke read from parcel
    public Earthquake(Parcel in){
        readFromParcel(in);
    }

    //Getters and Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }


    //Method to describe contents
    @Override
    public int describeContents() {
        return 0;
    }

    //Method to write to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        //Write parameters
        dest.writeString(place);
        dest.writeString(magnitude);
        dest.writeString(time);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(depth);

    }

    //Method to read from parcel
    private void readFromParcel(Parcel in){

        //Read parameters
        place = in.readString();
        magnitude = in.readString();
        time = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        depth = in.readString();
    }

    //Parcel creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };


}
