package com.lge.spartan.customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.customer.data.Message;
import com.lge.spartan.customer.data.Order;


public class OrderSubmitTCPClientImpl implements IOrderSubmit{
	String ipAddress;
	int port;
	Order order;
	
	public OrderSubmitTCPClientImpl(String ipAddress, int port){
		this.ipAddress = ipAddress;
		this.port = port; 
	}
	
	@Override
	public void submitOrder(Order order) {
		this.order = order; 
		connect();
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
	
	
	public void connect(){
		try{
			Socket s=new Socket(ipAddress, port);
			System.out.println("Socket created...");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ObjectOutputStream created...");
			new OrderSendThread(oos).start();
			System.out.println("Creating OrderSendThread...");
		}catch(Exception e){
			System.out.println("Check Server IP or PORT number...");
		}
	}
	
	class OrderSendThread extends Thread{
		ObjectInputStream ois; 
		ObjectOutputStream oos;
		public OrderSendThread(ObjectOutputStream oos){
			this.oos = oos; 
		}
		public void run(){
			try{
				Message msg = new Message(Message.SUBMIT_ORDER, order);
				oos.writeObject(msg);
				System.out.println("Submitted: " + order.toString());
			}catch(Exception e) {
				System.out.println("Error in Sending the order...");
			}
		}
		
	}

	

}
