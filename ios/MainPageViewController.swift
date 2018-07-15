//
//  MainPageViewController.swift
//  GourmetSB
//
//  Created by siyaoli on 2/21/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import UIKit

class MainPageViewController: UIViewController {
    
    @IBOutlet weak var firstContainer: ChildView!
    @IBOutlet weak var secondContainer: ChildView!
    @IBOutlet weak var thirdContainer: ChildView!
    @IBOutlet weak var fourthContainer: ChildView!
    
    //handle gesture
    @IBAction func handlePan(recognizer:UIPanGestureRecognizer) {
        let childView = recognizer.view as! ChildView
        if recognizer.state == .changed{
            let translation = recognizer.translation(in: self.view)
            let childViewY = childView.frame.minY + translation.y
            if !checkIfOutOfConstrain(childView: childView, childViewY: childViewY){
                moveChildView(childView: childView, translationY: translation.y)
            }
            recognizer.setTranslation(CGPoint.zero, in: self.view)
        }else if recognizer.state == .ended{
            finishPanByAnimation(childView: childView)
        }else if recognizer.state == .cancelled{
            print("cancelled")
        }
    }
    
    func finishPanByAnimation(childView : ChildView){
        if childView.isOpen {
            UIView.animate(withDuration: 0.5, animations: {
                switch childView.order{
                case 2:
                    self.fourthContainer.frame = self.childViewFrames!.fourthClose
                    self.thirdContainer.frame = self.childViewFrames!.thirdClose
                    self.secondContainer.frame = self.childViewFrames!.secondClose
                case 3:
                    self.fourthContainer.frame = self.childViewFrames!.fourthClose
                    self.thirdContainer.frame = self.childViewFrames!.thirdClose
                case 4:
                    self.fourthContainer.frame = self.childViewFrames!.fourthClose
                default:
                    break
                }
            }, completion: { (true) in
                switch childView.order{
                case 2:
                    self.fourthContainer.isOpen = false
                    self.thirdContainer.isOpen = false
                    self.secondContainer.isOpen = false
                case 3:
                    self.fourthContainer.isOpen = false
                    self.thirdContainer.isOpen = false
                case 4:
                    self.fourthContainer.isOpen = false
                default:
                    break
                }
            })
        }else{
            UIView.animate(withDuration: 0.5, animations: {
                switch childView.order{
                case 4:
                    self.secondContainer.frame = self.childViewFrames!.secondOpen
                    self.thirdContainer.frame = self.childViewFrames!.thirdOpen
                    self.fourthContainer.frame = self.childViewFrames!.fourthOpen
                case 3:
                    self.secondContainer.frame = self.childViewFrames!.secondOpen
                    self.thirdContainer.frame = self.childViewFrames!.thirdOpen
                case 2:
                    self.secondContainer.frame = self.childViewFrames!.secondOpen
                default:
                    break
                }
            }, completion: { (true) in
                switch childView.order{
                case 4:
                    self.secondContainer.isOpen = true
                    self.thirdContainer.isOpen = true
                    self.fourthContainer.isOpen = true
                case 3:
                    self.secondContainer.isOpen = true
                    self.thirdContainer.isOpen = true
                case 2:
                    self.secondContainer.isOpen = true
                default:
                    break
                }
            })
        }
    }
    
    func moveChildView(childView : ChildView, translationY : CGFloat){
        childView.frame = CGRect(x: 0, y: childView.frame.minY + translationY, width: childView.frame.width, height: childView.frame.height)
        let titleHeight = self.view.frame.height * 0.13
        if childView.order == 2{
            if childView.frame.minY + titleHeight > thirdContainer.frame.minY {
                moveChildView(childView: thirdContainer, translationY: translationY)
            }
        }else if childView.order == 3{
            if childView.frame.minY + titleHeight > fourthContainer.frame.minY {
                //cancel recursion to remove bug
                //moveChildView(childView: fourthContainer, translationY: translationY)
                fourthContainer.frame = CGRect(x: 0, y: fourthContainer.frame.minY + translationY, width: childView.frame.width, height: childView.frame.height)
            }
            if childView.frame.minY < secondContainer.frame.minY + titleHeight {
                //moveChildView(childView: secondContainer, translationY: translationY)
                secondContainer.frame = CGRect(x: 0, y: secondContainer.frame.minY + translationY, width: childView.frame.width, height: childView.frame.height)
            }

        }else if childView.order == 4{
            if childView.frame.minY < thirdContainer.frame.minY + titleHeight {
                moveChildView(childView: thirdContainer, translationY: translationY)
            }
        }
    }
    
