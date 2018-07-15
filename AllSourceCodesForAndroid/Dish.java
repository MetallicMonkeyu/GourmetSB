package com.example.shihualu.gourmettest.Module;

public class Dish {
    //basic attributes
    private String dishName;
    private int fromDC;
    private String section;
    private int mealTime;
    private int foodType;
    private boolean isNuts;
    private double score=2;

    //attributes for better sort

    public Dish(String dishName, int fromDC, String section, int mealTime) {
        this.dishName = dishName;
        this.fromDC = fromDC;
        this.section = section;
        this.mealTime = mealTime;
    }



    public String getDishName() {
        return dishName;
    }

    public int getFoodType() {
        return foodType;
    }

    public int getFromDC() {
        return fromDC;
    }

    public String getSection() {
        return section;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public void setFromDC(int fromDC) {
        this.fromDC = fromDC;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isNuts() {
        return isNuts;
    }

    public void setNuts(boolean nuts) {
        isNuts = nuts;
    }

    public int getMealTime() {
        return mealTime;
    }

    public void setMealTime(int mealTime) {
        this.mealTime = mealTime;
    }
}