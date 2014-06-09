/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gina.du
 */

package com.lge.spartan.supervisor.controller;        

import java.util.ArrayList;
import com.lge.spartan.supervisor.data.Widgets;
import com.lge.spartan.supervisor.data.Customer;
import com.lge.spartan.supervisor.data.OrderInfo;

public class SupervisorController implements IController {
    private static volatile SupervisorController obj_instance = null;
    boolean bDBConnectState = false;
            
    private SupervisorController() {
        //static DAL d = new DAL();
    }
    
    public static SupervisorController getInstance() {
        if (obj_instance == null) {
           synchronized(SupervisorController.class) {
             
           if (obj_instance == null)
                obj_instance = new SupervisorController();
           }
        }
        
        return obj_instance;
    }
    
    @Override
    public boolean connectDB() {
        // TODO
        //Initialize("127.0.0.1", "root", "");
        setConnectDBState(true);
        return false;
    }
    
    @Override
    public boolean disconnectDB() {
        // TODO
        setConnectDBState(false);
        return false;        
    }
    
    void setConnectDBState(boolean dbState) {
        bDBConnectState = dbState;
    }
    
    boolean isConnectDB() {
        return bDBConnectState;
    }
    
    public boolean createNewInventory(Widgets newWidget) {
        if (!isConnectDB()) {
            return false;
        }
               
        return true;
    }
    
    public ArrayList<Widgets> getWidgets() {
        // TODO
        ArrayList <Widgets> test = null;
        return test;
    }
    
    public ArrayList<Customer> getCustomers() {
        // TODO
        ArrayList <Customer> test = null;
        return test;
    }
    
    public ArrayList<OrderInfo> getPendingOrders() {
        // TODO
        ArrayList <OrderInfo> test = null;
        return test;
    }    
}
