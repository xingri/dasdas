package com.lge.spartan.customer;

import java.util.List;

public class OrderSubmitter implements IOrderSubmit{
	IOrderSubmit impl = null; 
	public OrderSubmitter(IOrderSubmit impl){
		this.impl = impl; 
	}
	
	@Override
	public void submitOrder(Order order) {
		impl.submitOrder(order);
	}

	@Override
	public List<String> getWidgetType() {
		return impl.getWidgetType();
	}

	@Override
	public List<Order> getOrderStatus(String phoneNumber) {
		return impl.getOrderStatus(phoneNumber);
	}


}
