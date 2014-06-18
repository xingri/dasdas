/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.data;

/**
 *
 * @author vijay.rachabattuni
 */
public enum StationType {
    Inventory, //obj.ordinal() returns 0
    Shipping;//obj.ordinal() returns 1
    
   public static final StationType values[] = values();
   
    @Override
   public String toString()
   {
       switch(this.ordinal())
       {
           case 0:
           return "Inventory";
           case 1:
               return "Shipping";
       }
       return "";
   }
}
