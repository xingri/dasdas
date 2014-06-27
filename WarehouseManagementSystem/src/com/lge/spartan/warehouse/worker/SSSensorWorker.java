package com.lge.spartan.warehouse.worker;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.main.*;

public class SSSensorWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    DAL dal;
    int currIndex = -1;

    public void procRequest(int idx) {
        currIndex = idx;

        Thread t=new Thread() {                                                             
            public void run() {                                                         
                handleRequest();
            }
        };

        t.start();
    }

    public void handleRequest() {

        int idx = currIndex; 

        System.out.println("proc Request@SSSensorWorker[" + idx + "]");

        boolean needStop = false;
        System.out.println("In proc Request@ISSensorWorker["+ idx + "]");

        dal = new MySQLDALImpl();
        rb = new WHRobotInf(WHConfig.GetRobotIP());
        if( WHConfig.IsEmulator() ) {
            rb.setEmulationMode();
        }

        boolean ret = dal.Initialize(dbURL, "spartan", "spartan");
        if(!ret) { 
            System.out.println("dal Initialization failed");
            return;
        }

        ArrayList<OrderInfo> progressList = new ArrayList<OrderInfo>();

        progressList = dal.GetOrders(OrderStatus.Inprogress);
        if(progressList.size() != 1 ) {
            System.out.println("Not in-progressing Orders status Num:" + progressList.size());
            return;
        }

        OrderInfo orderInfo = (OrderInfo) progressList.get(0);
        RobotStatus robotStatus = dal.GetRobotMoves(1, orderInfo.getOrderNo());

        if(robotStatus.getNextStation() == 4) {
            System.out.println("Check send NearStation(SS) to Robot");
            ret = rb.nearStation();
            dal.SetRobotMoveTS(1, orderInfo.getOrderNo());
            if(ret) dal.SetRobotTS(1);
        } else {
            boolean res1 = false;
            boolean res2 = false;
            dal.SetRobotMoveTS(1, orderInfo.getOrderNo());
            res1 = rb.nearStation();
            res2 = rb.goNextStation();
            if(res1 && res2) {
                dal.SetRobotTS(1);
            }
        }
    }
}
