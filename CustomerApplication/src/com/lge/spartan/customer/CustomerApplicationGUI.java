package com.lge.spartan.customer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class CustomerApplicationGUI {
	JFrame fMain;
	JPanel pNewOrder;
	JPanel pOrderStatus;
	JPanel pCustomerInfo;
	JPanel pOrderSpec; 
	JLabel lPhoneNumber, lAddress; 
	JTextField taPhoneNumber;
	JTextArea taAddress;
	JButton bSubmit; 
	
	JScrollPane spAddress;
	
	JList<String> widget;

	
	public CustomerApplicationGUI (){
		fMain = new JFrame("Customer Application");
		pNewOrder = new JPanel(new GridLayout(1,2));
		pOrderStatus = new JPanel(new BorderLayout());
		pCustomerInfo = new JPanel(new GridLayout(4,1));
		pOrderSpec = new JPanel(new BorderLayout());
		
		lPhoneNumber = new JLabel("Phone Number: ", JLabel.CENTER);
		lAddress = new JLabel("Address", JLabel.CENTER); 
		
		taPhoneNumber = new JTextField();
		taAddress = new JTextArea(30, 5);
		spAddress = new JScrollPane();
		spAddress.setViewportView(taAddress);
		
		bSubmit = new JButton("Submit");
	}
	
	public void display(){
		fMain.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//dao.close();
				System.exit(0);
			}
		});
	
		pCustomerInfo.setBorder(BorderFactory.createTitledBorder("Customer Info."));
		pOrderSpec.setBorder(BorderFactory.createTitledBorder("Order Spec."));
		pOrderStatus.setBorder(BorderFactory.createTitledBorder("Order Status"));
		
		pCustomerInfo.add(lPhoneNumber);
		pCustomerInfo.add(taPhoneNumber); 
		pCustomerInfo.add(lAddress);
		pCustomerInfo.add(spAddress);
	
		pNewOrder.add(pCustomerInfo, BorderLayout.WEST);
		pNewOrder.add(pOrderSpec, BorderLayout.EAST);
		//pNewOrder.add(bSubmit, BorderLayout.EAST);
		
		fMain.setLayout(new GridLayout(2, 1));
		fMain.getContentPane().add(pNewOrder);
		fMain.getContentPane().add(pOrderStatus); 
		
		fMain.setSize(500, 500);
		fMain.setVisible(true);
	}
}
