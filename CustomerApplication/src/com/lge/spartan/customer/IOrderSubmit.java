package com.lge.spartan.customer;

import java.util.List;

interface IOrderSubmit {
	void submitOrder(Order order);
	List<String> getWidgetType();
	List<Order> getOrderStatus(String phoneNumber); 
}
