//
//  ViewController.swift
//  Welcometest
//
//  Created by ShengjiaYu on 2018/3/10.
//  Copyright © 2018 ShengjiaYu. All rights reserved.
//

import UIKit

class SBViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        launchAnimation()
        sleep(2)
    }
    private func launchAnimation() {
        //get launch screen, change viewcontroller storyboard ID to "launch"
        let vc = UIStoryboard(name: "LaunchScreen", bundle: nil)
            .instantiateViewController(withIdentifier: "launch")
        let launchview = vc.view!
        let delegate = UIApplication.shared.delegate
        delegate?.window!!.addSubview(launchview)
        //self.view.addSubview(launchview)
        
        //Transforming&transparent animation
        UIView.animate(withDuration: 2, delay: 1.5, options: .beginFromCurrentState,
                       animations: {
                        launchview.alpha = 0.0
                        let transform = CATransform3DScale(CATransform3DIdentity, 1.5, 1.5, 1.0)
                        launchview.layer.transform = transform
        }) { (finished) in
            launchview.removeFromSuperview()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

