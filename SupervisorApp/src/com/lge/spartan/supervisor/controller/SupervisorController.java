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
import com.lge.spartan.supervisor.data.*;
import com.lge.spartan.supervisor.view.*;

public class SupervisorController implements IController {
    private static volatile SupervisorController obj_instance = null;
    boolean bDBConnectState;    
    
    private SupervisorController () {
        // TODO static DAL d = new DAL();
         bDBConnectState = false;
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
    
    @Override
    public void handleError() {
       
    }            
    
    void setConnectDBState(boolean dbState) {
        bDBConnectState = dbState;
    }
    
    boolean isConnectDB() {
        return bDBConnectState;
    }
    
    /*
     @return param : -2 : disconnected db, -1 : DB query error, 0 : succes
    */
    public int createNewInventory(Widgets newWidget) {
        if (!isConnectDB()) {
            return -2;
        }
               
        int res = 0;
        return res;
    }
    
    public int updateWidgetQuantity(Widgets widgetQuant) {
        if (!isConnectDB()) {
            return -2;
        }
               
        int res = 0;
        return res;
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
    
    public ArrayList<Integer> getWarehouseIdList() {
        // TODO
        
        //ArrayList<Integer> list = new ArrayList<>();
        
        ArrayList <Integer> test = null;
        return test;
    }
}
