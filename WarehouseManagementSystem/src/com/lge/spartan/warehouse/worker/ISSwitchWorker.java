package com.lge.spartan.warehouse.worker;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.main.*;

public class ISSwitchWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    int currIndex = -1;
    DAL dal;

    public void procRequest(int idx) {
        currIndex = idx;

        Thread t=new Thread() {
            public void run() {
                handleRequest();
            }
        };

        t.start();
    }

    public boolean isNumExistsInArrayList(int num, ArrayList<Integer> list) {
        for(int idx=0; idx< list.size(); idx++) {
            if(list.get(idx).intValue() == num) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameList(ArrayList<Integer> toVisit, ArrayList<Integer> visited) {

        if(visited == null || toVisit == null) return false;
        if(visited.size() == 0 || toVisit.size() == 0) return false;

        if(toVisit.size() != visited.size()) {
            return false;
        }

        for(int idx=0; idx<toVisit.size(); idx++) {
            boolean found = false;

            for(int idy=0; idy<visited.size(); idy++) {
                if(visited.get(idy).intValue() == toVisit.get(idx).intValue() ) {
                    found = true;
                }
            }
            if(!found) return false;
        }

        return true;
    }

    public int getNextVisitStn(ArrayList<Integer> toVisit, ArrayList<Integer> visited) {

        int tmpNext = 9999;

        if(visited == null) return toVisit.get(0);

        ArrayList<Integer> tmpList = new ArrayList<Integer>();

        for(int idx=0; idx<toVisit.size(); idx++) {
            tmpList.add(toVisit.get(idx).intValue());
        }

        for(int idx=0; idx<toVisit.size(); idx++) {
            for(int idy=0; idy<visited.size(); idy++) {
                if(visited.get(idy).intValue() == toVisit.get(idx).intValue() ) {
                    tmpList.remove(toVisit.get(idx));
                }
            }
        }

        if(tmpList.size() == 0) return 4;

        for(int idx=0; idx<tmpList.size(); idx++) {
            if(tmpList.get(idx).intValue() < tmpNext) tmpNext = tmpList.get(idx).intValue();
        }

        return tmpNext;
    }

    public void handleRequest() {
        int idx = currIndex;
        boolean needProc = false;

        boolean stn1Visited = false;
        boolean stn2Visited = false;
        boolean stn3Visited = false;

        System.out.println("proc Request@ISSwitchWorker[" + idx + "]");

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

        ArrayList<Integer> visitedList = robotStatus.getStationsVisited();
        if (visitedList == null) visitedList = new ArrayList<Integer>();

        switch(idx) {
            case 1:
                if(isNumExistsInArrayList(1, robotStatus.getStationsToVisit())) {
                    needProc = true;
                    stn1Visited = true;
                    if( robotStatus.getStationsVisited() == null 
                        || !isNumExistsInArrayList(1, robotStatus.getStationsVisited())) {
                        visitedList.add(new Integer(1));
                    }
                }
                break;

            case 2:
                if(isNumExistsInArrayList(2, robotStatus.getStationsToVisit())) {
                    needProc = true;
                    stn2Visited = true;
                    if( robotStatus.getStationsVisited() == null 
                        || !isNumExistsInArrayList(2, robotStatus.getStationsVisited())) {
                        visitedList.add(new Integer(2));
                    }
                }
                break;

            case 3:
                if(isNumExistsInArrayList(3, robotStatus.getStationsToVisit())) {
                    needProc = true;
                    stn3Visited = true;
                    if( robotStatus.getStationsVisited() == null 
                        || !isNumExistsInArrayList(3, robotStatus.getStationsVisited())) {
                        visitedList.add(new Integer(3));
                    }
                }
                break;
        }

        if(!needProc) {
            System.out.println("Nothing to Process .. Skipping...");
            return;
        }

        int tmpNextVisitStn = 
            getNextVisitStn(robotStatus.getStationsToVisit(), visitedList);

        robotStatus.setNextStation(tmpNextVisitStn);

        robotStatus.setStationsVisited(visitedList);
        robotStatus.setCurrentStation(idx);

        // Update Next Visit Stn
        dal.UpdateRobotStatus(robotStatus);

        // Decrement Widget Number
        ArrayList<OrderDetails> widgetList = new ArrayList<OrderDetails>();
        widgetList = orderInfo.getListOrderDetails();

        System.out.println("Check widgetSize: " + widgetList.size());

        for(int j=0; j < widgetList.size() ; j++) {
            int staId = dal.GetWidgetStation(widgetList.get(j).getWidgetId());
            if(staId == idx) {
                 dal.DecWidgets(widgetList.get(j).getWidgetId(), widgetList.get(j).getQuantity());
            }
        }

        // Let robot to go next station
        System.out.println("Check send Go Next Station to Robot");
        ret = rb.goNextStation();
        if(ret) dal.SetRobotTS(1);

        System.out.println("proc Request@ISSwitchWorker[" + idx + "] Done");
    }

}
