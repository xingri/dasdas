/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.data;

import static com.lge.spartan.data.OrderStatus.values;

/**
 *
 * @author vijay.rachabattuni
 */
public enum  RobotState {
    Idle, //obj.ordinal() returns 0
    Busy,//obj.ordinal() returns 1
    Error,//obj.ordinal() returns 2
    Complete;//obj.ordinal() returns 3
    
   public static final RobotState values[] = values();
   
    @Override
   public String toString()
   {
       switch(this.ordinal())
       {
           case 0:
           return "Idle";
           case 1:
               return "Busy";
           case 2:
               return "Error";
           case 3:
               return "Complete";
       }
       return "";
   }
}
