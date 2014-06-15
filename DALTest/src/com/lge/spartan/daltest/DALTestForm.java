/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.daltest;

import com.lge.spartan.dal.DAL;
import com.lge.spartan.dal.MySQLDALImpl;
import com.lge.spartan.data.Customer;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.OrderStatus;
import com.lge.spartan.data.Widget;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author vijay.rachabattuni
 */
public class DALTestForm extends javax.swing.JFrame {
     static DAL dal = null;
    /**
     * Creates new form DALTestForm
     */
    public DALTestForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnInitDB = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jbtnGetCustomers = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbtnPendingOrders = new javax.swing.JButton();
        jbtnShippingOrders = new javax.swing.JButton();
        jbtnBackorderedOrders = new javax.swing.JButton();
        jbtnOrdersByPhone = new javax.swing.JButton();
        jbtnUpdateOrderStatus = new javax.swing.JButton();
        jtfPhone = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtfOrderNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbOrderStatus = new javax.swing.JComboBox();
        jbtnAddOrder = new javax.swing.JButton();
        jbtnGetPendingOrders = new javax.swing.JButton();
        jbtnGetAllOrders = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jbtnGetWidgets = new javax.swing.JButton();
        jbtnAddWidget = new javax.swing.JButton();
        jbtnIncWidget = new javax.swing.JButton();
        jbtnDecWidget = new javax.swing.JButton();
        jtfWName = new javax.swing.JTextField();
        jtfWDesc = new javax.swing.JTextField();
        jtfWQuant = new javax.swing.JTextField();
        jtfWStationId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfIncWName = new javax.swing.JTextField();
        jtfIncBy = new javax.swing.JTextField();
        jtfDecWName = new javax.swing.JTextField();
        jtfDecBy = new javax.swing.JTextField();
        jbtnGetWQuantity = new javax.swing.JButton();
        jtfGetWQuantName = new javax.swing.JTextField();
        jlblWQuantity = new javax.swing.JLabel();
        jbtnUninitDB = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DAL Test Application");

        jbtnInitDB.setText("Initialize DB");
        jbtnInitDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnInitDBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Monitor", jPanel2);

