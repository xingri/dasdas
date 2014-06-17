import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class WHRobotManualCommander implements Runnable {
	private boolean done = false;			// Loop flag.
	private Integer userCommand	= -1;		// User selection from the menu
	private BufferedReader UserInput; 		// User keyboard input
	private WHRobotMonitor monitor;
	private int step = 1;
	
	public WHRobotManualCommander(WHRobotMonitor monitor) {
		this.monitor = monitor;
		UserInput = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		monitor.setPollingInterval(10000);
		while (!done) {
			boolean ret = true;
			
			System.out.println ( "\nPlease select a command:\n" );
			System.out.println ("Current Step : " + step);
			System.out.println ( "1.	Stop" );
			System.out.println ( "2.	Forward" );
			System.out.println ( "3.	Backward" );
			System.out.println ( "4.	Jog Right" );
			System.out.println ( "5.	Jog Left" );
			System.out.println ( "8.	See System Health Status" );
			System.out.println ( "9.	See Watchdog Status" );
			System.out.println ( "10.	Set Movement Step" );
			System.out.println ( "0.	Convert to Normal Mode" );
			System.out.print ( ">>>> " );

			try {
				userCommand = userCommand.valueOf(UserInput.readLine());
			} catch (Exception e) {

			}

			switch (userCommand) {
			case 0:
				monitor.convertNormalMode();
				break;
			case 1:
				ret = monitor.stop();
				if (ret)
					System.out.println("okay");
				break;
			case 2:
				ret = monitor.goFoward(step);
				if (ret)
					System.out.println("okay");
				break;
			case 3:
				ret = monitor.goBackward(step);
				if (ret)
					System.out.println("okay");
				break;
			case 4:
				ret = monitor.turnRight(step);
				if (ret)
					System.out.println("okay");
				break;
			case 5:
				ret = monitor.turnLeft(step);
				if (ret)
					System.out.println("okay");
				break;
			case 8:
				ret = monitor.isRobotHealth();
				System.out.println("health : " + ret);
				break;
			case 9:
				ret = monitor.isRobotWatchdog();
				System.out.println("watchdog : " + ret);
				break;
			case 10:
				System.out.println("Please input step...");
				try {
					step = userCommand.valueOf(UserInput.readLine());
				} catch (Exception e) {

				}
				break;
			default:
				System.out.println( "Invalid Command...\n" );
			}
		}
	}
}
