package com.lge.spartan.customer;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.customer.data.Order;

public class OrderSubmitDummyImpl implements IOrderSubmit {

	@Override
	public void submitOrder(Order order) {
		System.out.println("Submitted: " + order.toString());
	}

	@Override
	public List<String> getWidgetType() {
		List<String> widgetList = new ArrayList<String>();
		widgetList.add("BasketBall");
		widgetList.add("BaseBall");
		widgetList.add("TennisBall");
		widgetList.add("CrickeetBall");
		System.out.println("Got WidgetNames...");
		return widgetList;
	}

	@Override
	public List<Order> getOrderStatus(String phoneNumber) {
		return null;
	}

}
