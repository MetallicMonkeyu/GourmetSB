//
//  DiningCommons.swift
//  GourmetSB
//
//  Created by siyaoli on 2/24/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

final class DiningCommons{
    static let NULL = 0;
    static let Carrillo = 1;
    static let DeLaGuerra = 2;
    static let Ortega = 3;
    static let Portola = 4;
    
    static func changeNumberToString(i : Int) -> String{
        switch i
        {
        case Carrillo:
            return "Carrillo"
        case DeLaGuerra:
            return "De La Guerra"
        case Ortega:
            return "Ortega"
        case Portola:
            return "Portola"
        default:
            return "NULL"
        }
    }
}
