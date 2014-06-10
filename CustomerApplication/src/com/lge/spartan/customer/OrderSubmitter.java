package com.lge.spartan.customer;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;

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
	public void submitOrder(OrderInfo order) {
		impl.submitOrder(order);
	}

	@Override
	public ArrayList<Widget> getWidgetType() {
		return impl.getWidgetType();
	}

	@Override
	public ArrayList<OrderInfo> getOrderStatus(String phoneNumber) {
		return impl.getOrderStatus(phoneNumber);
	}


}
