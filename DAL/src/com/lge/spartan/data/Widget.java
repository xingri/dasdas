package com.lge.spartan.data;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
public class Widget implements Serializable {

    private int widgetId;
    private String name;
    private String desc;
    private int quantity;
    private int stationId;

    public Widget(String nm, String de, int quan, int stId) {
        setName(nm);
        setDesc(de);
        setQuantity(quan);
        setStationId(stId);
    }

    public Widget() {
    }

    public String toString() {
        return widgetId + " " + name + " " + quantity + " " + stationId;
    }

    /**
     * @return the widgetId
     */
    public int getWidgetId() {
        return widgetId;
    }

    /**
     * @param widgetId the widgetId to set
     */
    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the stationId
     */
    public int getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
}
