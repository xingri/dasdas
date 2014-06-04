public class StationManager {

    String robotIP;

    // SS goes to < 600 when robot arrived
    int SS1, SS2, SS3, SS4;

    // SW goes to 0 when it pushed
    int SW1, SW2, SW3, SW4;

    public StationManager(String robotIP) {
        this.robotIP = robotIP;
    }

    public void eventHandler(String inputLine) {

        System.out.println("check msg: " + inputLine);
        String[] sensList = inputLine.split(",");

        SS1 = Integer.parseInt(sensList[0]);
        SS2 = Integer.parseInt(sensList[1]);
        SS3 = Integer.parseInt(sensList[2]);
        SS4 = Integer.parseInt(sensList[3]);

        SW1 = Integer.parseInt(sensList[4]);
        SW2 = Integer.parseInt(sensList[5]);
        SW3 = Integer.parseInt(sensList[6]);
        SW4 = Integer.parseInt(sensList[7]);

        RobotControl rb = new RobotControl();

        //if one of Switch (SW1~SW4) is pressed(!=1) then need to call Go Next Station
        if( (SW1!=1) || (SW2!=1) || (SW3!=1) || (SW4!=1) ) {
            System.out.println("switch on");
            //rb.sendStopCmd(robotIP);
            rb.sendGoNextCmd(robotIP);
        } 

        //if one of Sensors (SS1~SS4) is below 600, then need to call stop
        if( (SS1 < 600) || (SS2 < 600) || (SS3 < 600 ) || (SS4 < 600) ) {
            rb.sendStopCmd(robotIP);
        }
    }
    
	public static void main(String[] argv) throws Exception {

        if (argv.length == 0)
        {
            System.out.println ( "\nPlease specify an IP address on the command line.\n" );
            System.exit(1);
        } else {
            System.out.println ( "\n\nUsing server " + argv[0] + ".\n" );
        }

        StationManager staMan = new StationManager(argv[0]); 

		SerialInput serialInput = new SerialInput();
        serialInput.setStationManager(staMan);

		serialInput.initialize();

		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		System.out.println("Started");
	}
}
