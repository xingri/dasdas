package com.lge.spartan.dal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay.rachabattuni
 */
public class OrderDetails {
    public int widgetId;
    public String widgetName;//not madatory for the customer app to update this
    public int quantity;

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
