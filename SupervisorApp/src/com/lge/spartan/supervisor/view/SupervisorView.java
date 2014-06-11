/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.view;

import java.util.Observable;
import java.util.Observer;

/**9
 *
 * @author gina.du
 */
public abstract class SupervisorView extends javax.swing.JFrame implements Observer{
    public abstract void refreshData();
    
    public void update(Observable o, Object arg) {        
        refreshData();
    }   
}
