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
import com.lge.spartan.data.*;

public class SupervisorController extends Thread/*implements IController*/ {
    private static volatile SupervisorController obj_instance = null;
    private static DAL db;
    boolean bDBConnectState;    
    
    private SupervisorController () {        
        db = new MySQLDALImpl();
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
    
    public boolean connectDB() {
        // TODO                
        //setConnectDBState(db.Initialize("localhost", "root", "1234"));
    	setConnectDBState(db.Initialize("128.237.247.93", "spartan", "spartan"));
        return bDBConnectState;
    }
    
    public boolean isConnectedDB() {
    	setConnectDBState(db.IsDBAvailable());
    	return isConnectDB();
    }
    
    public boolean disconnectDB() {
        // TODO
        setConnectDBState(false);
        return true;        
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
               
        return db.AddWidget(newWidget.getName(), newWidget.getDesc(), newWidget.getQuantity(), newWidget.getStationId(), newWidget.getCost());       
    }
    
    public int updateWidgetQuantity(Widget widgetQuant) {
        if (!isConnectDB()) {
            return -2;
        }
               
        return db.IncWidgets(widgetQuant.getName(), widgetQuant.getQuantity());        
    }
    
    public ArrayList<Widget> getWidgets() {
        // TODO        
        return db.GetWidgets();        
    }
    
    public ArrayList<Customer> getCustomers() {
        // TODO
        return db.GetCustomers();       
    }
    
    public ArrayList<OrderInfo> getAllOrders() {
        // TODO
        return db.GetOrders(OrderStatus.All);       
    }
    
    public ArrayList<OrderInfo> getPendingOrders() {
        // TODO
        return db.GetOrders(OrderStatus.Pending);       
    }
        
    public ArrayList<OrderInfo> getCurProgressOrder() {
    	return db.GetOrders(OrderStatus.Inprogress);
    }
    
    public ArrayList<OrderInfo> getBackorderedOrder() {
    	return db.GetOrders(OrderStatus.Backordered);
    }
    
    public ArrayList <Robot> getAllRobotLists() {
    	return db.GetRobots();
    }
    
    public RobotStatus getRobotStatus(int orderNo){
    	ArrayList <Robot> robotInfo = db.GetRobots();
    	
    	if (robotInfo == null || robotInfo.size() == 0) {
    		return null;
    	}
    	
    	for (Robot rt : robotInfo) {    		
    		RobotStatus rs =  db.GetRobotMoves(rt.getRobotId(), orderNo);
    		if (rs != null) {
    			return rs;
    		}
    	}
    	
    	return null;
    }
    
    public Robot getRobotInfo (int robotId) {
    	ArrayList <Robot> robotInfo = db.GetRobots();
    	
    	if (robotInfo == null || robotInfo.size() == 0) {
    		return null;
    	}
    	
    	for (Robot rt : robotInfo) {    		
    		if (rt.getRobotId() == robotId) {
    			return rt;
    		}
    	}
    	
    	return null;
    }
    
    public String getRobotState(int robotId){
    	return db.GetRobotState(robotId).toString();   	    	
    }
    
    public ArrayList<Integer> getWarehouseIdList() {
        // TODO
        
        //ArrayList<Integer> list = new ArrayList<>();       
        ArrayList <Integer> test = null;
        return test;
    }
    
    public ArrayList<Robot> getErrorRobotList() {
    	ArrayList<Robot> robotLists = getAllRobotLists();
    	
    	if (robotLists == null || robotLists.size() == 0) {
    		return null;
    	}
    	
    	ArrayList<Robot> errRbts = new ArrayList<>(); 
    	for (Robot rt : robotLists) {
    		
    		if (rt.getStatus() == RobotState.Error) {
    			errRbts.add(rt);    			
    		}
    	}
    	
    	return errRbts;
    }
    
    public boolean recoveryRobotError (int robotId) {
    	return db.SetRobotState(robotId, RobotState.Idle);
    }
}
