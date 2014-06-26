package com.lge.spartan.customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.lge.spartan.customer.data.Message;
import com.lge.spartan.data.OrderDetails;
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
			if(s == null)
			{
				JOptionPane.showMessageDialog(null, "Connection failed. May be network is lost or IPAddress (" + ipAddress + ") is wrong.");
			}
			System.out.println("Socket created...");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ObjectOutputStream created...");
		
			Message request = new Message(Message.GET_WIDGET_TYPE, new Integer(1));
			oos.writeObject(request);
			oos.flush();
			// oos.close(); 
			
			System.out.println("Reqeust Widget Type: ");
			Message reply = (Message)ois.readObject();
			System.out.println(reply.getMsgType());
			
			
			widgetList = (ArrayList<Widget>)reply.getPayload();
			System.out.println(widgetList);
			/*for(String p : widgetList){
				 System.out.println(s);
			}*/
			
			ois.close();
			oos.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Check Server IP or PORT number...");
		}
/*		
		List<String> widgetList2 = new ArrayList<String>();
		widgetList2.add("BasketBall");
		widgetList2.add("BaseBall");
		widgetList2.add("TennisBall");
		widgetList2.add("CrickeetBall");
		System.out.println("Got WidgetNames...");
*/		
		return widgetList;
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
			ois.close();
			oos.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Check Server IP or PORT number...");
		}
	}
	
	public ArrayList<OrderInfo> getOrderStatus(String phoneNumber){
		ArrayList<OrderInfo> orderList = new ArrayList<OrderInfo>();
		try{
			Socket s=new Socket(ipAddress, port);
			System.out.println("Socket created...");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			System.out.println("ObjectOutputStream created...");
		
			Message request = new Message(Message.GET_ORDER_STATUS, phoneNumber);
			oos.writeObject(request);
			System.out.println("Reqeust Order Status: ");
			Message reply = (Message)ois.readObject();
			System.out.println(reply.getMsgType());
			
			orderList = (ArrayList<OrderInfo>)reply.getPayload();
			System.out.println(orderList);
			
			ois.close();
			oos.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Check Server IP or PORT number...");
		}
		
/*
		OrderInfo order = new OrderInfo();
		ArrayList<OrderDetails> od = new ArrayList<OrderDetails>();
		OrderDetails od1 = new OrderDetails();
		od1.setQuantity(1);
		od1.setWidgetId(1);
		od1.setWidgetName("PP");
		od.add(od1);
		order.setOrderNo(1);
		order.setOrderTime("1974");
		order.setShippingTime("444");
		order.setStatus(1);
		order.setListOrderDetails(od);
		orderList.add(order);
*/		
		return orderList;
	}
}
