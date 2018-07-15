//
//  Pref3ViewController.swift
//  GourmetSB
//
//  Created by siyaoli on 2/21/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class Pref3ViewController: UIViewController {

    @IBAction func goToMainPage(_ sender: UIButton) {
        performSegue(withIdentifier: "PrefToMainSegue", sender: self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        FileManager.saveUserData(userDataObj: UserData.getInstance())
    }
}
