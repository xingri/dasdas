package com.lge.monitor;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserverEngineer1 implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
/*		Subject s = (Subject) o; 
		if(s.getStatus().equals("회의실")){
			System.out.println("Eng2: 만화책 자다 일하는 척");
		} else if(s.getStatus().equals("화장실")){
			System.out.println("Eng2: 만화책 자다 일하는 척");
		} else if(s.getStatus().equals("사장님실")){
			System.out.println("Eng2: 만화책 자다 일하는 척");
		}else{
			System.out.println("Eng2: 만화책 보기");
		}*/
		
		// Push Method
		Subject s = (Subject) o; 
		String name = (String) arg;
		if(name.equals("회의실")){
			System.out.println("Eng2: 만화책 보다 일하는 척");
		} else if(name.equals("화장실")){
			System.out.println("Eng2: 만화책 보다 일하는 척");
		} else if(name.equals("사장님실")){
			System.out.println("Eng2: 만화책 보다 일하는 척");
		}else{
			System.out.println("Eng2: 만화책 보기");
		}
			
	}
	
}
