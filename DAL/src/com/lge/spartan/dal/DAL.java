package com.lge.spartan.dal;

import com.lge.spartan.data.Widget;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Customer;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
public interface DAL {

    public boolean Initialize(String serverIPAddress, String userName, String pwd);

    public boolean Uninitialize();

    public int AddOrder(List<OrderDetails> orderList, Customer cust);

    public int AddWidget(String widgetName, String widgetDesc, int quant, int stationId);

    public int IncWidgets(String widgetName, int increment);

    public int DecWidgets(String widgetName, int decrement);

    public ArrayList<Widget> GetWidgets();

    public ArrayList<Customer> GetCustomers();

    public ArrayList<OrderInfo> GetShippedOrders();

    public ArrayList<OrderInfo> GetPendingOrders();

    public ArrayList<OrderInfo> GetBackorderedOrders();
    
    public int GetOrderStatus(int orderNo);
    
    public ArrayList<OrderInfo> GetOrdersByPhone(String phone/* Enum orderStatus*/) ;
    
    public boolean UpdateOrderStatus(int orderNo, Enum orderStatus);
}
