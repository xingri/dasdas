package com.lge.spartan.customer;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.customer.data.Order;
import com.lge.spartan.customer.data.OrderWidgetTuple;

public class CustomerApplicationController {
	private OrderSubmitter orderSubmitter; 
	private List<OrderWidgetTuple> widgetTuples;  
	private List<String> widgetNames; 
	private Order lastOrder; 
	
	public CustomerApplicationController(){
		orderSubmitter = new OrderSubmitter(new OrderSubmitDummyImpl());
		widgetTuples = new ArrayList<OrderWidgetTuple>();
		widgetNames = new ArrayList<String>(); 
		System.out.println("CustomerApplicationController is configured with DummyImpl...");
	}
	
	public CustomerApplicationController(String ipAddress, int port){
		orderSubmitter = new OrderSubmitter(
				new OrderSubmitTCPClientImpl(ipAddress, port));
		widgetTuples = new ArrayList<OrderWidgetTuple>();
		widgetNames = new ArrayList<String>(); 
		System.out.println("CustomerApplicationController is configured with TCPClientImpl...");

	}
	
	public void submitOrder(Order order){
		lastOrder = order; 
		orderSubmitter.submitOrder(order);
	}
	
	public List<String> getWidgetType(){
		widgetNames = orderSubmitter.getWidgetType();
		return widgetNames; 
	}
	
	public List<Order> getOrderStatus(String phoneNumber){
		return orderSubmitter.getOrderStatus(phoneNumber);
	}
	
	public void addWidgetTuple(String name, int quantity){
		widgetTuples.add(new OrderWidgetTuple(name,quantity));
		System.out.println("Added: "+ name + "," + quantity);
	}
	
	public List<OrderWidgetTuple> getWidgetTuple(){
		return widgetTuples; 
	}
	
}
