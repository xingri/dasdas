package com.lge.spartan.warehouse.main;

import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.worker.*;

import com.lge.spartan.warehouse.order.WarehouseManagementSystem;

public class WarehouseMain implements StationManager {

    static int currIndex = -1;
    WHEvent cachedSwEvent = null;

    public void eventHandler(String inputLine) {

        if(inputLine != null) {

            WHWorker whWorker = WHWorkerFactory.create(inputLine);
            if( whWorker != null ) {
                WHEvent currEvent = new WHEvent(inputLine);
                if (currEvent.isSwitch()) {
                    System.out.println("Switch IDX: " + currEvent.getSwitchIdx());

                    if(currIndex != currEvent.getSwitchIdx()) {
                        System.out.println("Invalid Event Sequence CurrIdex [" + currEvent.getSwitchIdx() 
                            + "] expected Index [!" + currIndex + "]!!!");
                        return;
                    }

                    whWorker.procRequest(currEvent.getSwitchIdx());
                    //currIndex = currEvent.getSwitchIdx();
                } else {
                    if (currEvent.isValid() && ( cachedSwEvent == null 
                            || !currEvent.isSameSensor(cachedSwEvent.getEventList())) ) {

                        System.out.println("Sensor IDX: " + currEvent.getSensorIdx());

                        cachedSwEvent = new WHEvent(inputLine);

                        if(currIndex == currEvent.getSensorIdx() || getNextIndex() != currEvent.getSensorIdx()) {
                            System.out.println("Invalid Event Sequence CurrIdex [" + currEvent.getSensorIdx() 
                                + "] expected Index [!" + currIndex + " || " + getNextIndex() + "]!!!");
                            return;
                        }

                        currIndex = currEvent.getSensorIdx();

                        //try {Thread.sleep(300);} catch (InterruptedException ie) {}
                        try {Thread.sleep(200);} catch (InterruptedException ie) {}

                        whWorker.procRequest(currEvent.getSensorIdx());
                    }
                    else {
                        System.out.println("Sensor IDX (outCond): " + currEvent.getSensorIdx());
                        System.out.println("Sensor IDX (outCond): " + currEvent.isValid());
                        System.out.println("Sensor IDX (outCond): " + cachedSwEvent);
                        System.out.println("Sensor IDX (outCond): " + currEvent.isSameSensor(cachedSwEvent.getEventList()));
                    }
                }
            } else {
                System.out.println("Cannot create Warehouse Worker, check input [" + inputLine + "]!!!");
            }

        } else {
            System.out.println("invalid data comming from warehouse hardware [" + inputLine + "]!!!");
        }
    }

    public static void setIndex(int index) {
        currIndex = index;
    }

    public static int getNextIndex() {
        if(currIndex == 4) {
            return 1;
        }

        return (currIndex+1);
    }
 

    public static void main(String[] argv) {

        System.out.println("Warehouse System Start.... ");

        Thread orderThread = new Thread() {
            public void run() {
                new WarehouseManagementSystem().init();
            }
        };

        orderThread.start();

        System.out.println("Warehouse Order Server Started.... ");

        WarehouseMain whMain = new WarehouseMain();

        SerialInf serialInput;

        if(WHConfig.IsInputEmulator()) {
            serialInput = new VSerialInput();
        } else { 
            serialInput = new SerialInput();
        }

        serialInput.setStationManager(whMain);
        serialInput.initialize();

        whMain.setIndex(4);
        WHStartWorker startWorker = new WHStartWorker();
        startWorker.procRequest(0); // arg 0 has no meaning in case of startWorker

        Thread t=new Thread() {
            public void run() {

                boolean res = false;
                WHRobotInf rb = new WHRobotInf(WHConfig.GetRobotIP());
                if( WHConfig.IsEmulator() ) {
                    rb.setEmulationMode();
                }

                while(true) {
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                    res = rb.getHealth();
                    if(!res) System.out.println("\n\n\n\n\n Robot not responding!!!!!");
                }

                //the following line will keep this app alive for 10000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                //try {Thread.sleep(10000000);} catch (InterruptedException ie) {}
            }
        };

        t.start();
        System.out.println("Started");
    }
}
