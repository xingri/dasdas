package com.lge.monitor;

public class ConcreteSubjectSurveillanceCamera extends Subject {

	
	public String getStatus(){
		return status; 
	}
	public void setStatus(String status){
		this.status = status; 
		setChanged();
		notifyObservers(status);
	}
	
}
