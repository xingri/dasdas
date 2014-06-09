//package com.lge.spartan.warehouse;

public class WarehouseMain implements StationManager {

    public void eventHandler(String inputLine) {
        if(inputLine != null) {
            WHWorker whWorker = WHWorkerFactory.create(inputLine);
            if(whWorker != null) {
                whWorker.procRequest();
            } else {
                System.out.println("Cannot create Warehouse Worker, check input [" + inputLine + "]!!!");
            }
        } else {
            System.out.println("invalid data comming from warehouse hardware [" + inputLine + "]!!!");
        }
    }

    public static void main(String[] argv) {

        System.out.println("Warehouse System Start.... ");

        if (argv.length == 0) {
            System.out.println ( "\nPlease specify an IP address on the command line.\n" );
            System.exit(1);
        } else {
            System.out.println ( "\n\nUsing server " + argv[0] + ".\n" );
        }

        WarehouseMain whMain = new WarehouseMain();

        SerialInput serialInput = new SerialInput();
        serialInput.setStationManager(whMain);
        serialInput.initialize();

        WHStartWorker startWorker = new WHStartWorker();
        startWorker.procRequest();

        Thread t=new Thread() {
            public void run() {
                //the following line will keep this app alive for 10000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {Thread.sleep(10000000);} catch (InterruptedException ie) {}
            }
        };

        t.start();
        System.out.println("Started");
    }
}
