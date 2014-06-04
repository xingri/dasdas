package com.lge.spartan.customer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.lge.spartan.customer.data.Order;
import com.lge.spartan.customer.data.OrderWidgetTuple;

public class CustomerApplicationGUI {
	CustomerApplicationController controller = null; 
	
	String selectedWidgetName = null; 
	
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
	JButton bGetOrderStatus; 
	
	JScrollPane spAddress;
	
	JList<OrderWidgetTuple> widgetListForOrder; 
	
	public CustomerApplicationGUI (CustomerApplicationController cac){
		this.controller = cac;  // Assigne Controller
		
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
		widgetListForOrder = new JList<OrderWidgetTuple>();
	}
	
	public void showList(){
		List<String> wList = controller.getWidgetType();
		String[] ca= new String[wList.size()];
		wList.toArray(ca);
		widgetNameList.setListData(ca);
		selectedWidgetName = wList.get(0);
		
		
	}
	public void showListTuple(){
		List<OrderWidgetTuple> owtList= controller.getWidgetTuple();
		OrderWidgetTuple[] owt = new OrderWidgetTuple[owtList.size()];
		owtList.toArray(owt); 
		widgetListForOrder.setListData(owt);
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
		
		ActionListener bHandler=new ButtonHandler();
		bSubmit.addActionListener(bHandler);
		bGetWidgetName.addActionListener(bHandler);
		bAddWidget.addActionListener(bHandler);
		
		ListSelectionListener listener=new ListHandler();
		widgetNameList.addListSelectionListener(listener);
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
	
	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== bSubmit){
				Order order = new Order(); 
				order.setPhoneNumber(taPhoneNumber.getText());
				order.setAddress(taAddress.getText());
				List<OrderWidgetTuple> listWidget = controller.getWidgetTuple();
				order.setWidgets(listWidget);
				
				controller.submitOrder(order); 
			}else if(e.getSource()== bAddWidget){
				String quantity = JOptionPane.showInputDialog("How many ?");
				System.out.println(selectedWidgetName + ":" + quantity);
				controller.addWidgetTuple(selectedWidgetName, Integer.parseInt(quantity));
				showListTuple(); 
				
			}else if(e.getSource()==bGetWidgetName){
				controller.getWidgetType();
			}else if(e.getSource()==bGetOrderStatus){
				controller.getOrderStatus("111");
			}
			else{
				//dao.close();
				System.exit(0);
			}
		}
	}
	
	class ListHandler implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource()== widgetNameList){
				selectedWidgetName = widgetNameList.getSelectedValue();
			 
				System.out.println(selectedWidgetName + " Selected");
			}
		}
	}
}
