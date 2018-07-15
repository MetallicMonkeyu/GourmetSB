//
//  ChildView.swift
//  GourmetSB
//
//  Created by siyaoli on 2/25/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class ChildView: UIView {
    var order = 0
    var isOpen = false

    var header = UILabel()
    func addHeader(dcName : String){
        header.frame = CGRect(x: 20, y: 0, width: bounds.width, height: bounds.height*0.21)
        header.font = UIFont(name: "Bradley Hand", size: 40)
        header.text = dcName
        addSubview(header)
    }
    
    func addDishes(dishNeedDisplay : [Dish]){
        let h = CGFloat(0.79) / CGFloat(dishNeedDisplay.count)
        if dishNeedDisplay.count != 0{
            for i in 0...(dishNeedDisplay.count-1){
                let menuCell = MenuCell()
                menuCell.frame = CGRect(x: 0, y: bounds.height*0.21 + bounds.height*h*CGFloat(i), width: bounds.width, height: bounds.height*h*0.9)
                menuCell.showLabel(dishName: dishNeedDisplay[i].getDishName())
                addSubview(menuCell)
            }
        }
    }

}
