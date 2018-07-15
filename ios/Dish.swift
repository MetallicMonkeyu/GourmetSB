//
//  Dish.swift
//  GourmetSB
//
//  Created by siyaoli on 2/24/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

public class Dish {
    //basic attributes
    private let dishName : String;
    private let fromDC : Int;
    private let section : String;
    private let mealTime : String;
    private let foodType : Int;
    //private boolean isNuts;
//    private var score : Double = 1;
    
    //attributes for better sort
    
    init(dishName : String, fromDC : Int, section : String, mealTime : String, foodType : Int) {
        self.dishName = dishName;
        self.fromDC = fromDC;
        self.section = section;
        self.mealTime = mealTime;
        self.foodType = foodType;
    }
    
    public func getDishName() -> String{
        return dishName;
    }
    
    public func getFoodType() -> Int {
        return foodType;
    }
    
    public func getFromDC() -> Int {
        return fromDC;
    }
    
    public func getSection() -> String {
        return section;
    }
    
//    public func getScore() -> Double {
//        return score;
//    }
//    
//    public func setScore(score : Double) {
//        self.score = score;
//    }
    
//    public void setFoodType(int foodType) {
//    this.foodType = foodType;
//    }
    
//    public boolean isNuts() {
//    return isNuts;
//    }
    
//    public void setNuts(boolean nuts) {
//    isNuts = nuts;
//    }
    
    public func getMealTime() -> String {
        return mealTime;
    }
}
