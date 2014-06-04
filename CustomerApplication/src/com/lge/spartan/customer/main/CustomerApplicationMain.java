package com.lge.spartan.customer.main;

import com.lge.spartan.customer.CustomerApplicationController;
import com.lge.spartan.customer.CustomerApplicationGUI;
import com.lge.spartan.customer.CustomerApplicationUIOld;
import com.lge.spartan.customer.OrderSubmitTCPClientImpl;
import com.lge.spartan.customer.OrderSubmitter;

public class CustomerApplicationMain {

	public static void main(String[] args) {
		System.out.println("CustomerApplication Start....");
	
		//final CustomerApplicationController cac = new CustomerApplicationController(); 
		final CustomerApplicationController cac = new CustomerApplicationController("127.0.0.1",3333); 
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerApplicationGUI(cac).display();
            	//new CustomerApplicationUIOld().setVisible(true);
            }
        });
	}	
}
