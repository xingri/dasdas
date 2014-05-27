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
		
		subject.setStatus("ȸ�ǽ�");
		subject.setStatus("ȭ���");
		subject.setStatus("����Խ�");
		subject.setStatus("��Ÿ");
	}

}
