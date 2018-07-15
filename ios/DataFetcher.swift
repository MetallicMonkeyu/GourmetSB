//
//  DataFetcher.swift
//  GourmetSB
//
//  Created by siyaoli on 2/22/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation
import Alamofire
import SwiftSoup

final class DataFetcher{
    static let webURL = "https://appl.housing.ucsb.edu/menu/day/";
    static var allElements = [Int : Elements]()
    
    //get html
    public static func fetchDataFromWeb(){
        Alamofire.request(webURL).responseString { response in
            if let html = response.result.value{
                self.parseHTML(html : html)
            }else{
                print("No data are retrieved")
                self.parseHTML(html: "")
            }
        }
    }
    
    //record html, elements will be empty if no corresponding menu
    private static func parseHTML(html:String) {
        do {
            let doc : Document = try SwiftSoup.parse(html)
            let crlElements = try doc.select("#Carrillo-body")
            let dlgElements = try doc.select("#DeLaGuerra-body")
            let otgElements = try doc.select("#Ortega-body")
            let ptlElements = try doc.select("#Portola-body")
            allElements[DiningCommons.Carrillo] = crlElements
            allElements[DiningCommons.DeLaGuerra] = dlgElements
            allElements[DiningCommons.Ortega] = otgElements
            allElements[DiningCommons.Portola] = ptlElements
        } catch Exception.Error( _, let message) {
            print(message)
        } catch {
            print("error")
        }
    }
    
    //return [] if closed during such meal time
    public static func getMenu(DCName : Int, MealTime : String) -> [Dish]{
        var menu : [Dish] = []
        do {
            //Danger!!!! Use DCName as Index
            let dcElements = allElements[DCName]?.get(0).children()
            if dcElements != nil{
                for meal in dcElements!{
                    //check mealtime
                    let mealName : String = try (meal.select("h5")).text()
                    if mealName == MealTime{
                        //check section
                        let sections : Elements = try meal.select("dl")
                        for section in sections{
                            let sectionName : String? = try section.select("dt").first()?.text()
                            //get all dish
                            let dishes = try section.select("dd")
                            for dish in dishes{
                                var dishName : String? = try dish.text()
                                let foodType = checkFoodType(dishName: dishName!)
                                dishName = deleteSuffix(dishName: dishName!)
                                menu.append(Dish(dishName: dishName!, fromDC: DCName, section: sectionName!, mealTime: MealTime, foodType : foodType))
                            }
                        }
                    }
                }
            }
        } catch Exception.Error( _, let message) {
            print(message)
        } catch {
            print("error")
        }
        return menu;
    }
    
    //check the food type by suffix
    private static func checkFoodType(dishName : String) -> Int{
        if let _ = dishName.range(of: "(vgn)"){
            return FoodType.VEGAN
        }else if let _ = dishName.range(of: "(v)"){
            return FoodType.VEGETABLE
        }else {
            return FoodType.MEAT
        }
    }
    
    //delete all suffix
    private static func deleteSuffix(dishName : String) -> String{
        if let range = dishName.range(of: "(") {
            return dishName.substring(to: range.lowerBound)
        }
        else {
            return dishName
        }
    }
}
