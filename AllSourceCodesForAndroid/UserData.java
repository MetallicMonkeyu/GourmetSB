package com.example.shihualu.gourmettest.Module;

import java.io.Serializable;

public class UserData implements Serializable {
    private double MVRatio;
    private int LocationPref[] = new int[4];

    public double getMVRatio() {
        return MVRatio;
    }

    public void setMVRatio(double MVRatio) {
        this.MVRatio = MVRatio;
    }

    public int[] getLocationPref() {
        return LocationPref;
    }

    public void setLocationPref(int[] LocationPref) {
        this.LocationPref = LocationPref;
    }

}