package com.lge.spartan.customer;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;

public class CustomerApplicationController {
	private OrderSubmitter orderSubmitter; 
	private ArrayList<OrderDetails> widgetTuples;  
	private ArrayList<Widget> widgetNames; 
	private OrderInfo lastOrder; 
	
	private CustomerApplicationGUI view; 
	
	public CustomerApplicationController(){
		orderSubmitter = new OrderSubmitter(new OrderSubmitDummyImpl());
		widgetTuples = new ArrayList<OrderDetails>();
		widgetNames = new ArrayList<Widget>(); 
		System.out.println("CustomerApplicationController is configured with DummyImpl...");
	}
	
	public CustomerApplicationController(String ipAddress, int port){
		orderSubmitter = new OrderSubmitter(
				new OrderSubmitTCPClientImpl(ipAddress, port));
		widgetTuples = new ArrayList<OrderDetails>();
		widgetNames = new ArrayList<Widget>(); 
		System.out.println("CustomerApplicationController is configured with TCPClientImpl...");
	}
	
	public CustomerApplicationController(CustomerApplicationGUI view, String ipAddress, int port){
		this.view = view; 
		orderSubmitter = new OrderSubmitter(
				new OrderSubmitTCPClientImpl(ipAddress, port));
		widgetTuples = new ArrayList<OrderDetails>();
		widgetNames = new ArrayList<Widget>(); 
		System.out.println("CustomerApplicationController is configured with TCPClientImpl...");
		view.registerHandler();
	}
	
	public void submitOrder(OrderInfo order){
		lastOrder = order; 
		orderSubmitter.submitOrder(order);
	}
	
	public ArrayList<Widget> getWidgetType(){
		widgetNames = orderSubmitter.getWidgetType();
		return widgetNames; 
	}
	
	public ArrayList<OrderInfo> getOrderStatus(String phoneNumber){
		return orderSubmitter.getOrderStatus(phoneNumber);
	}
	
	public void addWidgetTuple(int id, String name, int quantity){
		OrderDetails od = new OrderDetails(id,quantity);
		od.setWidgetName(name);
		widgetTuples.add(od);
		
		System.out.println("Added: "+ id + "," + name + "," + quantity);
	}
	
	public ArrayList<OrderDetails> getWidgetTuple(){
		return widgetTuples; 
	}
	
}
