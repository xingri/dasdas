import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

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

    public void handleRequest() {
        int idx = currIndex;
        boolean needProc = false;
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

        RobotStatus robotStatus = dal.GetRobotStatus(orderInfo.getOrderNo());
        switch(idx) {
            case 1:
                if(robotStatus.getStn1Need() == 1) {
                    needProc = true;
                    robotStatus.setStn1Visited(1);
                }
                break;

            case 2:
                if(robotStatus.getStn2Need() == 1) {
                    needProc = true;
                    robotStatus.setStn2Visited(1);
                }
                break;

            case 3:
                if(robotStatus.getStn3Need() == 1) {
                    needProc = true;
                    robotStatus.setStn3Visited(1);
                }
                break;
        }

        if(!needProc) {
            System.out.println("Nothing to Process .. Skipping...");
            return;
        }

        if(robotStatus.getStn1Need() == 1 && robotStatus.getStn1Visited() != 1) {
            robotStatus.setNextStn(1);
        }

        if(robotStatus.getStn2Need() == 1 && robotStatus.getStn2Visited() != 1) {
            robotStatus.setNextStn(2);
        }

        if(robotStatus.getStn3Need() == 1 && robotStatus.getStn3Visited() != 1) {
            robotStatus.setNextStn(3);
        }

        if( (robotStatus.getStn1Need() == robotStatus.getStn1Visited())
            && (robotStatus.getStn2Need() == robotStatus.getStn2Visited())
            && (robotStatus.getStn3Need() == robotStatus.getStn3Visited())
            ) {
            robotStatus.setNextStn(4);
        }

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
        rb.goNextStation();
        System.out.println("proc Request@ISSwitchWorker[" + idx + "] Done");
    }

}
