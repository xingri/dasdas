package com.lge.spartan.supervisor.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.lge.spartan.data.Robot;
import com.lge.spartan.supervisor.controller.SupervisorController;

public class MessageView extends SupervisorView {
	String errorMessage = "";
	ArrayList <DisplayErrorData> errorData;
	
	boolean bNoBtnOption = false;
	
	public enum  eErrorType {
		eDBConnection,
		eWareHouse,
		eRobot,
		eMaxErrorType,
	}
	
	public class DisplayErrorData {
		boolean bErrorOccured;
		int		nErrorWaitCnt;
		
		public DisplayErrorData() {
			reset();
		}
		
		public void reset() {
			bErrorOccured = false;
			nErrorWaitCnt = 0;
		}
	};
	
	public MessageView() {
		errorData = new ArrayList<> ();
		DisplayErrorData dbData = new DisplayErrorData();
		DisplayErrorData whData = new DisplayErrorData();
		DisplayErrorData rbtData = new DisplayErrorData();		
		
		errorData.add(dbData);
		errorData.add(whData);
		errorData.add(rbtData);
	}	
	
	final	int	nMaxErrorWaitCnt = 60;
	
	@Override
	public void refreshData() {
		errorMessage = "";
		
		if (isDisplayErrorMessage()) {
			
			if (bNoBtnOption) {			
				
				int res = JOptionPane.showOptionDialog(this, errorMessage,
								            "Error", JOptionPane.DEFAULT_OPTION,
								            JOptionPane.ERROR_MESSAGE, null, new Object[] {},
								            null);				
				
				if (res == JOptionPane.CLOSED_OPTION) {
					System.exit(0);
				}
				
			} else {
				JOptionPane.showMessageDialog(this, errorMessage, "Error", -1);
			}			
		}
	}

	private boolean isDisplayErrorMessage() {
		if (isDisplayDBError()) {
			bNoBtnOption = true;
			return true;
		}
		
		if (!isDBConnectionError() && isDisplayWHError()) {
			bNoBtnOption = false;
			return true;
		} 
		
		if (!isDBConnectionError() && !isWarehouseSystemError() && isDisplayRobotError()) {
			bNoBtnOption = false;
			return true;
		}
		
		return false;		
	}
	
	public boolean isDisplayDBError() {
		return isDBConnectionError();
		/*DisplayErrorData dbError = errorData.get(eErrorType.eDBConnection.ordinal());
		if (isDBConnectionError()) {					
			dbError.bErrorOccured = true;
			
			if (dbError.bErrorOccured && dbError.nErrorWaitCnt == 0) {
				//< "0"인 경우에만 popup이 표시가 되도록
				dbError.nErrorWaitCnt++;				
				errorData.set(eErrorType.eDBConnection.ordinal(), dbError);
				return true;
			}
			
			dbError.nErrorWaitCnt++;			
			if (dbError.nErrorWaitCnt > nMaxErrorWaitCnt) {
				dbError.nErrorWaitCnt = 0;
				dbError.bErrorOccured = false;
			}			
		} else {			
			dbError.nErrorWaitCnt = 0;
			dbError.bErrorOccured = false;
		}
		
		errorData.set(eErrorType.eDBConnection.ordinal(), dbError);
		return false;*/		
	}
	
	public boolean isDisplayWHError() {
		DisplayErrorData hwError = errorData.get(eErrorType.eWareHouse.ordinal());
		if (isWarehouseSystemError()) {
			hwError.bErrorOccured = true;
			
			if (hwError.bErrorOccured && hwError.nErrorWaitCnt == 0) {
				//< "0"인 경우에만 popup이 표시가 되도록
				hwError.nErrorWaitCnt++;				
				errorData.set(eErrorType.eWareHouse.ordinal(), hwError);
				return true;
			}
			
			hwError.nErrorWaitCnt++;			
			if (hwError.nErrorWaitCnt > nMaxErrorWaitCnt) {
				hwError.nErrorWaitCnt = 0;
				hwError.bErrorOccured = false;
			}
		} else {
			hwError.nErrorWaitCnt = 0;
			hwError.bErrorOccured = false;
		}
		
		errorData.set(eErrorType.eWareHouse.ordinal(), hwError);
		return false;
	}
	
	public boolean isDisplayRobotError() {		
		DisplayErrorData robotError = errorData.get(eErrorType.eRobot.ordinal());
		if (isRobotError()) {					
			robotError.bErrorOccured = true;	
			
			if (robotError.bErrorOccured && robotError.nErrorWaitCnt == 0) {
				//< "0"인 경우에만 popup이 표시가 되도록
				robotError.nErrorWaitCnt++;				
				errorData.set(eErrorType.eRobot.ordinal(), robotError);				
				return true;
			}
			
			robotError.nErrorWaitCnt++;			
			if (robotError.nErrorWaitCnt > nMaxErrorWaitCnt) {
				robotError.nErrorWaitCnt = 0;
				robotError.bErrorOccured = false;
			}			
		} else {
			robotError.nErrorWaitCnt = 0;
			robotError.bErrorOccured = false;
		}
		
		errorData.set(eErrorType.eRobot.ordinal(), robotError);
		return false;
	}
	
	private boolean isDBConnectionError() {
		if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage = "There is a problem with the database connection.\nAfter resolving the problem.\nPlease re-operation.";					
			return true;
		}
		
		return false;
	}
	
	private boolean isWarehouseSystemError() {
		if (!SupervisorController.getInstance().isConnectedDB()) {
			errorMessage = "Warehouse Management System maybe has some problems.\nCould you check Warehouse Management System\n\n";
			return true;
		}
		
		return false;
	}
	
	private boolean isRobotError() {
		errorMessage = SupervisorController.getInstance().getRobotErrorStr();
		
		if (errorMessage == null || errorMessage.length() == 0) {				
			return false;
		}
		
		return true;
	}
}
