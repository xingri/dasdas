package com.lge.spartan.supervisor.view;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.lge.spartan.supervisor.controller.*;
import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
public class SupervisorMainView extends SupervisorView {
    
    /**
     * Creates new form Supervisor
     */
    public SupervisorMainView() {
        initComponents();
        updateWidgetLists();
    }

    /*protected void finalize() throws Throwable {
        try {
            d.Uninitialize();
        } finally {
            super.finalize();
        }
    }

    public void dispose() {
        d.Uninitialize();
        super.dispose();
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbtnRefresh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTblMonitor = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jbtnOrderRefresh = new javax.swing.JButton();
        jbtnAddOrder = new javax.swing.JButton();
        jlblOrderNo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jBtnAddWidgetQnt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supervisor");

        jButton2.setText("Create New Widget");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S No", "Widget Name", "Widget Description", "Quantity", "StationId"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        jbtnRefresh.setText("Refresh");
        jbtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnRefresh)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jbtnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventory", jPanel1);

        jTblMonitor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Warehouse\nId", "Robot Id", "Robot Status", "Order No.", "Widget\nInfo", "Stn visited", "Cur Stn", "Next Stn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTblMonitor);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Monitor", jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S No", "Order No", "Submit Date", "Status", "Shipped Date", "Widget Name", "Widget Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setMinWidth(50);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable3.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jbtnOrderRefresh.setText("Refresh");
        jbtnOrderRefresh.setToolTipText("");
        jbtnOrderRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOrderRefreshActionPerformed(evt);
            }
        });

        jbtnAddOrder.setText("AddDummyOrders");
        jbtnAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddOrderActionPerformed(evt);
            }
        });

        jlblOrderNo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlblOrderNo.setText("        ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jbtnOrderRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnAddOrder))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jlblOrderNo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnOrderRefresh)
                    .addComponent(jbtnAddOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jlblOrderNo)
                .addGap(258, 258, 258))
        );

        jTabbedPane1.addTab("Orders", jPanel4);

        jBtnAddWidgetQnt.setText("Add Widget Quantity");
        jBtnAddWidgetQnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddWidgetQntActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S No", "Order No", "Widget Name", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBtnAddWidgetQnt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(397, 397, 397))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnAddWidgetQnt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Backordered order", jPanel3);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Connect");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("DB");
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("WH Server");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem4.setText("About");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 44, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        AddWidgetView aw = new AddWidgetView();
        aw.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBtnAddWidgetQntActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        UpdateWidgetView aw = new UpdateWidgetView();
        aw.setVisible(true);
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void updateWidgetLists() {
        ArrayList<Widget> widgetList = SupervisorController.getInstance().getWidgets();
        if (widgetList == null) {            
            return;
        }
        
        ClearTable(jTable1);
        int i = 0;
        for (Widget w : widgetList) {
            i++;
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{i, w.getName(), w.getDesc(), w.getQuantity(), w.getStationId()});
        }
    }
    
    public void refreshData() {
        // TODO
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:                
                updateWidgetLists();
                break;
            case 1:
            	updateCurOrderStatus();
            	break;
            case 2:
                updateOrderLists();
                break;
            case 3:
            	updateBackorderStatus();
            	break;
            default:
            	break;
        } 
    }
    
    private void jbtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRefreshActionPerformed
        // TODO add your handling code here:
        updateWidgetLists();
    }//GEN-LAST:event_jbtnRefreshActionPerformed

    
    /*private void jbtnCustRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCustRefreshActionPerformed
        // TODO add your handling code here:
        ArrayList<Customer> custList = SupervisorController.getInstance().getCustomers();
        
        if (custList == null) {
            return;
        }
        ClearTable(jTable2);
        int i = 0;
        for (Customer c : custList) {
            i++;
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.addRow(new Object[]{i, c.getFname(), c.getLname(), c.getPhone(), c.getAddress()});
        }
    }//GEN-LAST:event_jbtnCustRefreshActionPerformed
*/
    
    private void updateBackorderStatus() {
    	ArrayList<OrderInfo> orderList = SupervisorController.getInstance().getBackorderedOrder();    			
        if (orderList == null) {
        	return;
        }
        
        if (orderList.size() == 0) {            
        	ClearTable(jTable2);
     	   	return;
        }
        
        DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        int i = 0;
        for (OrderInfo oi : orderList) {
            i++;
            //DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            ArrayList <OrderDetails> ordDetails = oi.getListOrderDetails();   
            for (OrderDetails ordDetail : ordDetails) {
            	/*model*/dtm.addRow(new Object[]{i, oi.getOrderNo(),ordDetail.getWidgetName() 
            			, ordDetail.getQuantity(), oi.getOrderTime()});
            }
        }
    }
    
