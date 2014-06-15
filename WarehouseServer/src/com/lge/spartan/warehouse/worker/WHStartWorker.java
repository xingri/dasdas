import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class WHStartWorker implements WHWorker {

    String dbURL = "127.0.0.1";

    public void procRequest() {

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
         * 2. Update Order to processing Status
         * 3. Store Order ID into File System
         * 4. Order Robot to Move
         */
        orderInfo = dal.PickFirstOrder();
        System.out.println("First Order: " + orderInfo);

        widgetList = orderInfo.getListOrderDetails();
        if(widgetList.size() > 0) { 
            // Check Available Widget on Warehouse System

            System.out.println("WidgetList in Order: " + widgetList.get(0).getWidgetId());
            System.out.println("WidgetList in Order: " + widgetList.get(0).getQuantity());

            // check Widget Item availability

            // Update Order into Robot Status

            dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Inprogress);

            RobotStatus robotStatus = new RobotStatus();
            robotStatus.setOrderNo(orderInfo.getOrderNo());

            dal.AddRobotStatus(robotStatus);

            RobotStatus resRobotStatus = dal.GetRobotStatus(orderInfo.getOrderNo());
            System.out.println("RobotStatus: " + resRobotStatus.getStn1Need());

            resRobotStatus.setStn1Need(1);

            dal.UpdateRobotStatus(resRobotStatus);
            RobotStatus resRobotStatus2 = dal.GetRobotStatus(orderInfo.getOrderNo());

            System.out.println("RobotStatus: " + resRobotStatus2.getStn1Need());

            // Order Robot to Move Next Station..

        } else {
            System.out.println("Order is Deficient");
            //dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Deficient);
        }
        
    }
}
