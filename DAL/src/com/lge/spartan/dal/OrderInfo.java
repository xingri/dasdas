package com.lge.spartan.dal;


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
  int orderNo;
  String orderTime;
  String shippingTime;
  int orderStatus;
  Customer cust;
  ArrayList<OrderDetails> listOrderDetails;
}
