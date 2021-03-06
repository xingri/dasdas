package com.lge.spartan.customer.main;

import com.lge.spartan.customer.CustomerApplicationController;
import com.lge.spartan.customer.CustomerApplicationGUI;
import com.lge.spartan.customer.CustomerApplicationUIOld;
import com.lge.spartan.customer.OrderSubmitTCPClientImpl;
import com.lge.spartan.customer.OrderSubmitter;

public class CustomerApplicationMain {

    public static void main(String[] args) {
        System.out.println("CustomerApplication Start....");

        //final CustomerApplicationController cac = new CustomerApplicationController("localhost",3333); 
        final CustomerApplicationController cac = new CustomerApplicationController("128.237.247.93", 3333);//Shin's PC

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerApplicationGUI(cac).display();
                //new CustomerApplicationUIOld().setVisible(true);
            }
        });
    }
}
