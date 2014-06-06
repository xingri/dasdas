
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
public class UpdateWidget extends javax.swing.JFrame {

    ArrayList<Widget> widgetList = null;
    /**
     * Creates new form UpdateWidget
     */
    public UpdateWidget() {
        initComponents();
        
        widgetList = Supervisor.d.GetWidgets();
        if(widgetList == null) return;
        for(Widget w: widgetList)
            comboName.addItem(w.name);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Widget");

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
        textFieldDesc.setEnabled(false);

        textFieldCurQuant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textFieldCurQuant.setEnabled(false);
        textFieldCurQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldCurQuantActionPerformed(evt);
            }
        });

        btnUpdateWidget.setText("Update Widget");
        btnUpdateWidget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateWidgetActionPerformed(evt);
            }
        });

        textFieldStation.setEditable(false);
        textFieldStation.setEnabled(false);

        jLabel5.setText("No. of new items");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUpdateWidget)
                .addGap(155, 155, 155))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldStation)
                    .addComponent(textFieldCurQuant)
                    .addComponent(textFieldDesc)
                    .addComponent(comboName, 0, 147, Short.MAX_VALUE)
                    .addComponent(textFieldNewQuant))
                .addContainerGap(42, Short.MAX_VALUE))
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
                .addGap(33, 33, 33)
                .addComponent(btnUpdateWidget))
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
            if(w.name.equals(comboName.getSelectedItem()))
            {
                textFieldDesc.setText(w.desc);                
                textFieldCurQuant.setText(Integer.toString(w.quantity));
                textFieldStation.setText(Integer.toString(w.stationId + 1));
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
         
        int res = Supervisor.d.IncWidgets((String) comboName.getSelectedItem()
                , Integer.parseInt(textFieldNewQuant.getText()
                ));
        
        if(res == -1)
            JOptionPane.showMessageDialog(this, "Updating a widget failed.\nMay be widget is already existing.\nOr server is not connected.");
        else
            JOptionPane.showMessageDialog(this, "Widget is updated successfully");
    }//GEN-LAST:event_btnUpdateWidgetActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateWidget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateWidget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateWidget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateWidget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateWidget().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateWidget;
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
