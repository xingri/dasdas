package com.lge.spartan.warehouse.worker;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.main.*;

public class WHMonitorWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    int currIndex = -1;

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
                    monitorRobot(rb, dal);
                }
            }
        };

        rbMonThread.start();
    }

    public void timeStamping(DAL dal) {
        System.out.println("proc Request@WHMonitorWorker");
        System.out.println("proc Request@WHMonitorWorker Robot Err: " + dal.GetRobotErr(1) );
        
        dal.SetWarehouseServerTS(1);
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

                ret = rb.goNextStation();
                if(ret) { 
                    dal.SetRobotTS(1);
                    dal.SetRobotState(1, RobotState.Busy);
                }
            }
        }

        if(!dal.IsRobotAvailable(1)) {
            System.out.println("Robot status is over healthy interval....");
            try {
                ret = rbInf.getHealth();
                System.out.println("Robot monitoring ret Health [" + ret + "] ....\n\n\n\n");
                //ret = true;
                if(ret) dal.SetRobotTS(1);
                else dal.SetRobotErr(1, 1000);
            } catch (Exception e) {
                System.out.println("Robot monitoring exception Health ....\n\n\n\n");
                dal.SetRobotErr(1, 1000);
            }
        } else {
            System.out.println("Robot status is ok....");
        }

        /*
        if over healthy period, ping Robot and update time Stamp...
        if robot not respondig, set robot Error....
        if robot recovered, healthy checking again.....
        */
    }
}
