//
//  Pref2ViewController.swift
//  GourmetSB
//
//  Created by siyaoli on 2/21/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class Pref2ViewController: UIViewController {
    @IBOutlet weak var DLGButton: UIButton!
    @IBOutlet weak var OTGButton: UIButton!
    @IBOutlet weak var CarrilloButton: UIButton!
    @IBOutlet weak var PTLButton: UIButton!
    
    struct ButtonColor {
        static let NotChosen = UIColor(red: 251.0/255.0, green: 228.0/255.0, blue: 174.0/255.0, alpha: 1.0)
        static let Chosen = UIColor(red: 241.0/255.0, green: 177.0/255.0, blue: 107.0/255.0, alpha: 1.0)
    }
    
    @IBAction func changeState(_ sender: UIButton) {
        let buttonColor = sender.backgroundColor
        if(buttonColor == ButtonColor.Chosen){
            sender.backgroundColor = ButtonColor.NotChosen
        }else if(buttonColor == ButtonColor.NotChosen){
            sender.backgroundColor = ButtonColor.Chosen
            
        }
        updateUserData()
    }
    
    func updateUserData(){
        var preferedDC : [Int] = []
        if DLGButton.backgroundColor == ButtonColor.Chosen{
            preferedDC.append(DiningCommons.DeLaGuerra)
        }
        if PTLButton.backgroundColor == ButtonColor.Chosen{
            preferedDC.append(DiningCommons.Portola)
        }
        if CarrilloButton.backgroundColor == ButtonColor.Chosen{
            preferedDC.append(DiningCommons.Carrillo)
        }
        if OTGButton.backgroundColor == ButtonColor.Chosen{
            preferedDC.append(DiningCommons.Ortega)
        }
        UserData.setPreferedDC(dcs: preferedDC)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        DLGButton.backgroundColor = ButtonColor.Chosen
        PTLButton.backgroundColor = ButtonColor.Chosen
        OTGButton.backgroundColor = ButtonColor.Chosen
        CarrilloButton.backgroundColor = ButtonColor.NotChosen
        adorn(button: DLGButton)
        adorn(button: PTLButton)
        adorn(button: OTGButton)
        adorn(button: CarrilloButton)

    }
    
    private func adorn(button : UIButton){
        button.layer.cornerRadius = 5
        button.layer.borderWidth = 1
        button.layer.borderColor = UIColor.white.cgColor
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
