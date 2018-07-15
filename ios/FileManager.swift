//
//  FileManager.swift
//  GourmetSB
//
//  Created by siyaoli on 3/1/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

public class FileManager{
    
    public static func saveUserData(userDataObj : UserDataObj){
        do {
            let jsonEncoder = JSONEncoder()
            let jsonData = try jsonEncoder.encode(userDataObj)
            let data = NSKeyedArchiver.archivedData(withRootObject: jsonData)
            UserDefaults.standard.set(data, forKey: "GourmetUserData")
        }
        catch {
            print("Failed to write JSON data")
        }
    }
    
    public static func loadUserData() -> UserDataObj?{
        if let data = UserDefaults.standard.value(forKey: "GourmetUserData") as? Data,
            let jsonData = NSKeyedUnarchiver.unarchiveObject(with: data) as? Data{
            let jsonDecoder = JSONDecoder()
            do{
                let userData = try jsonDecoder.decode(UserDataObj.self, from: jsonData)
                return userData
            }catch{
                print("error")
            }
        }
        return nil
    }
}
