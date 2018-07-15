//
//  DataManager.swift
//  GourmetSB
//
//  Created by siyaoli on 2/26/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

final class DataManager{
    
// -----------------------------------------------------------------------------------------------
    //conciseMenu and its helpers
    public static func getConciseMenu(diningCommon : Int) -> [Dish]{
        //calc the number of dish to present , can be more flexible
        var numberOfMeatDish : Int = 0
        var numberOfVOrVeganDish : Int = 0
        var numberOfVeganDish : Int = 0
        if UserData.getMVRatio() > 1{
            numberOfMeatDish = Int(round(UserData.getMVRatio()))
            numberOfVOrVeganDish = 6 - Int(round(UserData.getMVRatio()))
        }else if UserData.getMVRatio() == 1{
            numberOfVOrVeganDish = 6
        }else{
            numberOfVeganDish = 6
        }
        
        return pickDishFromPreferedSection(diningCommon: diningCommon, numberOfMeatDish: numberOfMeatDish, numberOfVOrVeganDish: numberOfVOrVeganDish, numberOfVeganDish: numberOfVeganDish)
    }
    
    
    private static func pickDishFromPreferedSection(diningCommon : Int, numberOfMeatDish : Int, numberOfVOrVeganDish : Int, numberOfVeganDish : Int) -> [Dish]{
        let currentDishes = getCurrentDish(diningCommon: diningCommon)
        if currentDishes.count == 0{
            return []
        }
        
      //if need to present dish
        var conciseMenu : [Dish] = []
        var numberOfMeatDish = numberOfMeatDish
        var numberOfVOrVeganDish = numberOfVOrVeganDish
        var numberOfVeganDish = numberOfVeganDish
        //find the sections user like and choose dish from those section evenly from top to bottom (evenly if user is balanced eater)
        let loopIterator = LoopIterator(stringArr: UserData.getPreferedSection(diningCommon: diningCommon))
        if loopIterator.getSize() == 0 { return []}
        var checkingSection = ""
        
        //record the number of dish in conciseMenu and number of loops through prefered section
        var pcount = -1
        var ploop = 0
        var changeRequiredDishNumber = 0
        while numberOfVeganDish > 0 || numberOfMeatDish > 0 || numberOfVOrVeganDish > 0{
            if ploop != loopIterator.getLoopNumber(){  //then we go through one loop of string array
                if pcount < conciseMenu.count{  //then in this loop, we add some dish successfully
                    //nothing to worry about
                    pcount = conciseMenu.count
                }else{
                    //we didn't add any dish
                    if changeRequiredDishNumber > 2 {//not enough dish to choose from
                        break
                    }else if numberOfMeatDish > 0 {
                        numberOfVOrVeganDish = numberOfMeatDish
                        numberOfMeatDish = 0
                        changeRequiredDishNumber += 1
                    }else if numberOfVOrVeganDish > 0{
                        numberOfMeatDish = numberOfVOrVeganDish
                        numberOfVOrVeganDish = 0
                        changeRequiredDishNumber += 1
                    }else if numberOfVeganDish > 0{
                        numberOfVOrVeganDish = numberOfVeganDish
                        numberOfVeganDish = 0
                        changeRequiredDishNumber += 1
                    }
                }
                ploop += 1
            }
            checkingSection = loopIterator.next()
            for dish in currentDishes{
                if dish.getSection() == checkingSection {
                    //find one addable dish and break
                    if dish.getFoodType() == FoodType.MEAT && numberOfMeatDish > 0 {
                        //check if already added
                        if checkIfDishAlreadyExist(dish: dish, dishes: conciseMenu){continue}
                        numberOfMeatDish -= 1
                        conciseMenu.append(dish)
                        break
                    }else if dish.getFoodType() == FoodType.VEGAN && numberOfVOrVeganDish > 0 {
                        if checkIfDishAlreadyExist(dish: dish, dishes: conciseMenu){continue}
                        numberOfVOrVeganDish -= 1
                        conciseMenu.append(dish)
                        break
                    }else if dish.getFoodType() == FoodType.VEGAN && numberOfVeganDish > 0 {
                        if checkIfDishAlreadyExist(dish: dish, dishes: conciseMenu){continue}
                        numberOfVeganDish -= 1
                        conciseMenu.append(dish)
                        break
                    }else if dish.getFoodType() == FoodType.VEGETABLE && numberOfVOrVeganDish > 0 {
                        if checkIfDishAlreadyExist(dish: dish, dishes: conciseMenu){continue}
                        numberOfVOrVeganDish -= 1
                        conciseMenu.append(dish)
                        break
                    }
                }
            }
        }
        
        return conciseMenu
    }
    
