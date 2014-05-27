package com.lge.monitor;

import java.util.Observer;

public class MonitorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subject subject  = new ConcreteSubjectSurveillanceCamera();
		
		Observer eng1 = new ConcreteObserverEngineer1();
		Observer eng2 = new ConcreteObserverEngineer2();
		
		subject.addObserver(eng1);
		subject.addObserver(eng2);
		
		subject.setStatus("회의실");
		subject.setStatus("화장실");
		subject.setStatus("사장님실");
		subject.setStatus("기타");
	}

}
