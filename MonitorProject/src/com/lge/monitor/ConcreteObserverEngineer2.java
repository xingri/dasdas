package com.lge.monitor;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserverEngineer2 implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
/*		Subject s = (Subject) o; 
		if(s.getStatus().equals("ȸ�ǽ�")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		} else if(s.getStatus().equals("ȭ���")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		} else if(s.getStatus().equals("����Խ�")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		}else{
			System.out.println("Eng1: ������ �ڱ�");
		}
		
*/		
		Subject s = (Subject) o; 
		String name = (String)arg;
		if(name.equals("ȸ�ǽ�")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		} else if(name.equals("ȭ���")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		} else if(name.equals("����Խ�")){
			System.out.println("Eng1: ������ �ڴ� ���ϴ� ô");
		}else{
			System.out.println("Eng1: ������ �ڱ�");
		}
	}
	


}
