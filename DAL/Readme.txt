package com.lge.spartan.dal;

import com.lge.spartan.data.Customer;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.OrderStatus;
import com.lge.spartan.data.RobotStatus;
import com.lge.spartan.data.Widget;
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

    //Init and Uninit DB
    public boolean Initialize(String serverIPAddress, String userName, String pwd);

    public boolean Uninitialize();

    //Orders - Start
    public int AddOrder(List<OrderDetails> orderList, Customer cust);

    public ArrayList<OrderInfo> GetOrders(OrderStatus orderStatus);

    public int GetOrderStatus(int orderNo);

    public ArrayList<OrderInfo> GetOrdersByPhone(String phone/* Enum orderStatus*/);

    public boolean UpdateOrderStatus(int orderNo, Enum orderStatus);

    public OrderInfo PickFirstOrder();
    //Orders - End

    //Widgets - Start
    public int AddWidget(String widgetName, String widgetDesc, int quant, int stationId);

    public int IncWidgets(String widgetName, int increment);

    public int DecWidgets(String widgetName, int decrement);

    public int GetWidgetQuantity(String widgetName);

    public ArrayList<Widget> GetWidgets();

    public int GetWidgetStation(int widgetId);

    public int DecWidgets(int widgetId, int decrement);

    public int GetWidgetQuantity(int widgetId);
    //Widgets - End
    
    //Customers - Start
    public ArrayList<Customer> GetCustomers();
    //Customers - End
    
    //Robot - Start
    public int AddRobotStatus(RobotStatus robotStatus);

    public RobotStatus GetRobotStatus(int orderNo);

    public int UpdateRobotStatus(RobotStatus robotStatus);
    //Robot - End
}
