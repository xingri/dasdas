package com.lge.spartan.customer.data;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
	private String phoneNumber;
	private String address;
	List<OrderWidgetTuple> widgets; 

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
	
	public List<OrderWidgetTuple> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<OrderWidgetTuple> widgets) {
		this.widgets = widgets;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [phoneNumber=" + phoneNumber + ", address=" + address
				+ ", widgets=" + widgets + ", status=" + status + "]";
	};
	
	
}
