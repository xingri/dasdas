package com.lge.spartan.warehouse.order;

public class WarehouseManagementSystem {
	private OrderReceiver orderReceiver; 
	
	public void init(){
		System.out.println("WarehouseManagementSystem Initializing... ");
		orderReceiver = new OrderReceiver();
		orderReceiver.run(); 
	}
}
