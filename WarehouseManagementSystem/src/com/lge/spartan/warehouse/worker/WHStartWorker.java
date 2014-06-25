package com.lge.spartan.warehouse.worker;

import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;
import com.lge.spartan.warehouse.common.*;
import com.lge.spartan.warehouse.main.*;

public class WHStartWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    int currIndex = -1;

    public void procRequest(int idx) {
        currIndex = idx;
        rb = new WHRobotInf(WHConfig.GetRobotIP());
        if( WHConfig.IsEmulator() ) {
            rb.setEmulationMode();
        }

        Thread t=new Thread() {
            public void run() {
                while(true) {
                    handleRequest();
                    try {Thread.sleep(3000);} catch (InterruptedException ie) {}
                }
            }
        };

        t.start();
    }

    public boolean checkAvailability(OrderInfo myOrder, DAL myDal) {

        ArrayList<OrderDetails> widgetList = new ArrayList<OrderDetails>();
        widgetList = myOrder.getListOrderDetails();
        if(widgetList == null) {
            return false;
        }

        if(widgetList.size() > 0) { 
            // Check Available Widget on Warehouse System

            int widgetCnt = 0;
            for(int idx=0; idx < widgetList.size() ; idx++) {

                // Check availability of widget....
                widgetCnt = myDal.GetWidgetQuantity(widgetList.get(idx).getWidgetId());

                if(widgetCnt < widgetList.get(idx).getQuantity()) {
                    System.out.println("Widget of Id[" + widgetList.get(idx).getWidgetId() 
                        + "] is not enough Req[" + widgetList.get(idx).getQuantity() 
                        + "] : In-Stock[" + widgetCnt + "]");
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }

        return false;
    }

    public void handleRequest() {
        System.out.println("proc Request@WHStartWorker");

        boolean ret;
        DAL dal = new MySQLDALImpl();
        OrderInfo orderInfo; 
        ArrayList<OrderInfo> progressList = new ArrayList<OrderInfo>();
        ArrayList<OrderInfo> backOrderList = new ArrayList<OrderInfo>();
        ArrayList<OrderDetails> widgetList = new ArrayList<OrderDetails>();

        ret = dal.Initialize(dbURL, "spartan", "spartan");
        if(ret) System.out.println("dal Initialization success");

        System.out.println("proc Request@WHStartWorker Robot Err: " + dal.GetRobotErr(1) );

        progressList = dal.GetOrders(OrderStatus.Inprogress);
        if(progressList.size() > 0 ) {
            System.out.println("There are already in-progressing Orders.... " + progressList.size());
            return;
        }

        backOrderList = dal.GetOrders(OrderStatus.Backordered);
        if(backOrderList.size() > 0 ) {
            OrderInfo currOrderInfo;
            // Update backorder to pending order when it is available....
            System.out.println("There are back Orders.... " + backOrderList.size());
            for(int idx=0; idx<backOrderList.size(); idx++) {
                if(checkAvailability(backOrderList.get(idx), dal)) {
                    currOrderInfo = backOrderList.get(idx);
                    dal.UpdateOrderStatus(currOrderInfo.getOrderNo(), OrderStatus.Pending);
                } else {
                    System.out.println("widget items are still insufficient");
                }
            }
        }

        /*
         * 1. Pick Order ( From backorder to pending order)
         * 2. Check Order condition
         * 3. Update Order to processing Status
         * 4. Order Robot to Move
         */
        orderInfo = dal.PickFirstOrder();
        if(orderInfo == null) {
            System.out.println("There are nothing to progressing Orders....");
            return;
        }

        System.out.println("First Order: " + orderInfo);

        widgetList = orderInfo.getListOrderDetails();
        if(widgetList.size() > 0) { 
            // Check Available Widget on Warehouse System
            int widgetCnt = 0;
            int nextStn = 9999;

            int numStaToVisit = 0;

            boolean needVisit1 = false;
            boolean needVisit2 = false;
            boolean needVisit3 = false;

            RobotStatus robotStatus = new RobotStatus();

            robotStatus.setRobotId(1);
            robotStatus.setOrderNo(orderInfo.getOrderNo());

            for(int idx=0; idx < widgetList.size() ; idx++) {
                // Check availability of widget....
                widgetCnt = dal.GetWidgetQuantity(widgetList.get(idx).getWidgetId());
                if(widgetCnt < widgetList.get(idx).getQuantity()) {
                    System.out.println("Widget of Id[" + widgetList.get(idx).getWidgetId() 
                        + "] is not enough Req[" + widgetList.get(idx).getQuantity() 
                        + "] : In-Stock[" + widgetCnt + "]");
                    dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Backordered);
                    return;
                } else {
                    int staId = dal.GetWidgetStation(widgetList.get(idx).getWidgetId());
                    if(staId < 1) {
                        System.out.println("Invalid Station Number: " + staId 
                            + "of Widget: " + widgetList.get(idx).getWidgetId());
                        return;
                    } else {
                        if(staId == 1) {
                            nextStn = 1;
                            if(!needVisit1) {
                                numStaToVisit++;
                                needVisit1 = true;
                            }
                        } else if(staId == 2) {
                            if(nextStn > staId) nextStn = staId;
                            if(!needVisit2) {
                                numStaToVisit++;
                                needVisit2 = true;
                            }
                        } else if(staId == 3) {
                            if(nextStn > staId) nextStn = staId;
                            if(!needVisit3) {
                                numStaToVisit++;
                                needVisit3 = true;
                            }
                        } else {
                            System.out.println("Invalid Station Number: " + staId 
                             + "of Widget: " + widgetList.get(idx).getWidgetId());
                            return;
                        }
                    }
                }
            }

            ArrayList<Integer> stationsToVisit = new ArrayList<Integer> ();

            if(needVisit1) {
                stationsToVisit.add(new Integer(1));
            }

            if(needVisit2) {
                stationsToVisit.add(new Integer(2));
            }

            if(needVisit3) {
                stationsToVisit.add(new Integer(3));
            }

            robotStatus.setStationsToVisit(stationsToVisit);

            robotStatus.setNextStation(nextStn);
            robotStatus.setCurrentStation(currIndex);

            dal.SetRobotState(1, RobotState.Busy);

            // Update Order into Robot Status
            dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Inprogress);

            //RobotStatus chkRobotStatus = dal.GetRobotMoves(1, 1);

            //System.out.println("\n\n\n\n check robotStatus \n\n\n\n");
            //System.out.println("ToVisit: " + chkRobotStatus.getStationsToVisit());
            //System.out.println("Visited: " + chkRobotStatus.getStationsVisited());

            dal.AddRobotStatus(robotStatus);

            //RobotStatus resRobotStatus = dal.GetRobotStatus(orderInfo.getOrderNo());
            //System.out.println("RobotStatus: " + resRobotStatus.getStn1Need());

            //resRobotStatus.setStn1Need(1);

            //dal.UpdateRobotStatus(resRobotStatus);
            //RobotStatus resRobotStatus2 = dal.GetRobotStatus(orderInfo.getOrderNo());

            //System.out.println("RobotStatus: " + resRobotStatus2.getStn1Need());
            //System.out.println("RobotStatus: " + resRobotStatus2.getNextStn());

            // Order Robot to Move Next Station..
            System.out.println("Check send Go NextStation to Robot");
            rb.goNextStation();
            WarehouseMain.setIndex(4);

        } else {
            System.out.println("Order is Deficient");
            //dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Deficient);
        }
    }
}
