package com.lge.spartan.supervisor.view;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.lge.spartan.supervisor.controller.SupervisorController;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
public class AddWidgetView extends SupervisorView {

    /**
     * Creates new form AddWidget
     */
    public AddWidgetView() {
        initComponents();
	//updateWarehouseId();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        stationId = new javax.swing.JComboBox();
        createWidget = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        warehouseId = new javax.swing.JComboBox();
        closebtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        widgetCost = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create New Widget");

        jLabel1.setText("Widget Name");

        jLabel2.setText("Widget Description");

        jLabel3.setText("Quantity");

        jLabel4.setText("Station");

        stationId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3" }));

        createWidget.setText("Create");
        createWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createWidgetActionPerformed(evt);
            }
        });

        jLabel5.setText("Warehouse ID");

        warehouseId.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1"}));

        closebtn.setText("Close");
        closebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtnActionPerformed(evt);
            }
        });

        jLabel6.setText("Cost");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(createWidget, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(desc, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(warehouseId, 0, 156, Short.MAX_VALUE)
                                    .addComponent(stationId, 0, 156, Short.MAX_VALUE)
                                    .addComponent(widgetCost))))
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(widgetCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(warehouseId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(stationId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createWidget)
                    .addComponent(closebtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createWidgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createWidgetActionPerformed
        // TODO add your handling code here:
        if (name.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter the Name of the Widget.");
            name.requestFocus();
            return;
        } else if (desc.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter the Description of the Widget.");
            desc.requestFocus();
            return;
        } else if (quantity.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter the Quantity of the Widget.");
            quantity.requestFocus();
            return;
        } else if (widgetCost.getText().length() <= 0) {
        	JOptionPane.showMessageDialog(this, "Please enter the Cost of the Widget.");
        	widgetCost.requestFocus();
        	return;
        } else {
            try {
                Integer.parseInt(quantity.getText());
            } catch (NumberFormatException e) {
                //Not an integer
                JOptionPane.showMessageDialog(this, "Please enter the Quantity as Numeric.");
                quantity.requestFocus();
                return;
            }
            
            try {
                Double.parseDouble(widgetCost.getText());
            } catch (NumberFormatException e) {
                //Not an integer
                JOptionPane.showMessageDialog(this, "Please enter the Cost as Numeric.");
                widgetCost.requestFocus();
                return;
            }
        }

        Widget addInventory  = new Widget ();
        
        addInventory.setName(name.getText());
        addInventory.setDesc(desc.getText());
        addInventory.setQuantity(Integer.parseInt(quantity.getText()));
        addInventory.setStationId(stationId.getSelectedIndex() + 1);
        addInventory.setCost(Double.parseDouble(widgetCost.getText()));
        
        int res = SupervisorController.getInstance().createNewInventory(addInventory);
        
        if (res == -1)
            JOptionPane.showMessageDialog(this, "Adding a widget failed.\nMay be widget is already existing.\nOr server is not connected.");
        else if (res == -2) 
            JOptionPane.showMessageDialog(this, "Adding a widget failed.\nMay be widget is already existing.\nOr server is not connected.");
        else
            JOptionPane.showMessageDialog(this, "Widget is added successfully.");
    }//GEN-LAST:event_createWidgetActionPerformed

    private void closebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtnActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closebtnActionPerformed

    public void updateWarehouseId() {        
        /*ArrayList<Integer> warehouseIdList = SupervisorController.getInstance().getWarehouseIdList();
        
        for (Integer id : warehouseIdList) {
           warehouseId.addItem(Integer.toString(id));            
        }*/
    }
    
    public void updateStationId(int warehoudId) {
        /*ArrayList<Integer> warehouseIdList = SupervisorController.getInstance().getAddWarehouseIdList();
        
        for (Integer id : warehouseIdList) {
           warehouseId.addItem(Integer.toString(id));            
        }*/
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
            java.util.logging.Logger.getLogger(AddWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddWidgetView().setVisible(true);
            }
        });       
        
    }
    
    public void refreshData() {
        // TODO        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebtn;
    private javax.swing.JButton createWidget;
    private javax.swing.JTextField desc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField widgetCost;
    private javax.swing.JTextField name;
    private javax.swing.JTextField quantity;
    private javax.swing.JComboBox stationId;
    private javax.swing.JComboBox warehouseId;
    // End of variables declaration//GEN-END:variables
}