    private static func checkIfDishAlreadyExist(dish : Dish, dishes : [Dish]) -> Bool{
        for d in dishes{
            if d.getDishName() == dish.getDishName(){
                return true
            }
        }
        return false
    }
    
//--------------------------------------------------------------------------------------------------
    //sorting algorithm
    public static func sortDC() -> [Int]{
        let userPreferedDC = UserData.getPreferedDC()
        var dcScores : [(Int, Double)] = []
        for i in 1...4{
            if userPreferedDC.contains(i){ //if user want to view the dc
                let currentDishes = getCurrentDish(diningCommon: i)
                var DCScore = 0.0
                for dish in currentDishes{
                    DCScore += evaluateDish(dish: dish)
                }
                dcScores.append((i,DCScore))
            }else {
                if getCurrentDish(diningCommon: i).count == 0{
                    dcScores.append((i,0.0))
                }else{
                    dcScores.append((i,0.1))
                }
            }
        }
        dcScores.sort(by : {$0.1 > $1.1 })
        return [dcScores[0].0,dcScores[1].0,dcScores[2].0,dcScores[3].0]
    }
    
    //helper for sorting
    private static func evaluateDish(dish : Dish) -> Double{
        var score = 1.0
        if dish.getFoodType() == FoodType.MEAT{
            if UserData.getMVRatio() == 1{
                score = 0
            }else{
                score *= UserData.getMVRatio()
            }
        }else if dish.getFoodType() == FoodType.VEGAN {
            score *= (6.0-UserData.getMVRatio())
        }else if dish.getFoodType() == FoodType.VEGETABLE{
            if UserData.getMVRatio() == 0 { //vegan doesn't eat egg
                score *= 0
            }else{
                score *= (6.0-UserData.getMVRatio())
            }
        }
        return score;
    }
    
    
// -----------------------------------------------------------------------------------------------
    //filter dishes by diningcommon's name current time
     private static func getCurrentDish(diningCommon : Int) -> [Dish]{
        let (currentTime, currentDay) = getCurrentTimeAndDay()
        if currentDay == 1 || currentDay == 7 { // sat. sun.
            if currentTime < DCEndTime.BRUNCH {
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.BRUNCH)
            }else if currentTime < DCEndTime.DINNER{
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.DINNER)
            }
        }else{ //Mon ~ Fri
            //need more test for latenight that goes to next day
//            if currentTime < DCEndTime.LATENIGHT_TOMMORROW{
//                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.LATENIGHT)
//            }else
            if currentTime < DCEndTime.BREAKFAST {
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.BREAKFAST)
            }else if currentTime < DCEndTime.LUNCH {
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.LUNCH)
            }else if currentTime < DCEndTime.DINNER{
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.DINNER)
            }else if currentTime < DCEndTime.LATENIGHT{
                return DataFetcher.getMenu(DCName: diningCommon, MealTime: MealTime.LATENIGHT)
            }
        }
        return []
    }
    
    //get the current time
    private static func getCurrentTimeAndDay() -> (Double, Int){
        //return (12.0, 5) // for testing
        let date = Date()
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from: date)
        let minutes = calendar.component(.minute, from: date)
        let day = calendar.component(.weekday, from: date)
        return ((Double(hour) + Double(minutes) / 60.0),day)
    }
    
    struct DCEndTime{
        static let BREAKFAST = 10.0
        static let LUNCH = 14.5
        static let DINNER = 20.0
        static let LATENIGHT = 24.5
        static let LATENIGHT_TOMMORROW = 0.5
        static let BRUNCH = 14.0
    }
}
