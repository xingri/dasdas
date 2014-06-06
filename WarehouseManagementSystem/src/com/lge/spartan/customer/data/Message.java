package com.lge.spartan.customer.data;

import java.io.Serializable;

public class Message implements Serializable{
	int msgType; 
	Object payload;
	
	public static final int SUBMIT_ORDER = 1; 
	public static final int GET_WIDGET_TYPE = 2; 
	public static final int GET_ORDER_STATUS = 3; 
	
	public Message(int msgType, Object payload){
		this.msgType = msgType; 
		this.payload = payload; 
	}
	
	public int getMsgType() {
		return msgType;
	}
	
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	public Object getPayload() {
		return payload;
	}
	
	public void setPayload(Object payload) {
		this.payload = payload;
	} 
	
}
