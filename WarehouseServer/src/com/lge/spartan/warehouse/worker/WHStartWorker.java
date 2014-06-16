import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class WHStartWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;

    public void procRequest(int idx) {
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

    public void handleRequest() {
        System.out.println("proc Request@WHStartWorker");

        boolean ret;
        DAL dal = new MySQLDALImpl();
        OrderInfo orderInfo; 
        ArrayList<OrderInfo> progressList = new ArrayList<OrderInfo>();
        ArrayList<OrderDetails> widgetList = new ArrayList<OrderDetails>();

        ret = dal.Initialize(dbURL, "spartan", "spartan");
        if(ret) System.out.println("dal Initialization success");

        progressList = dal.GetOrders(OrderStatus.Inprogress);
        if(progressList.size() > 0 ) {
            System.out.println("There are already in-progressing Orders.... " + progressList.size());
            return;
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

            RobotStatus robotStatus = new RobotStatus();
            robotStatus.setOrderNo(orderInfo.getOrderNo());

            for(int idx=0; idx < widgetList.size() ; idx++) {
                // Check availability of widget....
                widgetCnt = dal.GetWidgetQuantity(widgetList.get(idx).getWidgetId());
                if(widgetCnt < widgetList.get(idx).getWidgetId()) {
                    System.out.println("Widget of Id[" + widgetList.get(0).getWidgetId() 
                        + "] is not enough Req[" + widgetList.get(idx).getQuantity() 
                        + "] : In-Stock[" + widgetCnt + "]");
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
                            robotStatus.setStn1Need(1);
                        } else if(staId == 2) {
                            if(nextStn > staId) nextStn = staId;
                            robotStatus.setStn2Need(1);
                        } else if(staId == 3) {
                            if(nextStn > staId) nextStn = staId;
                            robotStatus.setStn3Need(1);
                        } else {
                            System.out.println("Invalid Station Number: " + staId 
                             + "of Widget: " + widgetList.get(idx).getWidgetId());
                            return;
                        }
                    }
                }
            }

            robotStatus.setNextStn(nextStn);
            robotStatus.setState(RobotState.Busy.ordinal());

            // Update Order into Robot Status
            dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Inprogress);
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

        } else {
            System.out.println("Order is Deficient");
            //dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Deficient);
        }
    }
}
