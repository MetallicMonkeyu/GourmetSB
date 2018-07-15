//
//  MenuCell.swift
//  GourmetSB
//
//  Created by siyaoli on 2/25/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class MenuCell: UIView {
    
    var label = UILabel()
    public func showLabel(dishName : String){
        label.frame = CGRect(x: 20, y: 0, width: bounds.width, height: bounds.height)
        label.font = UIFont(name: "Apple LiGothic", size: 25)
        label.text = dishName
        addSubview(label)
    }
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
