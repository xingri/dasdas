package com.lge.spartan.warehouse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.lge.spartan.customer.data.Message;
import com.lge.spartan.customer.data.Order;

public class OrderReceiver extends Thread{
	OrderDAO orderDAO = null;
	int port=3333;
	
	public void run(){
		System.out.println("OrderServer is now running....");
		orderDAO = new OrderDAODummy();
		ready(); 
		
	}
	
	public void ready(){
		try{
			ServerSocket ss=new ServerSocket(port);
			System.out.println("ServerSocket("+port+") Creation ready...");
			while(true){
				Socket s=ss.accept();
				System.out.println("Socket Created.");
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				new OrderReceiveThread(ois).start();
				System.out.println("Creating OrderReceiveThread...");
			}
		}catch(Exception e) {
			System.out.println("Exception in Ready(): "+e.getMessage());			
		}
	}
	
	class OrderReceiveThread extends Thread{
		ObjectInputStream ois;
		
		public OrderReceiveThread(ObjectInputStream ois){
			this.ois=ois;
		}
		public void run(){
			try{
				while(true){
					Message msg=(Message)ois.readObject();
					if(msg.getMsgType() == Message.SUBMIT_ORDER){
						Order receivedOrder = (Order)msg.getPayload();
						System.out.println("Got an Order: " + receivedOrder.toString());
						orderDAO.insert(receivedOrder);
					}else{
						System.out.println("Unknown Message Type Error: " + msg.getMsgType());
					}
				
				}
			}catch(Exception e) {
				System.out.println("Read Message Error. ");
			}
		}
	}

}
