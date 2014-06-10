package com.lge.spartan.customer;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;


public class OrderSubmitDummyImpl implements IOrderSubmit {

	@Override
	public void submitOrder(OrderInfo order) {
		System.out.println("Submitted: " + order.toString());
	}

	@Override
	public ArrayList<Widget> getWidgetType() {
		ArrayList<Widget> widgetList = new ArrayList<Widget>();
/*		widgetList.add("BasketBall");
		widgetList.add("BaseBall");
		widgetList.add("TennisBall");
		widgetList.add("CrickeetBall");
*/		System.out.println("Got WidgetNames...");
		return widgetList;
	}

	@Override
	public ArrayList<OrderInfo> getOrderStatus(String phoneNumber) {
		return null;
	}

}