    private void updateCurOrderStatus() {
    	DefaultTableModel dtm = (DefaultTableModel) jTblMonitor.getModel();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
               
    	ArrayList<OrderInfo> processOrderLists = SupervisorController.getInstance().getCurProgressOrder();
    	if (processOrderLists == null || processOrderLists.size() == 0) {
    	
    	   ArrayList <Robot> robotLists = SupervisorController.getInstance().getAllRobotLists();
    	   SupervisorController.getInstance().getAllRobotLists();
    	   
    	   if (robotLists != null && robotLists.size() != 0) {
    		   for (Robot rbt : robotLists) {
    			   /*model*/dtm.addRow(new Object[]{
	            		   	rbt.getWarehouseId()
	            		  , rbt.getRobotId(), rbt.getStatus().toString()       		   
	            		   });
    		   }
    	   }
    	   
    	   return;
    	}       
       
              
       for (OrderInfo oi : processOrderLists) {
           RobotStatus rbtStatus = SupervisorController.getInstance().getRobotStatus(oi.getOrderNo());
           if (rbtStatus == null) {
        	   //ClearTable(jTblMonitor);
        	   return;
           }
           
           Robot robotInfo = SupervisorController.getInstance().getRobotInfo(rbtStatus.getRobotId());
           if (robotInfo == null) {
        	   //ClearTable(jTblMonitor);
        	   return;
           }           
           
           String orderDetails = "";
           for (OrderDetails orDetail : oi.getListOrderDetails()) {
        	   orderDetails += orDetail.toString();
        	   orderDetails += "\n";
           }
           
           /*model*/dtm.addRow(new Object[]{
        		   robotInfo.getWarehouseId()
        		   , robotInfo.getRobotId(), robotInfo.getStatus().toString()       		   
        		   , rbtStatus.getOrderNo()
        		   , orderDetails
        		   , rbtStatus.getStationsVisited() == null ? "" : rbtStatus.getStationsVisited().toString()
        		   , rbtStatus.getCurrentStation()
        		   , rbtStatus.getNextStation()
        		   });
       }
    }
    
    private void updateOrderLists() {
        final ArrayList<OrderInfo> orderList = SupervisorController.getInstance().getAllOrders();                
        if (orderList == null) {            
        	return;
        }
        
        DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
        int i = 0;
        for (OrderInfo oi : orderList) {
            i++;
            //DefaultTableModel model = (DefaultTableModel) jTable3.getModel();

            ArrayList <OrderDetails> ordDetails = oi.getListOrderDetails();
            
            for (OrderDetails ord : ordDetails) {
            	/*model*/dtm.addRow(new Object[]{i, oi.getOrderNo(), oi.getOrderTime(), oi.getStatus(),                    
                        oi.getShippingTime() != null ? oi.getShippingTime() : ' ',
                        ord.getWidgetName(), ord.getQuantity(),
                        oi.getCust() != null ? oi.getCust().getFname() : ' ',
                        oi.getCust() != null ? oi.getCust().getPhone() : ' '});
            }
        }
        
        /*jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (orderList == null) {
                    return;
                }
                
                ClearTable(jTable4);
                String sorderNo = jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString();
                int orderNo = Integer.parseInt(sorderNo);
                jlblOrderNo.setText(sorderNo);
                for (OrderInfo oi : orderList) {
                    if (oi.getOrderNo() != orderNo) {
                        continue;
                    }
                    FillOrderDetails(oi);
                }
            }

            private void FillOrderDetails(OrderInfo oi) {
                DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
                int ii = 0;
                
                for (OrderDetails od : oi.getListOrderDetails()) {
                    model.addRow(new Object[]{ii++, od.getWidgetId(), od.getWidgetName(), od.getQuantity()});
                }
            }
        }
        );*/
    }
    
    private void jbtnOrderRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOrderRefreshActionPerformed
        // TODO add your handling code here:
        updateOrderLists();
    }//GEN-LAST:event_jbtnOrderRefreshActionPerformed

    private void ClearTable(javax.swing.JTable jtable) {
        DefaultTableModel dtm = (DefaultTableModel) jtable.getModel();
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
    }

    private void jbtnAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddOrderActionPerformed
        // TODO add your handling code here:
        ArrayList<OrderDetails> list = new ArrayList<>();
        
        // TODO
        /*for (int i = 0; i < 4; i++) {
            OrderDetails od = new OrderDetails();
            od.setQuantity() = i * 4;
            od.setWidgetId() = (i % 4) + 1;
            list.add(od);
        }
        
        SupervisorController.getInstance().addOrderForTest(list, "amar", "rachabattuni", "1-123-2344", "NewYork");
        */
    }//GEN-LAST:event_jbtnAddOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAddWidgetQnt;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTblMonitor;
    private javax.swing.JButton jbtnAddOrder;
    private javax.swing.JButton jbtnOrderRefresh;
    private javax.swing.JButton jbtnRefresh;
    private javax.swing.JLabel jlblOrderNo;
    // End of variables declaration//GEN-END:variables
}
