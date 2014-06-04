package com.lge.spartan.customer.main;

import com.lge.spartan.customer.CustomerApplicationGUI;
import com.lge.spartan.customer.CustomerApplicationUIOld;
import com.lge.spartan.customer.OrderSubmitTCPClientImpl;
import com.lge.spartan.customer.OrderSubmitter;

public class CustomerApplicationMain {

	public static void main(String[] args) {
		System.out.println("CustomerApplication Start....");

		String ipAddress = "127.0.0.1";
		
		OrderSubmitter orderSubmitter = new OrderSubmitter(
				new OrderSubmitTCPClientImpl(ipAddress));

		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerApplicationGUI().display();
            	//new CustomerApplicationUIOld().setVisible(true);
            }
        });
	}	
}
