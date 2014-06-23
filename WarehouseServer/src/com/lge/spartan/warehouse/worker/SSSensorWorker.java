import java.util.ArrayList;
import java.util.List;

import com.lge.spartan.dal.*;
import com.lge.spartan.data.*;

public class SSSensorWorker implements WHWorker {

    String dbURL = WHConfig.GetDBIP();
    WHRobotInf rb;
    DAL dal;

    public void procRequest(int idx) {
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
            rb.nearStation();
        }
    }
}
