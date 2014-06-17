package com.lge.spartan.supervisor.view;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.supervisor.controller.SupervisorController;

import java.util.ArrayList;
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
public class UpdateWidgetView extends SupervisorView {

    ArrayList<Widget> widgetList = null;
    /**
     * Creates new form UpdateWidget
     */
    public UpdateWidgetView() {
        // TODO 
        
        initComponents();
        
        widgetList = SupervisorController.getInstance().getWidgets();                
        if(widgetList == null) return;
        for(Widget w: widgetList)
            comboName.addItem(w.getName());
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboName = new javax.swing.JComboBox();
        textFieldDesc = new javax.swing.JTextField();
        textFieldCurQuant = new javax.swing.JTextField();
        btnUpdateWidget = new javax.swing.JButton();
        textFieldStation = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFieldNewQuant = new javax.swing.JTextField();
        closeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Widget Quantity");

        jLabel1.setText("Widget Name");

        jLabel2.setText("Widget Description");

        jLabel3.setText("No. of current items");

        jLabel4.setText("Station");

        comboName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboNameItemStateChanged(evt);
            }
        });
        comboName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNameActionPerformed(evt);
            }
        });

        textFieldDesc.setEditable(false);
        textFieldDesc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textFieldDesc.setEnabled(false);

        textFieldCurQuant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textFieldCurQuant.setEnabled(false);
        textFieldCurQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldCurQuantActionPerformed(evt);
            }
        });

        btnUpdateWidget.setText("Update");
        btnUpdateWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateWidgetActionPerformed(evt);
            }
        });

        textFieldStation.setEditable(false);
        textFieldStation.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textFieldStation.setEnabled(false);

        jLabel5.setText("No. of new items");

        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(btnUpdateWidget))
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldStation)
                    .addComponent(textFieldCurQuant)
                    .addComponent(textFieldDesc)
                    .addComponent(comboName, 0, 147, Short.MAX_VALUE)
                    .addComponent(textFieldNewQuant)
                    .addComponent(closeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(comboName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addComponent(textFieldDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldCurQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textFieldNewQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textFieldStation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateWidget)
                    .addComponent(closeBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldCurQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldCurQuantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldCurQuantActionPerformed

    private void comboNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNameActionPerformed

    private void comboNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboNameItemStateChanged
        // TODO add your handling code here:
        for(Widget w: widgetList)
        {
            if(w.getName().equals(comboName.getSelectedItem()))
            {
                textFieldDesc.setText(w.getDesc());                
                textFieldCurQuant.setText(Integer.toString(w.getQuantity()));
                textFieldStation.setText(Integer.toString(w.getStationId() + 1));
                break;
            }
        }
    }//GEN-LAST:event_comboNameItemStateChanged

    private void btnUpdateWidgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateWidgetActionPerformed
        // TODO add your handling code here:
        if (textFieldNewQuant.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter the Quantity of the Widget.");
            textFieldNewQuant.requestFocus();
        } else {
            try {
                Integer.parseInt(textFieldNewQuant.getText());
            } catch (NumberFormatException e) {
                //Not an integer
                JOptionPane.showMessageDialog(this, "Please enter the Quantity as Numeric.");
                textFieldNewQuant.requestFocus();
            }
        }        
        
        Widget widget = (Widget)widgetList.get(comboName.getSelectedIndex());
        widget.setQuantity (Integer.parseInt(textFieldNewQuant.getText()));
                
        int res = SupervisorController.getInstance().updateWidgetQuantity(widget);
        if(res == -1)
            JOptionPane.showMessageDialog(this, "Updating a widget failed.\nMay be widget is already existing.\nOr server is not connected.");
        else if (res == -2)
            JOptionPane.showMessageDialog(this, "DB Connection Fail. Could you check DB Connection");
        else
            JOptionPane.showMessageDialog(this, "Widget is updated successfully");
    }//GEN-LAST:event_btnUpdateWidgetActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closeBtnActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateWidgetView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateWidgetView().setVisible(true);
            }
        });
    }
    
    public void refreshData() {
        // TODO
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateWidget;
    private javax.swing.JButton closeBtn;
    private javax.swing.JComboBox comboName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField textFieldCurQuant;
    private javax.swing.JTextField textFieldDesc;
    private javax.swing.JTextField textFieldNewQuant;
    private javax.swing.JTextField textFieldStation;
    // End of variables declaration//GEN-END:variables
}
