package com.lge.spartan.warehouse.order;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.customer.data.Message;
import com.lge.spartan.data.Customer;
import com.lge.spartan.dal.DAL;
import com.lge.spartan.dal.MySQLDALImpl;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.Widget;


public class OrderReceiver extends Thread{
	DAL db = new MySQLDALImpl();
	
	int port=3333;
	
	public void run(){
		System.out.println("OrderServer is now running....");
	
		boolean dbCheck = db.Initialize("localhost", "root", "seo10jin");
		//boolean dbCheck = db.Initialize("10.254.17.157", "spartan", "spartan");
		if(dbCheck == false){
			System.out.println("DB initailization Error");
		}
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
				new OrderReceiveThread(ois, oos).start();
				System.out.println("Creating OrderReceiveThread...");
			}
		}catch(Exception e) {
			System.out.println("Exception in Ready(): "+e.getMessage());			
		}
	}
	
	class OrderReceiveThread extends Thread{
		ObjectInputStream ois;
		ObjectOutputStream oos; 
		
		public OrderReceiveThread(ObjectInputStream ois, ObjectOutputStream oos){
			this.ois=ois;
			this.oos = oos; 
		}
		public void run(){
			try{
				while(true){
					Message msg=(Message)ois.readObject();
					
					if(msg.getMsgType() == Message.SUBMIT_ORDER){
						OrderInfo receivedOrder = (OrderInfo)msg.getPayload();
						System.out.println("Got an Order: " + receivedOrder.toString());
						//ArrayList<OrderDetails> list = new ArrayList<>();
						
						List<OrderDetails> widgetList = receivedOrder.getListOrderDetails();
						Customer customer = receivedOrder.getCust();
/*						for(OrderWidgetTuple owt : widgetList){
							OrderDetails od = new OrderDetails();
							od.setQuantity(owt.getQuantity());
							od.setWidgetId(owt.getWidgetId());
							//od.setWidgetName("");
							list.add(od);
						}
*/
						db.AddOrder(widgetList, customer);
						Message reply = new Message(102, new Integer(2)); 
						oos.writeObject(reply);
						oos.flush();
						
					}else if(msg.getMsgType() == Message.GET_WIDGET_TYPE){
						System.out.println("Got request");
						List<String> widgetList = new ArrayList<String>();

						List<Widget> widgetListDAL = db.GetWidgets();
						for(Widget w : widgetListDAL){
							widgetList.add(w.getName());
						}
/*						widgetList.add("BasketBall");
						widgetList.add("BaseBall");
						widgetList.add("TennisBall");
						widgetList.add("CrickeetBall");*/
						Message reply = new Message(103, widgetListDAL);
						oos.writeObject(reply);
						oos.flush();
						System.out.println("Msg Send");
					
					}else if (msg.getMsgType() == Message.GET_ORDER_STATUS){
						System.out.println("Got request");
						String phoneNumber = (String)msg.getPayload();
						System.out.println("PhoneNumber:" + phoneNumber);
						ArrayList<OrderInfo> oiList = new ArrayList<OrderInfo>();
						oiList = db.GetOrdersByPhone(phoneNumber);
						OrderInfo order = new OrderInfo();
						ArrayList<OrderDetails> od = new ArrayList<OrderDetails>();
						OrderDetails od1 = new OrderDetails();
						od1.setQuantity(1);
						od1.setWidgetId(1);
						od1.setWidgetName("BasketBall");
						od.add(od1);
						order.setOrderNo(27);
						order.setOrderTime("2014-06-11");
						order.setShippingTime("2014-06-11");
						order.setStatus(1);
						order.setListOrderDetails(od);
						oiList.add(order);
						
						Message reply = new Message(104, oiList);
						oos.writeObject(reply);
						oos.flush();
						System.out.println("Msg Send");
					}
					else{
						System.out.println("Unknown Message Type Error: " + msg.getMsgType());
					}
				
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Read Message Error. ");
			}
		}
	}

}
