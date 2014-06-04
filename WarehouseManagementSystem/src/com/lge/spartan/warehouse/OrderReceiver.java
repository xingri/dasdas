package com.lge.spartan.warehouse;

public class OrderReceiver extends Thread{
	public int test = 5; 
	public void run(){
		System.out.println("OrderServer is not running....");
		while(test > 0){
			System.out.println("OrderReceiving...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			test = test - 1; 
		}
	}
}
