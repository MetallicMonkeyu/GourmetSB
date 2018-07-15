//
//  PrefViewController.swift
//  GourmetSB
//
//  Created by siyaoli on 2/21/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//  
//  All Codes regarding PageViewController are modified from Jeff Burt's turtorial on spin.atomicobject.com
//  Web URL is https://spin.atomicobject.com/2015/12/23/swift-uipageviewcontroller-tutorial/


import UIKit

class PrefViewController: UIPageViewController {
    
    private(set) lazy var orderedViewControllers: [UIViewController] = {
        return [self.newPrefViewController(prefNumber: 1),
                self.newPrefViewController(prefNumber: 2),
                self.newPrefViewController(prefNumber: 3)]
    }()
    
    private func newPrefViewController(prefNumber: Int) -> UIViewController {
        return UIStoryboard(name: "Main", bundle: nil) .
            instantiateViewController(withIdentifier: "Pref\(prefNumber)ViewController")
    }


    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataSource = self;
        
        if let firstViewController = orderedViewControllers.first {
            setViewControllers([firstViewController],
                               direction: .forward,
                               animated: true,
                               completion: nil)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    

}

extension PrefViewController: UIPageViewControllerDataSource {
    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerBefore viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = orderedViewControllers.index(of: viewController) else {
            return nil
        }
        
        let previousIndex = viewControllerIndex - 1
        
        guard previousIndex >= 0 else {
            return nil
        }
        
        guard orderedViewControllers.count > previousIndex else {
            return nil
        }
        
        return orderedViewControllers[previousIndex]
    }
    
    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerAfter viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = orderedViewControllers.index(of: viewController) else {
            return nil
        }
        
        let nextIndex = viewControllerIndex + 1
        let orderedViewControllersCount = orderedViewControllers.count
        
        guard orderedViewControllersCount != nextIndex else {
            return nil
        }
        
        guard orderedViewControllersCount > nextIndex else {
            return nil
        }
        
        return orderedViewControllers[nextIndex]
    }
}
