package com.lge.spartan.customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.customer.data.Message;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;


public class OrderSubmitTCPClientImpl implements IOrderSubmit{
	String ipAddress;
	int port;
	
	OrderInfo order;
	
	public OrderSubmitTCPClientImpl(String ipAddress, int port){
		this.ipAddress = ipAddress;
		this.port = port; 
	}
	
	@Override
	public void submitOrder(OrderInfo order) {
		this.order = order; 
		sendOrder();
	}

	@Override
	public ArrayList<Widget> getWidgetType() {
		ArrayList<Widget> widgetList = new ArrayList<Widget>();
		try{
			Socket s=new Socket(ipAddress, port);
			System.out.println("Socket created...");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ObjectOutputStream created...");
		
			Message request = new Message(Message.GET_WIDGET_TYPE, new Integer(1));
			oos.writeObject(request);
			System.out.println("Reqeust Widget Type: ");
			Message reply = (Message)ois.readObject();
			System.out.println(reply.getMsgType());
			
			
			widgetList = (ArrayList<Widget>)reply.getPayload();
			System.out.println(widgetList);
			/*for(String p : widgetList){
				 System.out.println(s);
			}*/
			
/*			ois.close();
			oos.close();
			s.close();*/
			//new OrderSendThread(oos, ois).start();
			//System.out.println("Creating OrderSendThread...");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Check Server IP or PORT number...");
		}
		
		List<String> widgetList2 = new ArrayList<String>();
		widgetList2.add("BasketBall");
		widgetList2.add("BaseBall");
		widgetList2.add("TennisBall");
		widgetList2.add("CrickeetBall");
		System.out.println("Got WidgetNames...");
		
		return widgetList;
	}

	@Override
	public ArrayList<OrderInfo> getOrderStatus(String phoneNumber) {
		return null;
	}
	
	
	public void sendOrder(){
		try{
			Socket s=new Socket(ipAddress, port);
			System.out.println("Socket created...");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ObjectOutputStream created...");
		
			Message request = new Message(Message.SUBMIT_ORDER, order);
			oos.writeObject(request);
			System.out.println("Submitted: " + order.toString());
			Message reply = (Message)ois.readObject();
			System.out.println(reply.getMsgType());
		/*	ois.close();
			oos.close();
			s.close();*/
			//new OrderSendThread(oos, ois).start();
			//System.out.println("Creating OrderSendThread...");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Check Server IP or PORT number...");
		}
	}
	
	
	
	class OrderSendThread extends Thread{
		ObjectInputStream ois; 
		ObjectOutputStream oos;
		public OrderSendThread(ObjectOutputStream oos, ObjectInputStream ois){
			this.oos = oos; 
			this.ois = ois; 
		}
		public void run(){
			try{
				Message request = new Message(Message.SUBMIT_ORDER, order);
				oos.writeObject(request);
				System.out.println("Submitted: " + order.toString());
				Message reply = (Message)ois.readObject();
				System.out.println(reply.getMsgType());
			}catch(Exception e) {
				System.out.println("Error in Sending the order...");
			}
		}
		
	}

	

}
