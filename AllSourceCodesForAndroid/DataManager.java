package com.example.shihualu.gourmettest.Controller;

import android.content.Context;
import android.util.Log;

import com.example.shihualu.gourmettest.Module.DiningCommons;
import com.example.shihualu.gourmettest.Module.Dish;
import com.example.shihualu.gourmettest.Module.FetchData;
import com.example.shihualu.gourmettest.Module.FileManager;
import com.example.shihualu.gourmettest.Module.FoodType;
import com.example.shihualu.gourmettest.Module.MealTime;
import com.example.shihualu.gourmettest.Module.MealTimeInterval;
import com.example.shihualu.gourmettest.Module.UserData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DataManager {
    private ArrayList<Integer> sortResult;
    private FetchData df;
    Calendar calendar;


    public DataManager() {
        df = new FetchData();
        calendar = Calendar.getInstance();
    }

    public FetchData getDataFetch(){
        return df;
    }

    public ArrayList<Integer> sortDC(UserData u){
        ArrayList<Integer> sortedDC = new ArrayList();
        ArrayList<Double> scoreList = new ArrayList();
        double MVR =  u.getMVRatio();
        //double MVR = 1/6;
        int Location[] =  u.getLocationPref();
        //int Location[] = {1,2,3,4};

        for(int i = 0; i < Location.length; i++) {
            if (Location[i] != 0) {
                ArrayList<Dish> toBeSorted = filterByOpenTime((i + 1), df.getMenu(i + 1));
                if (toBeSorted.isEmpty()) {
                    scoreList.add(0.0);
                    sortedDC.add(i + 1);
                } else {
                    Double scoreSum = 0.0;
                    for (int j = 0; j < toBeSorted.size(); j++) {
                        Dish thisOne = toBeSorted.get(j);
                        if (thisOne.getFoodType() == FoodType.VEGAN || thisOne.getFoodType() == FoodType.VEGETABLE) {
                            scoreSum = thisOne.getScore() * (1 - MVR) + scoreSum;
                        } else {
                            scoreSum = thisOne.getScore() * MVR + scoreSum;
                        }
                    }
                    scoreList.add(scoreSum);
                    sortedDC.add(i + 1);

                }
            }else{
                scoreList.add(0.0);
                sortedDC.add(i+1);
            }
        }

        //Sorting DC based on scoreSum
        int j, k;
        Integer min_idx;
        for(j = 0; j < scoreList.size()-1; j++){
            min_idx = j;
            for(k = j+1; k < scoreList.size(); k++) {
                if (scoreList.get(k) < scoreList.get(min_idx)) {
                    min_idx = k;
                }
            }
            int temp1 = sortedDC.get(min_idx);
            double temp2 = scoreList.get(min_idx);
            scoreList.set(min_idx, scoreList.get(j));
            sortedDC.set(min_idx, sortedDC.get(j));
            scoreList.set(j, temp2);
            sortedDC.set(j, temp1);

        }


        //Log.d("Debug", sortedDC.size()+"sss");

        Collections.reverse(sortedDC);

        return sortedDC;
    }


    public ArrayList<Dish> getConciseMenu(int number, int DC, UserData u) {
        //Get the Meat/Ratio value of the user and create conciseMenu array for return
        double MVR = u.getMVRatio();
        //double MVR = 1;
        ArrayList<Dish> conciseMenu = new ArrayList<Dish>();

        //Carrilo case
        if (DC == DiningCommons.CARRILLO) {
            ArrayList<Dish> menu = filterByOpenTime(DiningCommons.CARRILLO, df.getMenu(DiningCommons.CARRILLO));
            if(menu.size()!= 0){
                //Breakfast case
                if (menu.get(0).getMealTime() == MealTime.BREAKFAST) {
                    //If the use is not a total vegetarian, we show the only meat meal during breakfast
                    if (MVR != 0) {
                        number--;
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.MEAT)
                                conciseMenu.add(dish);
                            else if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                        return conciseMenu;
                    } else {
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                    }
                }//Lunch Case
                else if (menu.get(0).getMealTime() == MealTime.LUNCH) {
                    int meatNum = (int) (MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && (dish.getFoodType() == FoodType.MEAT)) {
                            if (dish.getSection().equals("Mongolian Grill") || dish.getSection().equals("Euro") || dish.getSection().equals("Pizza") || dish.getSection().equals("Grill (Cafe)")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if (((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE)) || ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGAN))) {
                            if (dish.getSection().equals("Mongolian Grill") || dish.getSection().equals("Euro") || dish.getSection().equals("Pizza") || dish.getSection().equals("Pasta")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Dinner case
                else if (menu.get(0).getMealTime() == MealTime.DINNER) {
                    int meatNum = (int) (MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && (dish.getFoodType() == FoodType.MEAT)) {
                            if (dish.getSection().equals("Mongolian Grill") || dish.getSection().equals("Euro") || dish.getSection().equals("Pizza") || dish.getSection().equals("Grill (Cafe)")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Mongolian Grill") || dish.getSection().equals("Euro") || dish.getSection().equals("Pizza") || dish.getSection().equals("Pasta")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Brunch case
                else if (menu.get(0).getMealTime() == MealTime.BRUNCH) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Bakery") || dish.getSection().equals("Euro") || dish.getSection().equals("Grill (Cafe)")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Bakery") || dish.getSection().equals("Euro") || dish.getSection().equals("Grill (Cafe)")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }
            }else
                return menu;
        }


        //DLG case
        else if (DC == DiningCommons.DLG) {
            ArrayList<Dish> menu = filterByOpenTime(DiningCommons.DLG, df.getMenu(DiningCommons.DLG));
            if(menu.size()!= 0){
                //Breakfast case
                if (menu.get(0).getMealTime() == MealTime.BREAKFAST) {
                    //If the use is not a total vegetarian, we show the only meat meal during breakfast
                    if (MVR != 0) {
                        number--;
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.MEAT)
                                conciseMenu.add(dish);
                            else if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                        return conciseMenu;
                    } else {
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                    }
                }//Lunch Case
                else if (menu.get(0).getMealTime() == MealTime.LUNCH) {
                    int meatNum = (int) (MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && (dish.getFoodType() == FoodType.MEAT)) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Taqueria (East Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if (((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE)) || ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGAN))) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Taqueria (East Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Dinner case
                else if (menu.get(0).getMealTime() == MealTime.DINNER) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Taqueria (East Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Taqueria (East Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Brunch case
                else if (menu.get(0).getMealTime() == MealTime.BRUNCH) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Salads/Deli (West Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Blue Plate Special") || dish.getSection().equals("Salads/Deli (West Side)") || dish.getSection().equals("Pizza") || dish.getSection().equals("To Order")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Late Night Case
                else if (menu.get(0).getMealTime() == MealTime.LATENIGHT) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("Bakery")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("Bakery")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }
            }else
                return menu;
        }
        //  OTG case
        else if (DC == DiningCommons.OTG) {
            ArrayList<Dish> menu = filterByOpenTime(DiningCommons.OTG, df.getMenu(DiningCommons.OTG));
            if(menu.size()!= 0) {
                //Breakfast case
                if (menu.get(0).getMealTime() == MealTime.BREAKFAST) {
                    //If the use is not a total vegetarian, we show the only meat meal during breakfast
                    if (MVR != 0) {
                        number--;
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.MEAT)
                                conciseMenu.add(dish);
                            else if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                        return conciseMenu;
                    } else {
                        for (Dish dish : menu) {
                            if (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN) {
                                conciseMenu.add(dish);
                                number--;
                            }
                            if (number == 0)
                                break;
                        }
                    }
                }//Lunch Case
                else if (menu.get(0).getMealTime() == MealTime.LUNCH) {
                    int meatNum = (int) (MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && (dish.getFoodType() == FoodType.MEAT)) {
                            if (dish.getSection().equals("Hot Foods") || dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("Panini/Pizza")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if (((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE) || ((vegeNum > 0) && dish.getFoodType() == FoodType.VEGAN))) {
                            if (dish.getSection().equals("Hot Foods") || dish.getSection().equals("Grill (Cafe)") || dish.getSection().equals("Panini/Pizza")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Dinner case
                else if (menu.get(0).getMealTime() == MealTime.DINNER) {
                    int meatNum = (int)(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Hot Foods") || dish.getSection().equals("Specialty Bar") || dish.getSection().equals("Sushi")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        }else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Hot Foods") || dish.getSection().equals("Specialty Bar") || dish.getSection().equals("Sushi")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }
            }else
                return menu;
        }
        else if (DC == DiningCommons.PTL) {
            ArrayList<Dish> menu = filterByOpenTime(DiningCommons.PTL, df.getMenu(DiningCommons.PTL));
            if (menu.size() != 0) {
                // Log.d("Debug", menu.size() + "");
                if (menu.get(0).getMealTime() == MealTime.BREAKFAST) {
                    //Breakfast case
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("The Grill")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("The Grill")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Lunch Case
                else if (menu.get(0).getMealTime() == MealTime.LUNCH) {
                    int meatNum = (int) (MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && (dish.getFoodType() == FoodType.MEAT)) {
                            if (dish.getSection().equals("International") || dish.getSection().equals("The Grill") || dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Brick")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if (((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE)) || ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGAN))) {
                            if (dish.getSection().equals("International") || dish.getSection().equals("The Grill") || dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Brick")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Dinner case
                else if (menu.get(0).getMealTime() == MealTime.DINNER) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("International") || dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Brick")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("International") || dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Brick")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }//Brunch case
                else if (menu.get(0).getMealTime() == MealTime.BRUNCH) {
                    int meatNum = (int) Math.floor(MVR * number);
                    int vegeNum = number - meatNum;
                    for (Dish dish : menu) {
                        if ((meatNum > 0) && dish.getFoodType() == FoodType.MEAT) {
                            if (dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Grill") || dish.getSection().equals("The Brick)")) {
                                conciseMenu.add(dish);
                                meatNum--;
                            }
                        } else if ((vegeNum > 0) && (dish.getFoodType() == FoodType.VEGETABLE || dish.getFoodType() == FoodType.VEGAN)) {
                            if (dish.getSection().equals("Chef's Choice") || dish.getSection().equals("The Grill") || dish.getSection().equals("The Brick)")) {
                                conciseMenu.add(dish);
                                vegeNum--;
                            }
                        }
                        if ((vegeNum == 0) && (meatNum == 0))
                            break;
                    }
                    return conciseMenu;
                }
            } else
                return menu;
        }
        return conciseMenu;
    }


    public static ArrayList<Dish> filterByMealTime(int mealTime, ArrayList<Dish> original) {
        ArrayList<Dish> newArr = new ArrayList<Dish>();
        for (Dish d : original) {
            if (d.getMealTime() == mealTime) {
                newArr.add(d);
            }
        }
        return newArr;
    }

    public ArrayList<Dish> filterByOpenTime(int DC, ArrayList<Dish> original) {
        ArrayList<Dish> newArr = new ArrayList<Dish>();
        double currentT = calendar.get(Calendar.HOUR_OF_DAY)+calendar.get(Calendar.MINUTE)/60.0;
        ArrayList<MealTimeInterval> mtis = df.getMealTimeInterval(DC);
        if(mtis.size()!=0) {
            int mealTimeNeedDisplay = -1;
            for (int i = mtis.size() - 1; i >= 0; i--) {
                //menu at mti.dc mti.mealTime should be displayed
                if (mtis.get(i).getEnd() > currentT) {
                    mealTimeNeedDisplay = mtis.get(i).getMealTime();
                }
            }
            if(mealTimeNeedDisplay!=-1){
                for(Dish d : original){
                    if(d.getFromDC() == DC && d.getMealTime() == mealTimeNeedDisplay)
                        newArr.add(d);
                }
            }
        }
        //Log.d("Debug", newArr.size()+"newArr");
        return newArr;
    }

}