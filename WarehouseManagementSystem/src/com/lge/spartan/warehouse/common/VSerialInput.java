package com.lge.spartan.warehouse.common;

import java.io.File;

public class VSerialInput implements SerialInf {

    StationManager myMan;
    int currIndex = 0;

    public void initIndex() {
        currIndex = 1;
    }

    public int getNextIndex() {
        if(currIndex != 4) { 
            return ++currIndex;
        } else {
            currIndex = 1;
            return currIndex;
        }
    }

    public boolean invokeSensor() {
        File f = new File("/tmp/snsd");
        if(f.exists() && !f.isDirectory()) {
            System.out.println("\n\n\n Sensor Event Emulated....\n\n\n");
            f.delete();
            return true;
        }
        return false;
    }
   
    public void setStationManager(StationManager staMan) {
        myMan = staMan;
    }

    public void initialize() {
        Thread t = new Thread() {
            public void run() {
                initIndex();
                while(true) {

                    try {Thread.sleep(100);} catch (InterruptedException ie) {}
                    if(invokeSensor()) {
                        switch(getNextIndex()) {
                            case 2:
                                myMan.eventHandler("1,1,0,0,0,0,0,0,0");
                                try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                                myMan.eventHandler("1,0,0,0,0,1,0,0,0");
                                break;
                            case 3:
                                myMan.eventHandler("1,0,1,0,0,0,0,0,0");
                                try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                                myMan.eventHandler("1,0,0,0,0,0,1,0,0");
                                break;
                            case 4:
                                myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                                try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                                myMan.eventHandler("1,0,0,0,0,0,0,1,0");
                                break;
                            case 1:
                                myMan.eventHandler("1,0,0,0,1,0,0,0,0");
                                try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                                myMan.eventHandler("1,0,0,0,0,0,0,0,1");
                                break;
                        }
                    }

                    /*
                    try {Thread.sleep(5000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,1,0,0,0,0,0,0,0");
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,1,0,0,0");

                    try {Thread.sleep(5000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,1,0,0,0,0,0,0");
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,1,0,0");

                    try {Thread.sleep(5000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,1,0");

                    try {Thread.sleep(5000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,1,0,0,0,0");
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,0,1");

                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,1,0,0,0,0,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,1,0,0,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,0,0,0,0,0,0,1,0");
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    myMan.eventHandler("1,1,1,1,1,0,0,0,1");
                    */

                    /*
                    try {Thread.sleep(10000);} catch (InterruptedException ie) {}
                    System.out.println("Finish One Cycle!!");
                    */
                }
            }
        };

        t.start();
        System.out.println("VSerial Input Initialized");
    }
}
