package com.lge.spartan.supervisor.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.lge.spartan.data.Robot;
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
		if (isDBConnectionError() || isWarehouseSystemError()) {
			return;
		}
		
		isRobotError();
	}
	
	private boolean isDBConnectionError() {
		if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage += "Cannot connect Database System.\nCould you check Database System\n\n";
			return true;
		}
		
		return false;
	}
	
	private boolean isWarehouseSystemError() {
		if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage += "Warehouse Management System maybe has some problems.\nCould you check Warehouse Management System\n\n";
			return true;
		}
		
		return false;
	}
	
	private boolean isRobotError() {
		ArrayList<Robot> errRbts = SupervisorController.getInstance().getErrorRobotList();
		
		if (errRbts == null || errRbts.size() == 0) {
			return false;
		}
		
		return true;
	}
}
