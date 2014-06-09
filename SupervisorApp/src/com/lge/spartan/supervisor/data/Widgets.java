/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.data;

/**
 *
 * @author gina.du
 */
public class Widgets {
    int widgetId;
    String name;
    String desc;
    int quantity;
    int stationId;
        
    public Widgets (String name, String desc, int quantity, int stationId) {        
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.stationId = stationId;
    }
    
    public int getWidgetId() {
        return widgetId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public int getStationId() {
        return stationId;
    }
}
