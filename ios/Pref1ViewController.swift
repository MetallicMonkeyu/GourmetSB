//
//  Pref1ViewController.swift
//  GourmetSB
//
//  Created by siyaoli on 2/21/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class Pref1ViewController: UIViewController {
    @IBOutlet weak var slider: UISlider!
    @IBOutlet weak var label: UILabel!
    var currentSliderValue : Int = 3
    
    var descriptionOfPref : [Int: String] = [
        0: "I'm a vegan",
        1: "I'm a vegetarian",
        2: "I prefer vegatable",
        3: "Balance",
        4: "I prefer meat",
        5: "I love meat",
        6: "I'm a meat-eater"
    ]
    
    @IBAction func SliderValueChanged(_ sender: UISlider) {
        currentSliderValue = (Int)((sender.value).rounded())
        label.text = descriptionOfPref[currentSliderValue]
        UserData.setMVRatio(r: Double(currentSliderValue))
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        //put everything here for now
        //load Data
        DataFetcher.fetchDataFromWeb()
        let userData = FileManager.loadUserData()
        if (userData == nil){
            print("not saved")
        }else{
            print(userData?.MVRatio ?? 3.0)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


//    // In a storyboard-based application, you will often want to do a little preparation before navigation
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        // Get the new view controller using segue.destinationViewController.
//        // Pass the selected object to the new view controller.
//
//    }
}