        jbtnGetCustomers.setText("Get Customers");
        jbtnGetCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetCustomersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnGetCustomers)
                .addContainerGap(488, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnGetCustomers)
                .addContainerGap(239, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Customers", jPanel3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("All", jPanel5);

        jbtnPendingOrders.setText("Get InProgress Orders");
        jbtnPendingOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPendingOrdersActionPerformed(evt);
            }
        });

        jbtnShippingOrders.setText("Get Shipped Orders");
        jbtnShippingOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnShippingOrdersActionPerformed(evt);
            }
        });

        jbtnBackorderedOrders.setText("Get Backordered Orders");
        jbtnBackorderedOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBackorderedOrdersActionPerformed(evt);
            }
        });

        jbtnOrdersByPhone.setText("Get Orders By Phone");
        jbtnOrdersByPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOrdersByPhoneActionPerformed(evt);
            }
        });

        jbtnUpdateOrderStatus.setText("Update Order Status");
        jbtnUpdateOrderStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateOrderStatusActionPerformed(evt);
            }
        });

        jtfPhone.setText("1-123-2344");

        jLabel1.setText("Phone");

        jtfOrderNo.setText("101");

        jLabel2.setText("OrderNo");

        jLabel3.setText("Status");

        jcbOrderStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pending", "Inprogress", "Backoredered", "Complete" }));

        jbtnAddOrder.setText("Add Orders");
        jbtnAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddOrderActionPerformed(evt);
            }
        });

        jbtnGetPendingOrders.setText("Get Pending Orders");
        jbtnGetPendingOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetPendingOrdersActionPerformed(evt);
            }
        });

        jbtnGetAllOrders.setText("Get All Orders");
        jbtnGetAllOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetAllOrdersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnOrdersByPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnUpdateOrderStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfPhone)
                            .addComponent(jtfOrderNo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jcbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbtnGetAllOrders, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnGetPendingOrders, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnAddOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnBackorderedOrders, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnShippingOrders, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnPendingOrders, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnAddOrder)
                .addGap(5, 5, 5)
                .addComponent(jbtnPendingOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnShippingOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnBackorderedOrders)
                .addGap(4, 4, 4)
                .addComponent(jbtnGetPendingOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnGetAllOrders)
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnOrdersByPhone)
                    .addComponent(jtfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnUpdateOrderStatus)
                    .addComponent(jtfOrderNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jcbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Orders", jPanel4);

        jbtnGetWidgets.setText("Get Widgets");
        jbtnGetWidgets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetWidgetsActionPerformed(evt);
            }
        });

        jbtnAddWidget.setText("Add Widget");
        jbtnAddWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddWidgetActionPerformed(evt);
            }
        });

        jbtnIncWidget.setText("Inc Widget");
        jbtnIncWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIncWidgetActionPerformed(evt);
            }
        });

        jbtnDecWidget.setText("Dec Widget");
        jbtnDecWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDecWidgetActionPerformed(evt);
            }
        });

        jLabel4.setText("Name");

        jLabel5.setText("Desc");

        jLabel6.setText("Quantity");

        jLabel7.setText("StationId");

        jLabel8.setText("Name");

        jLabel9.setText("Increment By");

        jbtnGetWQuantity.setText("Get Widget Quantity");
        jbtnGetWQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetWQuantityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnAddWidget)
                    .addComponent(jbtnGetWidgets)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbtnIncWidget, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnDecWidget, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jbtnGetWQuantity))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfWName)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jtfIncWName)
                    .addComponent(jtfDecWName)
                    .addComponent(jtfGetWQuantName, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jtfDecBy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(jtfIncBy, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfWDesc, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jtfWQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jtfWStationId, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jlblWQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnGetWidgets)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnAddWidget)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfWName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfWDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfWQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfWStationId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnIncWidget)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfIncBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfIncWName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnDecWidget)
                    .addComponent(jtfDecWName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDecBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnGetWQuantity)
                    .addComponent(jtfGetWQuantName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblWQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventory", jPanel1);

        jbtnUninitDB.setText("Uninitialize DB");
        jbtnUninitDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUninitDBActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jbtnInitDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnUninitDB)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnInitDB)
                    .addComponent(jbtnUninitDB))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnInitDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInitDBActionPerformed
        // TODO add your handling code here:
        InitDB();
    }//GEN-LAST:event_jbtnInitDBActionPerformed

    private void jbtnPendingOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPendingOrdersActionPerformed
        // TODO add your handling code here:
        ArrayList<OrderInfo> listOI = dal.GetOrders(OrderStatus.Inprogress);
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnPendingOrdersActionPerformed

    private void jbtnGetCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetCustomersActionPerformed
        // TODO add your handling code here:
        ArrayList<Customer> list = dal.GetCustomers();
        for(Customer c : list)
        {
            System.out.println(c.toString());
        }
    }//GEN-LAST:event_jbtnGetCustomersActionPerformed

    private void jbtnGetWidgetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetWidgetsActionPerformed
        // TODO add your handling code here:
        ArrayList<Widget> list = dal.GetWidgets();
        for(Widget w : list)
        {
            System.out.println(w.toString());
        }
    }//GEN-LAST:event_jbtnGetWidgetsActionPerformed

    private void jbtnShippingOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnShippingOrdersActionPerformed
        // TODO add your handling code here:
         ArrayList<OrderInfo> listOI = dal.GetOrders(OrderStatus.Complete);
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnShippingOrdersActionPerformed

    private void jbtnBackorderedOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBackorderedOrdersActionPerformed
        // TODO add your handling code here:
           ArrayList<OrderInfo> listOI = dal.GetOrders(OrderStatus.Backordered);
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnBackorderedOrdersActionPerformed

    private void jbtnOrdersByPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOrdersByPhoneActionPerformed
        // TODO add your handling code here:
        System.out.println("DALTest: Phone" + jtfPhone.getText());
        ArrayList<OrderInfo> listOI = dal.GetOrdersByPhone(jtfPhone.getText());
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnOrdersByPhoneActionPerformed

    private void jbtnUpdateOrderStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateOrderStatusActionPerformed
        // TODO add your handling code here:
        int on = Integer.parseInt(jtfOrderNo.getText());
        int status = jcbOrderStatus.getSelectedIndex();
        System.out.println("DALTest: OrderNo: " + on + ", Status: " + status);
        dal.UpdateOrderStatus(101, OrderStatus.values[status]);
    }//GEN-LAST:event_jbtnUpdateOrderStatusActionPerformed

    private void jbtnUninitDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUninitDBActionPerformed
        // TODO add your handling code here:
        UninitDB();
    }//GEN-LAST:event_jbtnUninitDBActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Developed by Team3 Vijay.\nThis is for testing Data Access Layer (DAL)");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jbtnAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddOrderActionPerformed
        // TODO add your handling code here:
        ArrayList<OrderDetails> list = new ArrayList<OrderDetails>();
        list.add(new OrderDetails(2, 100));
        list.add(new OrderDetails(3, 200));
        Customer cust = new Customer("vijay", "rachabattuni", "912312879812", "Outer ring road , Bangalore India 55439945");
        Customer cust1 = new Customer("vijay", "rachabattuni", "912312879812", "Bangalore India 55439945");
        
        //dal.AddOrder(null, null);
        //dal.AddOrder(list, cust);
        dal.AddOrder(list, cust1);
    }//GEN-LAST:event_jbtnAddOrderActionPerformed

    private void jbtnAddWidgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddWidgetActionPerformed
        // TODO add your handling code here:ex
        String strWName = jtfWName.getText();
        String strWDec = jtfWDesc.getText();
        int q = Integer.parseInt(jtfWQuant.getText());
        int sid = Integer.parseInt(jtfWStationId.getText());
        dal.AddWidget(strWName, strWDec, q, sid);
    }//GEN-LAST:event_jbtnAddWidgetActionPerformed

    private void jbtnIncWidgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIncWidgetActionPerformed
        // TODO add your handling code here:
        String strWName = jtfIncWName.getText();
        int q = Integer.parseInt(jtfIncBy.getText());
        dal.IncWidgets(strWName,q);
    }//GEN-LAST:event_jbtnIncWidgetActionPerformed

    private void jbtnDecWidgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDecWidgetActionPerformed
        // TODO add your handling code here:
         String strWName = jtfDecWName.getText();
        int q = Integer.parseInt(jtfDecBy.getText());
        dal.DecWidgets(strWName,q);
    }//GEN-LAST:event_jbtnDecWidgetActionPerformed

    private void jbtnGetWQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetWQuantityActionPerformed
        // TODO add your handling code here:
        String name = jtfGetWQuantName.getText();
        int q = 0;
        q = dal.GetWidgetQuantity(name);
        if(q != -9999999)
            System.out.println("DALTest:GetWidgetQuanity success");
        else
            System.out.println("DALTest:GetWidgetQuanity failed");
        jlblWQuantity.setText("Q: " + q);
    }//GEN-LAST:event_jbtnGetWQuantityActionPerformed

    private void jbtnGetPendingOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetPendingOrdersActionPerformed
        // TODO add your handling code here:
            ArrayList<OrderInfo> listOI = dal.GetOrders(OrderStatus.Pending);
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnGetPendingOrdersActionPerformed

    private void jbtnGetAllOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetAllOrdersActionPerformed
        // TODO add your handling code here:
          ArrayList<OrderInfo> listOI = dal.GetOrders(OrderStatus.All);
        for(OrderInfo oi : listOI)
        {
            System.out.println(oi.toString());
        }
    }//GEN-LAST:event_jbtnGetAllOrdersActionPerformed

    private void InitDB() {
        dal = new MySQLDALImpl();
        boolean res = dal.Initialize("127.0.0.1", "root", "");//vijay
        if(res == true)
        {
            JOptionPane.showMessageDialog(this, "DB Init Success11");
        }
        else
            JOptionPane.showMessageDialog(this, "DB Init Failed");
    }
    
     private void UninitDB() {
        boolean res = dal.Uninitialize();
        if(res == true)
        {
            JOptionPane.showMessageDialog(this, "DB Uninit Success");
        }
        else
            JOptionPane.showMessageDialog(this, "DB Uninit Failed");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DALTestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DALTestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DALTestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DALTestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DALTestForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnAddOrder;
    private javax.swing.JButton jbtnAddWidget;
    private javax.swing.JButton jbtnBackorderedOrders;
    private javax.swing.JButton jbtnDecWidget;
    private javax.swing.JButton jbtnGetAllOrders;
    private javax.swing.JButton jbtnGetCustomers;
    private javax.swing.JButton jbtnGetPendingOrders;
    private javax.swing.JButton jbtnGetWQuantity;
    private javax.swing.JButton jbtnGetWidgets;
    private javax.swing.JButton jbtnIncWidget;
    private javax.swing.JButton jbtnInitDB;
    private javax.swing.JButton jbtnOrdersByPhone;
    private javax.swing.JButton jbtnPendingOrders;
    private javax.swing.JButton jbtnShippingOrders;
    private javax.swing.JButton jbtnUninitDB;
    private javax.swing.JButton jbtnUpdateOrderStatus;
    private javax.swing.JComboBox jcbOrderStatus;
    private javax.swing.JLabel jlblWQuantity;
    private javax.swing.JTextField jtfDecBy;
    private javax.swing.JTextField jtfDecWName;
    private javax.swing.JTextField jtfGetWQuantName;
    private javax.swing.JTextField jtfIncBy;
    private javax.swing.JTextField jtfIncWName;
    private javax.swing.JTextField jtfOrderNo;
    private javax.swing.JTextField jtfPhone;
    private javax.swing.JTextField jtfWDesc;
    private javax.swing.JTextField jtfWName;
    private javax.swing.JTextField jtfWQuant;
    private javax.swing.JTextField jtfWStationId;
    // End of variables declaration//GEN-END:variables
}
