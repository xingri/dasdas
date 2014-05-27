package com.lge.monitor;

import java.util.Observable;

public abstract class Subject extends Observable{
	String status; 
	abstract public String getStatus();
	abstract public void setStatus(String status);
}
