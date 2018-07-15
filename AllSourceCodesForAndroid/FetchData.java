package com.example.shihualu.gourmettest.Module;

/**
 * Created by Paul on 2/5/2018.
 */
import android.widget.ScrollView;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Calendar;

public class FetchData{
    private Document doc=null;
    private Elements menu, dishList, dishes;
    private Element data;
    private ArrayList<Dish> CRL = new ArrayList<Dish>();
    private ArrayList<Dish> OTG = new ArrayList<Dish>();
    private ArrayList<Dish> DLG = new ArrayList<Dish>();
    private ArrayList<Dish> PTL = new ArrayList<Dish>();
    List<String> CRLmealtime;
    List<String> OTGmealtime;
    List<String> DLGmealtime;
    List<String> PTLmealtime;
    MealTimeInterval CRLopentime;
    MealTimeInterval OTGopentime;
    MealTimeInterval DLGopentime;
    MealTimeInterval PTLopentime;
    Calendar calendar;

    public FetchData(){
        calendar = Calendar.getInstance();
    }

    public void getData() {
        try {
            doc = Jsoup.connect("https://appl.housing.ucsb.edu/menu/day/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get menus for all DCs
        menu = doc.select("div#menu-row");
        if (menu != null) {
            //Get time of each meal
            CRLmealtime = menu.first().child(0).child(1).select("h5").eachText();
            DLGmealtime = menu.first().child(1).child(1).select("h5").eachText();
            OTGmealtime = menu.first().child(2).child(1).select("h5").eachText();
            PTLmealtime = menu.first().child(3).child(1).select("h5").eachText();


            //Get CRL menu
            for (int i = 0; i < menu.first().child(0).child(1).children().size(); i++) {
                int mealTag = 7;
                if (CRLmealtime.get(i).equals("Breakfast")) {
                    mealTag = 0;
                } else if (CRLmealtime.get(i).equals( "Lunch")) {
                    mealTag = 1;
                } else if (CRLmealtime.get(i).equals("Dinner")) {
                    mealTag = 2;
                } else if (CRLmealtime.get(i).equals("Brunch")) {
                    mealTag = 3;
                } else if (CRLmealtime.get(i).equals("Late Night")) {
                    mealTag = 4;
                } else if (CRLmealtime.get(i).equals("Bright Meal")) {
                    mealTag = 5;
                }else
                    continue;
                data = menu.first().child(0).child(1).child(i);
                dishList = data.select("dl");
                for (int j = 0; j < dishList.size(); j++) {
                    dishes = dishList.get(j).select("dd");
                    for (int k = 0; k < dishes.size(); k++) {
                        Dish thisOne = new Dish(dishes.get(k).text(), 2, dishList.get(j).child(0).text(), mealTag);
                        String dishtxt = dishes.get(k).text();
                        //Check if the dish is with nuts
                        if (dishtxt.contains(" (w/nuts)")) {
                            thisOne.setNuts(true);
                            dishtxt = dishtxt.replace(" (w/nuts)", "");
                        }
                        //Assign corresponding food type
                        if (dishtxt.contains(" (vgn)")) {
                            thisOne.setFoodType(FoodType.VEGAN);
                            thisOne.setDishName(dishtxt.replace(" (vgn)", ""));
                        } else if (dishtxt.contains(" (v)")) {
                            thisOne.setFoodType(FoodType.VEGETABLE);
                            thisOne.setDishName(dishtxt.replace(" (v)", ""));
                        } else {
                            thisOne.setFoodType(FoodType.MEAT);
                        }

                        CRL.add(thisOne);
                    }
                }
            }




            //Get DLG menu
            for (int i = 0; i < menu.first().child(1).child(1).children().size(); i++) {
                int mealTag = 7;
                if (DLGmealtime.get(i).equals("Breakfast")) {
                    mealTag = 0;
                } else if (DLGmealtime.get(i).equals("Lunch")) {
                    mealTag = 1;
                } else if (DLGmealtime.get(i).equals("Dinner")) {
                    mealTag = 2;
                } else if (DLGmealtime.get(i).equals("Brunch")) {
                    mealTag = 3;
                } else if (DLGmealtime.get(i).equals("Late Night")) {
                    mealTag = 4;
                } else if (DLGmealtime.get(i).equals("Bright Meal")) {
                    mealTag = 5;
                } else
                    continue;
                data = menu.first().child(1).child(1).child(i);
                dishList = data.select("dl");
                for (int j = 0; j < dishList.size(); j++) {
                    dishes = dishList.get(j).select("dd");
                    for (int k = 0; k < dishes.size(); k++) {
                        Dish thisOne = new Dish(dishes.get(k).text(), 3, dishList.get(j).child(0).text(), mealTag);
                        String dishtxt = dishes.get(k).text();
                        //Check if the dish is with nuts
                        if (dishtxt.contains(" (w/nuts)")) {
                            thisOne.setNuts(true);
                            dishtxt = dishtxt.replace(" (w/nuts)", "");
                        }
                        //Assign corresponding food type
                        if (dishtxt.contains(" (vgn)")) {
                            thisOne.setFoodType(FoodType.VEGAN);
                            thisOne.setDishName(dishtxt.replace(" (vgn)", ""));
                        } else if (dishtxt.contains(" (v)")) {
                            thisOne.setFoodType(FoodType.VEGETABLE);
                            thisOne.setDishName(dishtxt.replace(" (v)", ""));
                        } else {
                            thisOne.setFoodType(FoodType.MEAT);
                        }

                        DLG.add(thisOne);

                    }

                }
            }
            //Get OTG menu
            for (int i = 0; i < menu.first().child(2).child(1).children().size(); i++) {
                int mealTag = 7;
                if (OTGmealtime.get(i).equals("Breakfast")) {
                    mealTag = 0;
                } else if (OTGmealtime.get(i).equals("Lunch")) {
                    mealTag = 1;
                } else if (OTGmealtime.get(i).equals("Dinner")) {
                    mealTag = 2;
                } else if (OTGmealtime.get(i).equals("Brunch")) {
                    mealTag = 3;
                } else if (OTGmealtime.get(i).equals("Late Night")) {
                    mealTag = 4;
                } else if (OTGmealtime.get(i).equals("Bright Meal")) {
                    mealTag = 5;
                }else
                    continue;
                data = menu.first().child(2).child(1).child(i);
                dishList = data.select("dl");
                for (int j = 0; j < dishList.size(); j++) {
                    dishes = dishList.get(j).select("dd");
                    for (int k = 0; k < dishes.size(); k++) {
                        Dish thisOne = new Dish(dishes.get(k).text(), 1, dishList.get(j).child(0).text(), mealTag);
                        String dishtxt = dishes.get(k).text();
                        //Check if the dish is with nuts
                        if (dishtxt.contains(" (w/nuts)")) {
                            thisOne.setNuts(true);
                            dishtxt = dishtxt.replace(" (w/nuts)", "");
                        }
                        //Assign corresponding food type
                        if (dishtxt.contains(" (vgn)")) {
                            thisOne.setFoodType(FoodType.VEGAN);
                            thisOne.setDishName(dishtxt.replace(" (vgn)", ""));
                        } else if (dishtxt.contains(" (v)")) {
                            thisOne.setFoodType(FoodType.VEGETABLE);
                            thisOne.setDishName(dishtxt.replace(" (v)", ""));
                        } else {
                            thisOne.setFoodType(FoodType.MEAT);
                        }

                        OTG.add(thisOne);

                    }

                }
            }

            //Get PTL menu
            for (int i = 0; i < menu.first().child(3).child(1).children().size(); i++) {
                //Set meal time
                int mealTag = 7;
                if (PTLmealtime.get(i).equals("Breakfast")) {
                    mealTag = 0;
                } else if (PTLmealtime.get(i).equals("Lunch")) {
                    mealTag = 1;
                } else if (PTLmealtime.get(i).equals("Dinner")) {
                    mealTag = 2;
                } else if (PTLmealtime.get(i).equals("Brunch")) {
                    mealTag = 3;
                } else if (PTLmealtime.get(i).equals("Late Night")) {
                    mealTag = 4;
                } else if (PTLmealtime.get(i).equals("Bright Meal")) {
                    mealTag = 5;
                }else
                    continue;
                data = menu.first().child(3).child(1).child(i);
                dishList = data.select("dl");
                for (int j = 0; j < dishList.size(); j++) {
                    dishes = dishList.get(j).select("dd");
                    for (int k = 0; k < dishes.size(); k++) {
                        Dish thisOne = new Dish(dishes.get(k).text(), 4, dishList.get(j).child(0).text(), mealTag);
                        String dishtxt = dishes.get(k).text();
                        //Check if the dish is with nuts
                        if (dishtxt.contains(" (w/nuts)")) {
                            thisOne.setNuts(true);
                            dishtxt = dishtxt.replace(" (w/nuts)", "");
                        }
                        //Assign corresponding food type
                        if (dishtxt.contains(" (vgn)")) {
                            thisOne.setFoodType(FoodType.VEGAN);
                            thisOne.setDishName(dishtxt.replace(" (vgn)", ""));
                        } else if (dishtxt.contains(" (v)")) {
                            thisOne.setFoodType(FoodType.VEGETABLE);
                            thisOne.setDishName(dishtxt.replace(" (v)", ""));
                        } else {
                            thisOne.setFoodType(FoodType.MEAT);
                        }

                        PTL.add(thisOne);
                    }

                }
            }
        }


    }
    public ArrayList<Dish> getMenu(int index) {
        if(index == DiningCommons.CARRILLO){ return CRL;}
        else if(index == DiningCommons.DLG){return DLG;}
        else if(index == DiningCommons.OTG){return OTG;}
        else if(index == DiningCommons.PTL){return PTL;}
        else{return null;}

    }

    public List<String> getMealtime(int index) {
        if(index == DiningCommons.CARRILLO){return CRLmealtime;}
        else if(index == DiningCommons.DLG){return DLGmealtime;}
        else if(index == DiningCommons.OTG){return OTGmealtime;}
        else if(index == DiningCommons.PTL){return PTLmealtime;}
        else{return null;}
    }

    public ArrayList<MealTimeInterval> getMealTimeInterval(int DC){
        ArrayList<MealTimeInterval> mti = new ArrayList<MealTimeInterval>();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(DC == DiningCommons.CARRILLO){
            if(dayOfWeek==1||dayOfWeek==7){//saturday/sunday
                mti.add(new MealTimeInterval(DC,MealTime.BRUNCH,10.5,true, 2, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }else{
                mti.add(new MealTimeInterval(DC,MealTime.BREAKFAST,7.25,true, 10, true ));
                mti.add(new MealTimeInterval(DC,MealTime.LUNCH,11,true, 2.5, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }
        }else if(DC == DiningCommons.DLG){
            if(dayOfWeek==1||dayOfWeek==7){//saturday/sunday
                mti.add(new MealTimeInterval(DC,MealTime.BRUNCH,10.5,true, 2, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }else if(dayOfWeek==6){//Friday
                mti.add(new MealTimeInterval(DC,MealTime.LUNCH,11,true, 2.5, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }else{
                mti.add(new MealTimeInterval(DC,MealTime.LUNCH,11,true, 2.5, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
                mti.add(new MealTimeInterval(DC,MealTime.LATENIGHT,9,false,12.5,true));
            }
        }else if(DC == DiningCommons.PTL){
            if(dayOfWeek==1||dayOfWeek==7){//saturday/sunday
                mti.add(new MealTimeInterval(DC,MealTime.BRUNCH,10.5,true, 2, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }else{
                mti.add(new MealTimeInterval(DC,MealTime.BREAKFAST,7.25,true, 10, true ));
                mti.add(new MealTimeInterval(DC,MealTime.LUNCH,11,true, 2.5, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }
        }else if(DC == DiningCommons.OTG){
            if(dayOfWeek==1||dayOfWeek==7){//saturday/sunday
            }else{
                mti.add(new MealTimeInterval(DC,MealTime.BREAKFAST,7.25,true, 10, true ));
                mti.add(new MealTimeInterval(DC,MealTime.LUNCH,11,true, 2.5, false ));
                mti.add(new MealTimeInterval(DC,MealTime.DINNER, 5,false,8,false));
            }
        }else{

        }

        return mti;
    }


}