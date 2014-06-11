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
public enum OrderStatus {
    Pending, //obj.ordinal() returns 0
    Inprogress,//obj.ordinal() returns 1
    Backordered,//obj.ordinal() returns 2
    Complete,//obj.ordinal() returns 3
    All//obj.ordinal() returns 4
}
