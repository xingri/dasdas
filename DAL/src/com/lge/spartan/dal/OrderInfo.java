package com.lge.spartan.dal;


import com.lge.spartan.dal.Customer;
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
public class OrderInfo {
  public int orderNo;
  public String orderTime;
  public String shippingTime;
  public int orderStatus;
  public Customer cust;
  public ArrayList<OrderDetails> listOrderDetails;

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
     * @return the orderStatus
     */
    public int getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
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
