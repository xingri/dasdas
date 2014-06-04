package com.lge.spartan.customer.data;


import java.io.Serializable;

public class OrderWidgetTuple implements  Serializable{
	public String name; 
	public int quantity; 
	
	public OrderWidgetTuple(String name, int quantity){
		this.name = name;
		this.quantity = quantity; 
	}

	@Override
	public String toString() {
		return name + "   " + quantity;
	
	}
	
}
