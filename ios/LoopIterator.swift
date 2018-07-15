//
//  LoopIterator.swift
//  GourmetSB
//
//  Created by siyaoli on 3/1/18.
//  Copyright © 2018 siyaoli. All rights reserved.
//

import Foundation

public class LoopIterator{
    private let stringArr : [String]
    private var cursor = -1
    private var loopNumber = 0
    
    init(stringArr : [String]) {
        self.stringArr = stringArr
    }
    
    public func next() -> String{
        if (cursor + 1) >= stringArr.count{
            loopNumber += 1
        }
        cursor = (cursor+1)%(stringArr.count)
        return stringArr[cursor]
    }
    
    public func getLoopNumber() -> Int{
        return loopNumber
    }
    
    public func getSize() -> Int{
        return stringArr.count
    }
    
    public func beforeStart(){
        cursor = -1
    }
    
}
