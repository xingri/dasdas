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
import com.lge.spartan.supervisor.view.*;

import com.lge.spartan.dal.*;

public class SupervisorController implements IController {
    private static volatile SupervisorController obj_instance = null;
    private static DAL dal = null;
    boolean bDBConnectState;    
    
    private SupervisorController () {
        dal = new DAL();
        connectDB();        
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
        setConnectDBState(dal.Initialize("localhost", "root", "1234"));
        return bDBConnectState;
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
    public int createNewInventory(Widget newWidget) {
        if (!isConnectDB()) {
            return -2;
        }
               
        int res = 0;
        return res;
    }
    
    public int updateWidgetQuantity(Widget widgetQuant) {
        if (!isConnectDB()) {
            return -2;
        }
               
        int res = 0;
        return res;
    }
    
    public ArrayList<Widget> getWidgets() {
        // TODO
        ArrayList <Widget> test = null;
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
