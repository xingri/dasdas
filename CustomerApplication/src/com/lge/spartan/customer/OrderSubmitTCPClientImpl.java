package com.lge.spartan.customer;

import java.util.List;

import com.lge.spartan.customer.data.Order;

public class OrderSubmitTCPClientImpl implements IOrderSubmit{
	String ipAddress;
	
	public OrderSubmitTCPClientImpl(String ipAddress){
		this.ipAddress = ipAddress;
	}
	
	@Override
	public void submitOrder(Order order) {
		
	}

	@Override
	public List<String> getWidgetType() {
		return null;
	}

	@Override
	public List<Order> getOrderStatus(String phoneNumber) {
		return null;
	}

}
