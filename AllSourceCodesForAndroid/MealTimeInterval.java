package com.example.shihualu.gourmettest.Module;

public class MealTimeInterval {
    private int DiningCommons;
    private int mealTime;
    private double start;
    private double end;

    //set time with am or pm
    public MealTimeInterval(int diningCommons, int mealTime, double start, boolean isAm, double end, boolean isAm2) {
        DiningCommons = diningCommons;
        this.mealTime = mealTime;
        if(!isAm&&start<12) this.start = start+12;
        else if(isAm&&start>=12) this.start = start + 12;
        else this.start = start;
        if(!isAm2&&end<12) this.end = end+12;
        else if(isAm2&&end>=12) this.end = end + 12;
        else this.end = end;
    }

    public int getDiningCommons() {
        return DiningCommons;
    }

    public int getMealTime() {
        return mealTime;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }
}