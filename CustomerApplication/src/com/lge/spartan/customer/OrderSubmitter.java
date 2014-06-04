package com.lge.spartan.customer;

import java.util.List;

import com.lge.spartan.customer.data.Order;

public class OrderSubmitter implements IOrderSubmit{
	private IOrderSubmit impl = null; 
	public OrderSubmitter(){}
	
	public OrderSubmitter(IOrderSubmit impl){
		this.impl = impl; 
	}
	
	public void setOrderSubmitterImpl(IOrderSubmit impl){
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
