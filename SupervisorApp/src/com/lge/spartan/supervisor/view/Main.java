/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.view;

import com.lge.spartan.dal.DAL;
import com.lge.spartan.dal.MySQLDALImpl;
import com.lge.spartan.supervisor.controller.DBNotifier;
import com.lge.spartan.supervisor.controller.SupervisorController;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gina.du
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        
        SupervisorMainView sprView = new SupervisorMainView ();
        
        final DBNotifier dbNotifier = new DBNotifier();
        dbNotifier.registerObserver(sprView);        
        sprView.setVisible(true); 
        
        Thread notifierThread = new Thread () {
            public void run () {
                while (true) {
                    dbNotifier.notifyObservers();

                    try {                
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DBNotifier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
            }
        };
                
        notifierThread.start();   
    }
}
