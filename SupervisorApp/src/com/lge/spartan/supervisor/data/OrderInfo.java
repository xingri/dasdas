/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.data;

import java.util.ArrayList;
import com.lge.spartan.supervisor.data.OrderDetails;
        
/**
 *
 * @author gina.du
 */
public class OrderInfo {
  int orderNo;
  String orderTime;
  String shippingTime;
  int orderStatus;
  
  Customer cust;
  ArrayList<OrderDetails> listOrderDetails;

    public int getOrderNo() {
        return orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public Customer getCust() {
        return cust;
    }

    public ArrayList<OrderDetails> getListOrderDetails() {
        return listOrderDetails;
    }
}
