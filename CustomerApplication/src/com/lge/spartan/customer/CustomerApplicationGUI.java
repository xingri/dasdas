package com.lge.spartan.customer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
	
	JPanel pTitle; 
	JPanel pWidget;
	JPanel pAdd; 
	JPanel pSubmit; 
	
	
	JLabel lPhoneNumber, lAddress; 
	JTextField taPhoneNumber;
	JTextArea taAddress;

	JLabel lWidgetName;
	JList<String> widgetNameList;
	JLabel lWidgetOrder; 

	JButton bAddWidget;
	JButton bGetWidgetName; 
	JButton bSubmit; 
	
	JScrollPane spAddress;
	
	JList<String> widgetListForOrder; 
	
	public CustomerApplicationGUI (){
		fMain = new JFrame("Customer Application");
		//pNewOrder = new JPanel(new GridLayout(1,2));
		pNewOrder = new JPanel(new BorderLayout());
		pOrderStatus = new JPanel(new BorderLayout());
		pCustomerInfo = new JPanel(new GridLayout(4,1));
		pOrderSpec = new JPanel(new BorderLayout());
		
		pTitle = new JPanel(new GridLayout(1,3)); 
		pWidget = new JPanel(new GridLayout(1,3));
		pAdd = new JPanel();
		pSubmit = new JPanel(new GridLayout(1,3)); 
		
		lPhoneNumber = new JLabel("Phone Number: ", JLabel.LEFT);
		lAddress = new JLabel("Address: ", JLabel.LEFT); 
		
		taPhoneNumber = new JTextField();
		taAddress = new JTextArea(30, 5);
		spAddress = new JScrollPane();
		spAddress.setViewportView(taAddress);
		
		bSubmit = new JButton("Order Submit");
		bSubmit.setSize(20,5);
		bAddWidget = new JButton(">>");
		bGetWidgetName = new JButton("Referesh WidgetName");
		
		lWidgetName = new JLabel("Widgets: ");
		widgetNameList = new JList<String>();
		lWidgetOrder = new JLabel("Selected List for Your Order:");
		widgetListForOrder = new JList<String>();
	}
	
	public void showList(){
		ArrayList<String> wList = new ArrayList<String>();
		wList.add("BaseBallWhite");
		wList.add("BaseketBall");
		wList.add("BaseBallYellow");
		wList.add("SoccerBall");
		String[] ca= new String[wList.size()];
		wList.toArray(ca);
		widgetNameList.setListData(ca);
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
	
		pNewOrder.add(pCustomerInfo, BorderLayout.CENTER);
		pNewOrder.add(pOrderSpec, BorderLayout.EAST);
		
		
		pTitle.add(lWidgetName);
		pTitle.add(new JLabel(""));
		pTitle.add(lWidgetOrder); 

		pWidget.add(widgetNameList);
		pWidget.add(pAdd);
		pAdd.add(bAddWidget);
		pWidget.add(widgetListForOrder);
		
		pSubmit.add(bGetWidgetName);
		pSubmit.add(new JLabel());
		pSubmit.add(bSubmit);
		pOrderSpec.add(pTitle, BorderLayout.NORTH);
		pOrderSpec.add(pWidget, BorderLayout.CENTER);
		pOrderSpec.add(pSubmit, BorderLayout.SOUTH);
		
		
		
/*		
		pOrderSpec.add(lWidgetName);
		pOrderSpec.add(new JLabel());
		pOrderSpec.add(lWidgetOrder); 
		pOrderSpec.add(widgetNameList);
		pOrderSpec.add(bAddWidget);
		pOrderSpec.add(widgetListForOrder);
		pOrderSpec.add(new JLabel(""));
		pOrderSpec.add(new JLabel(""));
		pOrderSpec.add(bSubmit);
		*/
		
		showList();
		
		fMain.setLayout(new GridLayout(2, 1));
		fMain.getContentPane().add(pNewOrder);
		fMain.getContentPane().add(pOrderStatus); 
		
		fMain.setSize(800, 500);
		fMain.setVisible(true);
		
	}
}
