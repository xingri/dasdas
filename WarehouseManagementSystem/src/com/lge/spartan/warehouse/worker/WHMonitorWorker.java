package com.lge.spartan.warehouse.worker;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.main.*;

public class WHMonitorWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    int currIndex = -1;
    int robotErrCnt = 0;
    int MAX_ROBOT_ERR_CNT = 1;
    boolean isWarehouseErr = false;

    SerialInf serialInf;
   
    public void setSerialInput(SerialInf serialInput) {
        this.serialInf = serialInput;
    }

    public void procRequest(int idx) {
        boolean ret = false;
        currIndex = idx;
        rb = new WHRobotInf(WHConfig.GetRobotIP());

        DAL dal = new MySQLDALImpl();
        ret = dal.Initialize(dbURL, "spartan", "spartan");
        if(ret) System.out.println("dal Initialization success");

        if( WHConfig.IsEmulator() ) {
            rb.setEmulationMode();
        }

        Thread whMonThread=new Thread() {
            public void run() {
                while(true) {
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    timeStamping(dal);
                }
            }
        };

        whMonThread.start();

        Thread rbMonThread = new Thread() {
            public void run() {
                while(true) {
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    try {
                        monitorRobot(rb, dal);
                    } catch ( Exception re) {
                        re.printStackTrace();
                    }
                }
            }
        };

        rbMonThread.start();

        Thread rbEchoThread = new Thread() {
            public void run() {
                while(true) {
                    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
                    try {
                        echoRobot(rb, dal);
                    } catch ( Exception re) {
                        re.printStackTrace();
                    }
                }
            }
        };

        rbEchoThread.start();

        Thread whHwMonThread = new Thread() {
            public void run() {
                while(true) {
                    try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                    try {
                        monWarehouse(rb, dal, serialInf);
                    } catch ( Exception re) {
                        re.printStackTrace();
                    }
                }
            }
        };

        whHwMonThread.start();

    }

    public void monWarehouse(WHRobotInf rbInf, DAL dal, SerialInf serialInf) {
        
        boolean ret;
        File usbFile = new File("/dev/tty.usbmodem641");

        try {Thread.sleep(1000);} catch (InterruptedException ie) {}
        if(!usbFile.exists()) {
            System.out.println("\n\n\n*********\n*********\n*********\n Warehouse HW Not Working, send Stop to Robot \n\n\n\n\n\n");
            isWarehouseErr = true;
            try {
                ret = rbInf.stop();
                System.out.println("Robot Stop requested ret [" + ret + "] ....\n\n\n\n");
                dal.SetRobotErr(1, 3000);
                dal.SetRobotTS(1);
                return;

            } catch (Exception e) {
                System.out.println("Robot stop request exception ....\n\n\n\n");
                dal.SetRobotTS(1);
                dal.SetRobotErr(1, 1000);
                return;
            }
        } else {
            if(isWarehouseErr) {
                System.out.println("\n\n\n*********\n*********\n*********\n Warehouse HW Recovered, ReInitializing Server..\n\n\n\n\n\n");
                try {Thread.sleep(7000);} catch (InterruptedException ie) {}
                serialInf.close();
                serialInf.initialize();
                try {Thread.sleep(1000);} catch (InterruptedException ie) {}
                System.out.println("\n\n\n*********\n*********\n*********\n Warehouse HW Recovered, ReInitializing Server Done....\n\n\n\n\n\n");
                isWarehouseErr = false;
            }
        }
    }

    public void timeStamping(DAL dal) {
        System.out.println("proc Request@WHMonitorWorker");
        System.out.println("proc Request@WHMonitorWorker Robot Err: " + dal.GetRobotErr(1) );
        
        dal.SetWarehouseServerTS(1);
    }

    public void echoRobot(WHRobotInf rbInf, DAL dal) {
        // check timestamping of Robot
        // if robot is healthy status < 3sec 
        // return; 
        boolean ret;
        
        System.out.println("Robot ping-echo called ....\n\n\n\n");
        if(dal.GetRobotErr(1) != null) {
            System.out.println("Robot is currently error status(" + dal.GetRobotErr(1) + ") ....\n\n\n\n");
            return;
        }

        /*
        if over healthy period, ping Robot and update time Stamp...
        if robot not respondig, set robot Error....
        if robot recovered, healthy checking again.....
        */
        if(!dal.IsRobotAvailable(1)) {
            System.out.println("Robot status is over healthy interval....");
            try {
                ret = rbInf.getHealth();
                System.out.println("Robot ping-echo ret [" + ret + "] ....\n\n\n\n");
                if(ret) dal.SetRobotTS(1);
                else {
                    robotErrCnt++;
                    if(robotErrCnt > MAX_ROBOT_ERR_CNT) {
                        System.out.println("\n\n\n*********\n*********\n*********\n Robot connection lost, let's Stop the Robot... \n\n\n\n\n\n");
                        robotErrCnt = 0;

                        rbInf.stop();
                        dal.SetRobotErr(1, 1000);
                    }
                }
            } catch (Exception e) {
                System.out.println("Robot ping-echo exception ....\n\n\n\n");

                robotErrCnt++;
                if(robotErrCnt > MAX_ROBOT_ERR_CNT) {
                    System.out.println("\n\n\n*********\n*********\n*********\n Robot connection lost, let's Stop the Robot... \n\n\n\n\n\n");
                    robotErrCnt = 0;

                    rbInf.stop();
                    dal.SetRobotErr(1, 1000);
                }
            }
        } else {
            System.out.println("Robot status is ok....");
        }
    }

    public void monitorRobot(WHRobotInf rbInf, DAL dal) {
        // check timestamping of Robot
        // if robot is healthy status < 3sec 
        // return; 
        boolean ret;
        
        System.out.println("Robot monitoring called ....\n\n\n\n");

        if(dal.GetRobotErr(1) != null) {
            System.out.println("Robot is currently error status(" + dal.GetRobotErr(1) + ") ....\n\n\n\n");
            return;
        }

        if(dal.GetRobotState(1) == RobotState.Idle) {

            ArrayList<OrderInfo> progressList = new ArrayList<OrderInfo>();
            progressList = dal.GetOrders(OrderStatus.Inprogress);
            if(progressList.size() > 0) {
                System.out.println(
                "\n\n\n\n *****\n\n There is in-progressing Order and RobotStatus is Idle, so we resume Robot");

                OrderInfo currOrder = progressList.get(0);

                ret = rb.goNextStation();
                if(!ret) {
                    System.out.println(
                    "\n\n\n\n *****\n\n Something wrong with goNext.. but keep going.....");
                }
                dal.SetRobotMoveTS(1, currOrder.getOrderNo());
                dal.SetRobotTS(1);
                dal.SetRobotState(1, RobotState.Busy);
            }
        }

        ArrayList<OrderInfo> currList = new ArrayList<OrderInfo>(); 
        currList = dal.GetOrders(OrderStatus.Inprogress);
        if(currList.size() > 0) {
            boolean res1; 
            boolean res2; 

            OrderInfo currOrder = currList.get(0);
            //res1 = dal.IsRobotMovesMinCond(1, currOrder.getOrderNo());
            //if(res1) System.out.println("\n\n\n Check min Condition...\n\n\n");

            //res2 = dal.IsRobotMovesMaxCond(1, currOrder.getOrderNo());
            res2 = dal.IsRobotMovesMaxCond(1, currOrder.getOrderNo());
            if(res2) {
                System.out.println("\n\n\n*********\n*********\n*********\n Check max Condition, send Stop to Robot \n\n\n\n\n\n");
                try {
                    ret = rbInf.stop();
                    System.out.println("Robot Stop requested ret [" + ret + "] ....\n\n\n\n");
                    dal.SetRobotErr(1, 2000);
                    dal.SetRobotTS(1);
                    dal.SetRobotMoveTS(1, currOrder.getOrderNo());
                    return;

                } catch (Exception e) {
                    System.out.println("Robot stop request exception ....\n\n\n\n");
                    dal.SetRobotTS(1);
                    dal.SetRobotErr(1, 1000);
                    dal.SetRobotMoveTS(1, currOrder.getOrderNo());
                    return;
                }
            }
        }
    }
}
