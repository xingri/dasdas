public class VSerialInput implements SerialInf {

    StationManager myMan;
   
    public void setStationManager(StationManager staMan) {
        myMan = staMan;
    }

    public void initialize() {
        Thread t = new Thread() {
            public void run() {
                while(true) {
//                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
//                    myMan.eventHandler("1,0,0,0,1,0,0,0,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,1,0,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,1,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,1,0,0,0,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,1,0,0,0");
                    try {Thread.sleep(4000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,1,0,0,0,0");
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,0,1");
                    /*
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,1,0,0,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,1,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,1,1,1,1,0,0,0,1");
                    */

                    try {Thread.sleep(10000);} catch (InterruptedException ie) {}
                    System.out.println("Finish One Cycle!!");
                }
            }
        };

        t.start();
        System.out.println("VSerial Input Initialized");
    }
}
