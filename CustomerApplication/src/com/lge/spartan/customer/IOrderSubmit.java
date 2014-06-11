package com.lge.spartan.customer;

import java.util.ArrayList;

import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;


interface IOrderSubmit {
	void submitOrder(OrderInfo order);
	ArrayList<Widget> getWidgetType();
	ArrayList<OrderInfo> getOrderStatus(String phoneNumber); 
}
