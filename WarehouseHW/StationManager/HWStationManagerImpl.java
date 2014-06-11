public class HWStationManagerImpl implements StationManager {

    String robotIP;
    boolean inStation = false;
    boolean isSwitchOn = false;

    //RobotControl rb;
    WHRobotInf rb;

    // SS goes to < 600 when robot arrived
    int SS1, SS2, SS3, SS4;

    // SW goes to 0 when it pushed
    int SW1, SW2, SW3, SW4;
    int prevSta = 0;

    public HWStationManagerImpl(String robotIP) {
        this.robotIP = robotIP;
        //this.rb = new RobotControl(robotIP);
        this.rb = new WHRobotInf(robotIP);
    }

    public void eventHandler(String inputLine) {

        int currSta = 0;

        System.out.println("check msg: " + inputLine);
        String[] sensList = inputLine.split(",");

        if(sensList.length != 9) {
            System.out.println("garbage!!!!");
            return;
        }

        SS1 = Integer.parseInt(sensList[1]);
        SS2 = Integer.parseInt(sensList[2]);
        SS3 = Integer.parseInt(sensList[3]);
        SS4 = Integer.parseInt(sensList[4]);

        SW1 = Integer.parseInt(sensList[5]);
        SW2 = Integer.parseInt(sensList[6]);
        SW3 = Integer.parseInt(sensList[7]);
        SW4 = Integer.parseInt(sensList[8]);


        //if one of Switch (SW1~SW4) is pressed(!=1) then need to call Go Next Station
        if( (SW1!=0) || (SW2!=0) || (SW3!=0) || (SW4!=0) ) {
        //if( (SW1!=0) || (SW2!=0) || (SW3!=0) ) {
            if(!isSwitchOn)  {
                isSwitchOn = true;
                System.out.println("switch on - go Next Station: " + robotIP);
                //rb.sendGoNextCmd();
                rb.goNextStation();
            }
        } else {
            isSwitchOn = false;
        }

        //if one of Sensors (SS1~SS4) is below 600, then need to call stop
        if( (SS1!=0) || (SS2!=0) || (SS3!=0) || (SS4!=0) ) {
            if(SS1 == 1) 
                currSta  = 1;
            if(SS2 == 1) 
                currSta  = 2;
            if(SS3 == 1) 
                currSta  = 3;
            if(SS4 == 1) 
                currSta  = 4;

            if(!inStation && prevSta != currSta) {
                inStation = true;
				try {Thread.sleep(300);} catch (InterruptedException ie) {}
                System.out.println("In station - Stop(1): " + robotIP);
                //rb.sendStopCmd();
                rb.nearStation();
                if(SS1 == 1) 
                    prevSta  = 1;
                if(SS2 == 1) 
                    prevSta  = 2;
                if(SS3 == 1) 
                    prevSta  = 3;
                if(SS4 == 1) 
                    prevSta  = 4;
            }
        } 
       else {
          inStation = false;
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

        StationManager staMan = new HWStationManagerImpl(argv[0]); 

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
