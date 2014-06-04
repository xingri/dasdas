package com.lge.spartan.customer;

import java.io.Serializable;

public class Order implements Serializable {
	private String phoneNumber;
	private String address;
	private int quantity;
	private String widgetType;
	private OrderStatus status; 

	public enum OrderStatus {Ordered, Inprocess, Backordered, Completed}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	};
	
	
	
}