    func checkIfOutOfConstrain(childView : ChildView, childViewY : CGFloat) -> Bool{
        if childView.order == 2 && childViewY > self.view.frame.height*0.13 && childViewY < self.view.frame.height*0.61 {
            return false
        }else if childView.order == 3 && childViewY > self.view.frame.height*0.26+1 && childViewY < self.view.frame.height*0.74-1 {
            return false
        }else if childView.order == 4 && childViewY > self.view.frame.height*0.39 && childViewY < self.view.frame.height*0.87 {
            return false
        }
        return true;
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setChildViewFrames()
        loadFourChildViews()

//        DataManager.getConciseMenu(diningCommon: DiningCommons.DeLaGuerra)
        //test
//        let menu = DataFetcher.getMenu(DCName: DiningCommons.Portola, MealTime: MealTime.LUNCH )
//        for m in menu{
//            print(m.getDishName())
//            print(m.getFoodType())
//        }
        //DataFetcher.getMenu(DCName: DiningCommons.Ortega, MealTime: MealTime.DINNER )
//        DataFetcher.getMenu(DCName: DiningCommons.DeLaGuerra, MealTime: MealTime.LUNCH )

    }

    //setup childviews for first time
    func loadFourChildViews(){
        loadOrder()
        let sortedDC = DataManager.sortDC()
        //height = 0.61, title height = 0.13
        firstContainer.frame = CGRect(x: 0, y: self.view.frame.height*0, width: self.view.frame.width, height: self.view.frame.height*0.61)
        firstContainer.addHeader(dcName: DiningCommons.changeNumberToString(i: sortedDC[0]))
        firstContainer.addDishes(dishNeedDisplay: DataManager.getConciseMenu(diningCommon: sortedDC[0]))
        secondContainer.frame = childViewFrames!.secondClose
        secondContainer.addHeader(dcName: DiningCommons.changeNumberToString(i: sortedDC[1]))
        secondContainer.addDishes(dishNeedDisplay: DataManager.getConciseMenu(diningCommon: sortedDC[1]))
        thirdContainer.frame = childViewFrames!.thirdClose
        thirdContainer.addHeader(dcName: DiningCommons.changeNumberToString(i: sortedDC[2]))
        thirdContainer.addDishes(dishNeedDisplay: DataManager.getConciseMenu(diningCommon: sortedDC[2]))
        fourthContainer.frame = childViewFrames!.fourthClose
        fourthContainer.addHeader(dcName: DiningCommons.changeNumberToString(i: sortedDC[3]))
        fourthContainer.addDishes(dishNeedDisplay: DataManager.getConciseMenu(diningCommon: sortedDC[3]))

        self.view.addSubview(firstContainer)
        self.view.addSubview(secondContainer)
        self.view.addSubview(thirdContainer)
        self.view.addSubview(fourthContainer)
    }
    
    func loadOrder(){
        firstContainer.order = 1
        firstContainer.isOpen = true
        secondContainer.order = 2
        thirdContainer.order = 3
        fourthContainer.order = 4
    }
    
    //record six possible frames
    struct ChildViewFrames{
        let secondClose : CGRect
        let thirdClose : CGRect
        let fourthClose : CGRect
        let secondOpen : CGRect
        let thirdOpen : CGRect
        let fourthOpen : CGRect
    }
    
    var childViewFrames : ChildViewFrames? = nil
    
    func setChildViewFrames() {
        childViewFrames = ChildViewFrames(
            secondClose: CGRect(x: 0, y: self.view.frame.height*0.61, width: self.view.frame.width, height: self.view.frame.height*0.61),
            thirdClose: CGRect(x: 0, y: self.view.frame.height*0.74, width: self.view.frame.width, height: self.view.frame.height*0.61),
            fourthClose: CGRect(x: 0, y: self.view.frame.height*0.87, width: self.view.frame.width, height: self.view.frame.height*0.61),
            secondOpen: CGRect(x: 0, y: self.view.frame.height*0.13, width: self.view.frame.width, height: self.view.frame.height*0.61),
            thirdOpen: CGRect(x: 0, y: self.view.frame.height*0.26, width: self.view.frame.width, height: self.view.frame.height*0.61),
            fourthOpen: CGRect(x: 0, y: self.view.frame.height*0.39, width: self.view.frame.width, height: self.view.frame.height*0.61))
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
}
