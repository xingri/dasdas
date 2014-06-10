package com.lge.spartan.data;

import com.lge.spartan.data.Customer;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
public class OrderInfo implements Serializable {

    private int orderNo;
    private String orderTime;
    private String shippingTime;
    private int status;
    private Customer cust;
    private ArrayList<OrderDetails> listOrderDetails;

    public OrderInfo(String ot, String st, int sta, Customer c, ArrayList<OrderDetails> listod) {
        setOrderTime(ot);
        setShippingTime(st);
        setStatus(sta);
        setCust(c);
        setListOrderDetails(listod);
    }

    public OrderInfo(String ot, String st, int sta) {
        setOrderTime(ot);
        setShippingTime(st);
        setStatus(sta);
    }

    public OrderInfo() {
    }

    @Override
    public String toString() {
        return "OrderInfo [orderNo=" + orderNo + ", orderTime=" + orderTime
                + ", shippingTime=" + shippingTime + ", orderStatus="
                + status + ", cust=" + cust + ", listOrderDetails="
                + listOrderDetails + "]";
    }

    /**
     * @return the orderNo
     */
    public int getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the orderTime
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime the orderTime to set
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return the shippingTime
     */
    public String getShippingTime() {
        return shippingTime;
    }

    /**
     * @param shippingTime the shippingTime to set
     */
    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the cust
     */
    public Customer getCust() {
        return cust;
    }

    /**
     * @param cust the cust to set
     */
    public void setCust(Customer cust) {
        this.cust = cust;
    }

    /**
     * @return the listOrderDetails
     */
    public ArrayList<OrderDetails> getListOrderDetails() {
        return listOrderDetails;
    }

    /**
     * @param listOrderDetails the listOrderDetails to set
     */
    public void setListOrderDetails(ArrayList<OrderDetails> listOrderDetails) {
        this.listOrderDetails = listOrderDetails;
    }
}
