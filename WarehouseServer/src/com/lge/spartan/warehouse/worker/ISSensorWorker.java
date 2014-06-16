import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class ISSensorWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    DAL dal;

    public void procRequest(int idx) {

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

        RobotStatus robotStatus = dal.GetRobotStatus(orderInfo.getOrderNo());
        switch(idx) {
            case 1:
                if(robotStatus.getStn1Need() == 1) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 1");
                }
                break;

            case 2:
                if(robotStatus.getStn2Need() == 1) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 2");
                }
                break;

            case 3:
                if(robotStatus.getStn3Need() == 1) {
                    // Let the Robot Stop
                    needStop = true;
                    System.out.println("Robot Stop Requested on Station 3");
                }
                break;
        }

        if(needStop) {
            System.out.println("Check send NearStation to Robot");
            rb.nearStation();
            needStop = false;
        }

    }
}
