import java.io.*;

public class WHRobotMonitor implements Runnable {

	private boolean done = false;
	private boolean arrivalStation = false;
	private boolean robotHealth = true;
	private boolean robotWatchdog = false;
	private WHRobotInf robotInf = null;
	private boolean goCmd = false;
	private boolean cmdReturn = false;
	private int pollingInterval = 1000; //default 1000ms
	
	public WHRobotMonitor(WHRobotInf robotInf) {
		this.robotInf = robotInf;
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
		goCmd = true;
		arrivalStation = false;
		
		return true;
	}
	
	public boolean goNextStationSync() {
		if (!goNextStation())
			return false;
		
		while (!arrivalStation) {
			System.out.println("wait for arrival event");
			try {
				Thread.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
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