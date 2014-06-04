package com.lge.spartan.customer;

import java.util.List;

import com.lge.spartan.customer.data.Order;

interface IOrderSubmit {
	void submitOrder(Order order);
	List<String> getWidgetType();
	List<Order> getOrderStatus(String phoneNumber); 
}
