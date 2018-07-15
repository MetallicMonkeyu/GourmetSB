//
//  UserData.swift
//  GourmetSB
//
//  Created by siyaoli on 2/26/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

final class UserData{
    private static var MVRatio = 3.0; //0~6; 0 for v and 6 for meat
    private static var PreferedDC = [DiningCommons.Carrillo,DiningCommons.DeLaGuerra,DiningCommons.Ortega,DiningCommons.Portola];
    private static var preferedSectionInCRL = ["Grill (Cafe)","Mongolian Grill","Euro","Pizza","Pasta"]
    private static var preferedSectionInDLG = ["Blue Plate Special","Taqueria (East Side)","Grill (Cafe)","Pizza","To Order","Salads/Deli (West Side)"]
    private static var preferedSectionInPTL = ["The Grill","International","Chef's Choice","The Brick"]
    private static var preferedSectionInOTG = ["Grill (Cafe)","Hot Foods","Sushi","Panini/Pizza","Specialty Bar"]
    
    public static func getPreferedSection(diningCommon : Int) -> [String]{
        switch diningCommon {
        case DiningCommons.Carrillo:
            return preferedSectionInCRL
        case DiningCommons.DeLaGuerra:
            return preferedSectionInDLG
        case DiningCommons.Portola:
            return preferedSectionInPTL
        case DiningCommons.Ortega:
            return preferedSectionInOTG
        default:
            return []
        }
    }
    
    public static func getInstance() -> UserDataObj{
        return UserDataObj(MVRatio: self.MVRatio, PreferedDC : self.PreferedDC)
    }
    
    public static func getMVRatio() -> Double{
        return MVRatio
    }
    
    public static func getPreferedDC() -> [Int]{
        return PreferedDC
    }

    public static func setMVRatio(r : Double){
        MVRatio = r
    }
    
    public static func setPreferedDC(dcs : [Int]){
        PreferedDC = dcs
    }
}

public struct UserDataObj : Codable{
    public var MVRatio = 3.0;
    public var PreferedDC = [DiningCommons.Carrillo,2,3,4];
}
