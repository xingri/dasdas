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
public class OrderDetails implements Serializable {

    private int widgetId;
    private String widgetName;//not madatory for the customer app to update this
    private int quantity;

    public OrderDetails(int wId, int quan) {
        setWidgetId(wId);
        setQuantity(quan);
    }

    public OrderDetails() {
    }

    @Override
    public String toString() {
        return widgetName + "   " + quantity;
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
     * @return the widgetName
     */
    public String getWidgetName() {
        return widgetName;
    }

    /**
     * @param widgetName the widgetName to set
     */
    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
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
}
