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

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    
    
}
