package com.lge.spartan.customer.data;

import com.lge.spartan.data.OrderStatus;

public class OrderDisplay {
	private int orderNo;
	private OrderStatus orderStatus;
	private String orderTime;
	private String shippingTime;
	private String details;
	
	private String status; 
	
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		String shipTime = shippingTime == null ? "                                       " : shippingTime;
		String status = orderStatus.toString();
		if(status.equals("Complete"))
			status = "Shipped";
		return "   " + orderNo + 
				"          " +	status + 
				"          " + orderTime + 
				"        " +  shipTime + 
				"    " + details;
	} 	
}
