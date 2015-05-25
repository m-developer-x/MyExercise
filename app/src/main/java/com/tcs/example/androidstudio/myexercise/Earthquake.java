package com.tcs.example.androidstudio.myexercise;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JAVAXIAN on 24/05/15.
 */
public class Earthquake implements Parcelable {

    private String place;
    private String magnitude;
    private String time;
    private String latitude;
    private String longitude;
    private String depth;

    public Earthquake(){

    }

    public Earthquake(Parcel in){
        readFromParcel(in);
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(place);
        dest.writeString(magnitude);
        dest.writeString(time);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(depth);

    }

    private void readFromParcel(Parcel in){
        place = in.readString();
        magnitude = in.readString();
        time = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        depth = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };


}
