package com.lge.spartan.customer.data;



public class OrderDisplay {
	private int orderNo;
	private int orderStatus;
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
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
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
		return orderNo + "   " + 
				((orderStatus == 3) ? "Shipeed" : "Pending") + 
				"   " + orderTime + 
				"   " + shippingTime + 
				"   " + details;
	} 
	
	
	
}