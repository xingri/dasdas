package com.lge.monitor;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserverEngineer1 implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
/*		Subject s = (Subject) o; 
		if(s.getStatus().equals("ȸ�ǽ�")){
			System.out.println("Eng2: ��ȭå �ڴ� ���ϴ� ô");
		} else if(s.getStatus().equals("ȭ���")){
			System.out.println("Eng2: ��ȭå �ڴ� ���ϴ� ô");
		} else if(s.getStatus().equals("����Խ�")){
			System.out.println("Eng2: ��ȭå �ڴ� ���ϴ� ô");
		}else{
			System.out.println("Eng2: ��ȭå ����");
		}*/
		
		// Push Method
		Subject s = (Subject) o; 
		String name = (String) arg;
		if(name.equals("ȸ�ǽ�")){
			System.out.println("Eng2: ��ȭå ���� ���ϴ� ô");
		} else if(name.equals("ȭ���")){
			System.out.println("Eng2: ��ȭå ���� ���ϴ� ô");
		} else if(name.equals("����Խ�")){
			System.out.println("Eng2: ��ȭå ���� ���ϴ� ô");
		}else{
			System.out.println("Eng2: ��ȭå ����");
		}
			
	}
	
}
