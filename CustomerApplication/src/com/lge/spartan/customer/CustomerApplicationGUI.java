package com.lge.spartan.customer;

import com.lge.spartan.customer.data.OrderDisplay;
import com.lge.spartan.data.Customer;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.OrderStatus;
import com.lge.spartan.data.Widget;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CustomerApplicationGUI {

    CustomerApplicationController controller = null;

    String selectedWidgetName = null;
    int selectedWidgetID;

    JFrame fMain;
    JPanel pNewOrder;
    JPanel pOrderStatus;
    JPanel pCustomerInfo;
    JPanel pOrderSpec;

    JPanel pTitle;
    JPanel pWidget;
    JPanel pAdd;
    JPanel pSubmit;
    JPanel pGetStatus;
    JPanel pListOrder;

    JLabel lPhoneNumber, lAddress, lOrderStatus, lFName, lLName, lEmail;
    JTextField taPhoneNumber;
    JTextField taFName;
    JTextField taLName;
    JTextField taEmail;
    JTextArea taAddress;

    JLabel lWidgetName;
    JLabel lWidgetOrder;

    JButton bAddWidget;
    JButton bGetWidgetName;
    JButton bSubmit;
    JButton bGetOrderStatus;
    JButton bClear;

    JScrollPane spAddress;
    JScrollPane spWidget;
    JList<Widget> widgetNameList;
    JList<OrderDetails> widgetListForOrder;
    JList<OrderDisplay> orderDisplay;
    javax.swing.JTable jTable1;

    public CustomerApplicationGUI(CustomerApplicationController cac) {
        this.controller = cac;  // Assigne Controller

        fMain = new JFrame("Customer Application");
        //pNewOrder = new JPanel(new GridLayout(1,2));
        pNewOrder = new JPanel(new BorderLayout());
        pOrderStatus = new JPanel(new BorderLayout());
        pCustomerInfo = new JPanel(new GridLayout(4, 1));
        pOrderSpec = new JPanel(new BorderLayout());
        pListOrder = new JPanel(new BorderLayout());
        pGetStatus = new JPanel();

        pTitle = new JPanel(new GridLayout(1, 3));
        pWidget = new JPanel(new GridLayout(1, 3));
        pAdd = new JPanel();
        pSubmit = new JPanel(new GridLayout(1, 3));

        lPhoneNumber = new JLabel("Phone Number: ", JLabel.LEFT);
        lAddress = new JLabel("Address: ", JLabel.LEFT);
        lFName = new JLabel("First Name: ", JLabel.LEFT);
        lLName = new JLabel("Last Name: ", JLabel.LEFT);
        lEmail = new JLabel("Email Id: ", JLabel.LEFT);
        lOrderStatus = new JLabel("OrderNo.    Status          OrderTime                          ShipTime                                 Desc.");

        jTable1 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "S No", "Order No", "Status", "Order Time", "Shipping Time", "Details"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });

        taPhoneNumber = new JTextField();
        taFName = new JTextField();
        taLName = new JTextField();
        taEmail = new JTextField();
        taAddress = new JTextArea(30, 5);
        spAddress = new JScrollPane();
        spAddress.setViewportView(taAddress);
        bSubmit = new JButton("Submit Order");
        bSubmit.setSize(20, 5);
        bAddWidget = new JButton(">>");
        bGetWidgetName = new JButton("Refresh Widgets");
        bGetOrderStatus = new JButton("Get Order Status");
        bClear = new JButton("Clear");

        lWidgetName = new JLabel("Widgets: ");
        widgetNameList = new JList<Widget>();
        lWidgetOrder = new JLabel("Selected List for Your Order:");
        widgetListForOrder = new JList<OrderDetails>();
        orderDisplay = new JList<OrderDisplay>();

        spWidget = new JScrollPane();
        spWidget.setViewportView(widgetNameList);;
    }

    public void showList() {
        List<Widget> wList = controller.getWidgetType();
        if (wList == null) {
            return;
        }
        Widget[] ca = new Widget[wList.size()];
        wList.toArray(ca);
        widgetNameList.setListData(ca);
        selectedWidgetName = wList.get(0).getName();
    }

    public void showListTuple() {
        ArrayList<OrderDetails> owtList = controller.getWidgetTuple();
        if (owtList == null) {
            return;
        }
        OrderDetails[] owt = new OrderDetails[owtList.size()];
        owtList.toArray(owt);
        widgetListForOrder.setListData(owt);
    }

    public void showOrderStatus() {
        ArrayList<OrderDisplay> odList = new ArrayList<OrderDisplay>();
        ArrayList<OrderInfo> oiList = new ArrayList<OrderInfo>();
        oiList = controller.getOrderStatus(taPhoneNumber.getText());
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        int i = 0;
        for (OrderInfo oi : oiList) {
            OrderDisplay od = new OrderDisplay();
            ArrayList<OrderDetails> details = oi.getListOrderDetails();
            StringBuilder sb = new StringBuilder();
            for (OrderDetails d : details) {
                sb.append("[" + d.getWidgetName())
                        .append(",")
                        .append(d.getQuantity())
                        .append("]");
            }

            od.setDetails(sb.toString());
            od.setOrderNo(oi.getOrderNo());
            od.setOrderStatus(oi.getStatus());
            od.setOrderTime(oi.getOrderTime());
            od.setShippingTime(oi.getShippingTime());
            odList.add(od);

            String status = od.getOrderStatus().toString();
            if (status.equals("Complete")) {
                status = "Shipped";
            }
            model.addRow(new Object[]{i++, od.getOrderNo(), status, od.getOrderTime(), od.getShippingTime(), od.getDetails()});
        }

        OrderDisplay[] owt = new OrderDisplay[odList.size()];
        odList.toArray(owt);
        orderDisplay.setListData(owt);
    }

    public void registerHandler() {

        ActionListener bHandler = new ButtonHandler();
        bSubmit.addActionListener(bHandler);
        bGetWidgetName.addActionListener(bHandler);
        bAddWidget.addActionListener(bHandler);
        bGetOrderStatus.addActionListener(bHandler);

        ListSelectionListener listener = new ListHandler();
        widgetNameList.addListSelectionListener(listener);
    }

    public void display() {
        fMain.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //dao.close();
                System.exit(0);
            }
        });

        pCustomerInfo.setBorder(BorderFactory.createTitledBorder("Customer Info."));
        pOrderSpec.setBorder(BorderFactory.createTitledBorder("Order Spec."));
        pOrderStatus.setBorder(BorderFactory.createTitledBorder("Order Status"));

        pOrderSpec.add(pTitle, BorderLayout.NORTH);
        pOrderSpec.add(pWidget, BorderLayout.CENTER);
        pOrderSpec.add(pSubmit, BorderLayout.SOUTH);
        pCustomerInfo.add(lPhoneNumber);
        pCustomerInfo.add(taPhoneNumber);
        pCustomerInfo.add(lFName);
        pCustomerInfo.add(taFName);
        pCustomerInfo.add(lEmail);
        pCustomerInfo.add(taEmail);

        pCustomerInfo.add(lAddress);
        pCustomerInfo.add(spAddress);

                //pCustomerInfo.add(lLName); 
        //pCustomerInfo.add(taLName); 
        pNewOrder.add(pCustomerInfo, BorderLayout.CENTER);
        pNewOrder.add(pOrderSpec, BorderLayout.EAST);

        pTitle.add(lWidgetName);
        pTitle.add(new JLabel(""));
        pTitle.add(lWidgetOrder);

        pWidget.add(widgetNameList);
        //pWidget.add(spWidget);
        pWidget.add(pAdd);
        pAdd.add(bAddWidget);
        pWidget.add(widgetListForOrder);

        pSubmit.add(bGetWidgetName);
        pSubmit.add(new JLabel());
        pSubmit.add(bSubmit);

		//pOrderStatus.add(lOrderStatus, BorderLayout.NORTH);
        //pOrderStatus.add(pListOrder, BorderLayout.CENTER);
        pOrderStatus.add(pGetStatus, BorderLayout.SOUTH);
        pOrderStatus.add(jTable1.getTableHeader(), BorderLayout.NORTH);
        pOrderStatus.add(jTable1, BorderLayout.CENTER);

        pListOrder.add(orderDisplay);
        pGetStatus.add(bGetOrderStatus);

        //showList();
        registerHandler();
        fMain.setLayout(new GridLayout(2, 1));
        fMain.getContentPane().add(pNewOrder);
        fMain.getContentPane().add(pOrderStatus);

        fMain.setSize(800, 700);
        fMain.setVisible(true);
    }

    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bSubmit) {
                SubmitOrder();
            } else if (e.getSource() == bAddWidget) {
                AddWidget();
            } else if (e.getSource() == bGetWidgetName) {
                showList();
            } else if (e.getSource() == bGetOrderStatus) {
                System.out.println("GetOrderStatus Called");
                showOrderStatus();
            } else {
                //dao.close();
                System.exit(0);
            }
        }

        private void AddWidget() throws HeadlessException {
            if (selectedWidgetName == null) {
                JOptionPane.showMessageDialog(null, "Please select the Widgets before adding to the Order");
            } else {
                int quant = -1;
                String quantity = null;
                do {
                    boolean validQuant = false;
                    quantity = JOptionPane.showInputDialog("How many ?");
                    try {
                        quant = Integer.parseInt(quantity);
                        validQuant = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Please enter the numeric value");
                    }
                    if (validQuant == true && quant <= 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a value > 0");
                    }
                } while (quant <= 0);
                System.out.println(selectedWidgetID + ":" + selectedWidgetName + ":" + quantity);
                controller.addWidgetTuple(selectedWidgetID, selectedWidgetName, quant);
                showListTuple();
            }
        }

        private void SubmitOrder() throws HeadlessException {
            OrderInfo order = new OrderInfo();
            Customer customer = new Customer();
            customer.setAddress(taAddress.getText());
            customer.setPhone(taPhoneNumber.getText());
            customer.setFname(taFName.getText());
            customer.setLname(taLName.getText());
            customer.setEmail(taEmail.getText());
            order.setCust(customer);
            ArrayList<OrderDetails> listWidget = controller.getWidgetTuple();
            if (listWidget.size() <= 0) {
                JOptionPane.showMessageDialog(null, "Please add Widgets before submitting the Order");
            } else {
                order.setListOrderDetails(listWidget);
                controller.submitOrder(order);
                controller.clearOrder();
                showListTuple();
            }
        }
    }

    class ListHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == widgetNameList) {
                selectedWidgetName = widgetNameList.getSelectedValue().getName();
                selectedWidgetID = widgetNameList.getSelectedValue().getWidgetId();
                System.out.println(selectedWidgetID + "," + selectedWidgetName + " Selected");
            }
        }
    }
}
