import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class SSSwitchWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    DAL dal;

    public void procRequest(int idx) {
        System.out.println("proc Request@SSSwitchWorker[" + idx + "]");

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
        if(robotStatus.getNextStn() != 4) {
            System.out.println("Not a End Conditon... ");
            return;
        }

        robotStatus.setState(RobotState.Complete.ordinal());
        robotStatus.setNextStn(-1);

        dal.UpdateOrderStatus(orderInfo.getOrderNo(), OrderStatus.Complete);
        dal.UpdateRobotStatus(robotStatus);
        
    }
}
