package com.example.proximityrenaultmais;

import android.os.Parcelable;
import android.os.Parcel;


public class CustomGeofence implements Parcelable {

    private String name;
    private double latitude;
    private double longitude;
    private int radius;



    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CustomGeofence createFromParcel(Parcel in) {
            return new CustomGeofence(in);
        }

        public CustomGeofence[] newArray(int size) {
            return new CustomGeofence[size];
        }
    };

    //CONSTRUCTOR
    CustomGeofence(String name, double lattitude, double longitude, int radius){
        this.name = name;
        this.radius = radius;
        this.latitude = lattitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name =  newName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double newLatitude) {
        this.latitude =  newLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double newLongitude) {
        this.longitude =  newLongitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int newRadius) {
        this.radius =  newRadius;
    }

    //PARCELLING PART

    public CustomGeofence(Parcel in){
        this.name = in.readString();
        this.latitude = in.readDouble();
        this.longitude =  in.readDouble();
        this.radius = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.radius);
    }

};