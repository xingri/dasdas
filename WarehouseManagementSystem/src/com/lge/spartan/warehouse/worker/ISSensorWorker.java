import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class ISSensorWorker implements WHWorker {

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

    public boolean isNumExistsInArrayList(int num, ArrayList<Integer> list) {
        for(int idx=0; idx< list.size(); idx++) {
            if(list.get(idx).intValue() == num) {
                return true;
            }
        }
        return false;
    }

    public void handleRequest() {
        int idx = currIndex;

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

        robotStatus.setCurrentStation(idx);
        dal.UpdateRobotStatus(robotStatus);

        switch(idx) {
            case 1:
                if(isNumExistsInArrayList(1, robotStatus.getStationsToVisit())) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 1");
                }
                break;

            case 2:
                if(isNumExistsInArrayList(2, robotStatus.getStationsToVisit())) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 2");
                }
                break;

            case 3:
                if(isNumExistsInArrayList(3, robotStatus.getStationsToVisit())) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 3");
                }
                break;
        }

        if(needStop) {
            boolean res = false;
            System.out.println("[send NearStation to Robot]\n\n\n\n");
            res = rb.nearStation();
            if(!res) {
                System.out.println("[send NearStation to Robot failed!!!]\n\n\n\n");
            }

/*
            try {Thread.sleep(1000);} catch (InterruptedException ie) {}
            res = rb.getArrival();
            if(!res) {
                System.out.println("[getArrival from Robot failed!!!]\n\n\n\n");
            }

*/
            needStop = false;
        } else {
            rb.nearStation();
            rb.goNextStation();
        }

    }
}
