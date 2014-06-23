package com.lge.spartan.supervisor.view;

import javax.swing.JOptionPane;

import com.lge.spartan.supervisor.controller.SupervisorController;

public class MessageView extends SupervisorView {
	String errorMessage = "";
	
	@Override
	public void refreshData() {
		errorMessage = "";
		
		checkError();
		
		if (!errorMessage.isEmpty()) {
			JOptionPane.showMessageDialog(this, errorMessage, "Error", -1);
		}
	}
	
	private void checkError() {
		if (checkDBConnection()) {
			return;
		} 		
	}
	
	private boolean checkDBConnection() {
		if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage += "Cannot connect Database System.\nCould you check Database System\n\n";
			return false;
		}
		
		return true;
	}
	
	private void checkWarehouseSystem() {
		//if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage += "Warehouse Management System maybe has some problems.\nCould you check Warehouse Management System\n\n";			
		//}
	}
}
