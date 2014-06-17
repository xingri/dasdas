import java.io.*;

public class WHRobotMonitor implements Runnable {
	public static final boolean NORMAL_CMD = true;
	public static final boolean MANUAL_CMD = false;
	
	private boolean done = false;
	private String robotIP;
	private int	portNum;				
	private boolean arrivalStation = false;
	private boolean robotHealth = true;
	private boolean robotWatchdog = false;
	private WHRobotInf robotInf = null;
	private WHRobotManualCommander manualCommander = null;
	private boolean goCmd = false;
	private boolean commandMode = NORMAL_CMD;
	private boolean cmdReturn = false;
	private int pollingInterval = 1000; //default 1000ms
	
	public WHRobotMonitor(String robotIP, int portNum) {
		this.robotIP = robotIP;
		this.portNum = portNum;
		robotInf = new WHRobotInf(robotIP, portNum);
		manualCommander = new WHRobotManualCommander(this);
		Thread manualThread = new Thread(manualCommander);
		manualThread.start();
	}
	
	public boolean isArrivalStation() {
		return arrivalStation;
	}

	public boolean isRobotHealth() {
		return robotHealth;
	}

	public boolean catchRobotDeath() {
		while (robotHealth) {
			try {
				Thread.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean isRobotWatchdog() {
		return robotWatchdog;
	}
	
	public boolean catchRobotWatchdog() {
		while (robotWatchdog) {
			try {
				Thread.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean goNextStation() {
		if (!robotHealth) {
			System.out.println("Can't processe command due to abnormal robot state");
			return false;
		}
		
		if (commandMode == MANUAL_CMD) {
			System.out.println("Can't processe command while MANUAL COMMAND mode");
			return false;
		}
		
		goCmd = true;
		arrivalStation = false;
		
		return true;
	}
	
	public boolean goNextStationSync() {
		if (!goNextStation())
			return false;
		
		while (!arrivalStation) {
			if (commandMode == MANUAL_CMD) {
				System.out.println("Exit waiting arrival event because of changing to MANUAL Command mode");
				return false;
			}
			
			System.out.println("wait for arrival event");
			try {
				Thread.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean goFoward(int step) {
		if (step == 0)
			return true;
		return robotInf.goFoward(step);
	}
	
	public boolean goBackward(int step) {
		if (step == 0)
			return true;
		return robotInf.goBackward(step);
	}
	
	public boolean turnLeft(int step) {
		if (step == 0)
			return true;
		return robotInf.turnLeft(step);
	}

	public boolean turnRight(int step) {
		if (step == 0)
			return true;
		return robotInf.turnRight(step);
	}
	
	public boolean stop() {
		commandMode = MANUAL_CMD;
		return robotInf.stop();
	}
	
	public void convertNormalMode() {
		commandMode = NORMAL_CMD;
	}
	
	public int getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public boolean isCmdProcessed() {
		return cmdReturn;
	}
	
	public boolean isRobotConnected() {
		boolean ret = false;
		if (robotInf != null) 
			ret = robotInf.isRobotServerStatus();
		return ret;
	}

	public WHRobotInf getRobotInf() {
		return robotInf;
	}

	@Override
	public void run() {
		while (!done) {
			arrivalStation = robotInf.getArrival();
			robotHealth = robotInf.getHealth();
			robotWatchdog = robotInf.getWatchdog();
			
			if (goCmd) {
				cmdReturn = robotInf.goNextStation();
				goCmd = false;
			}
			
			try {
				Thread.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Monitor Thread Terminated");
	}
}