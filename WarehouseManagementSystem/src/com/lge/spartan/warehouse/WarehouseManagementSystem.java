package com.lge.spartan.warehouse;

public class WarehouseManagementSystem {
	private OrderReceiver orderReceiver; 
	
	public void init(){
		System.out.println("WarehouseManagementSystem Initializing... ");
		orderReceiver = new OrderReceiver();
		orderReceiver.run(); 
	}
}
