public class StationManager {

    String robotIP;

    String SW1, SW2, SW3, SW4, SS1, SS2, SS3, SS4;
    // SW goes to 0 when it pushed
    // SS goes to < 600 when robot arrived

    public StationManager(String robotIP) {
        this.robotIP = robotIP;
    }

    public void eventHandler(String inputLine) {

        System.out.println("check msg: " + inputLine);
        String[] sensList = inputLine.split(",");

        SS1 = sensList[0];
        SS2 = sensList[1];
        SS3 = sensList[2];
        SS4 = sensList[3];

        SW1 = sensList[4];
        SW2 = sensList[5];
        SW3 = sensList[6];
        SW4 = sensList[7];

        RobotControl rb = new RobotControl();

        if(sensList[4].equals("0")) {
            System.out.println("switch on");
            //rb.sendStopCmd(robotIP);
            rb.sendGoNext(robotIP);
        } 

        //if one of Sensors (SS1~SS4) is below 600, then need to call stop
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
