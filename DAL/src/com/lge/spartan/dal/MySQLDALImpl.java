package com.lge.spartan.dal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//To correctly size a connection pool for your application, 
//create load test scripts with tools such as Apache JMeter or The Grinder,
//and load test your application.
/**
 *
 * @author vijay.rachabattuni
 */
import static com.lge.spartan.dal.MySQLDALImpl.logger;
import com.lge.spartan.data.Customer;
import com.lge.spartan.data.OrderDetails;
import com.lge.spartan.data.OrderInfo;
import com.lge.spartan.data.OrderStatus;
import com.lge.spartan.data.Robot;
import com.lge.spartan.data.RobotState;
import com.lge.spartan.data.RobotStatus;
import com.lge.spartan.data.Station;
import com.lge.spartan.data.StationType;
import com.lge.spartan.data.Warehouse;
import com.lge.spartan.data.Widget;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
//import org.apache.log4j.LogManager;

//public class MySQLDALImpl implements IDAL  {
public class MySQLDALImpl implements DAL {

    // Define a static logger variable so that it references the
// Logger instance named "MyApp".
    static final Logger logger = LogManager.getLogger(MySQLDALImpl.class.getName());

    Connection DBConn = null;           // MySQL connection handle    
    Statement s = null;                 // SQL statement pointer
    ResultSet res = null;               // SQL query result set pointer
    String SQLStatement;                // SQL query
    String userName;
    String pwd;
    DataSource ds;
    Connection con = null;
    final int WAREHOUSE_SERVER_HEARTBEAT = 2000;//2 seconds
    final int ROBOT_HEARTBEAT = 3000;   //3 seconds

    final boolean CONNECTION_POOL = false;

    public boolean Initialize(String serverIPAddress, String user, String p) {
        logger.entry();
        System.out.println("***********************************************");
        System.out.println("DAL:Initialize##" + serverIPAddress + "," + user);
        userName = user;
        pwd = p;

        String SQLServerIP = serverIPAddress;
        String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/spartan";

        if (CONNECTION_POOL == true) {
            try {
                InitialContext ctx = new InitialContext();
                Class.forName("com.mysql.jdbc.Driver");
                ds = (DataSource) ctx.lookup(sourceURL);
                MysqlDataSource ds = (MysqlDataSource) ctx.lookup(sourceURL);
                ds.setServerName(SQLServerIP);
                ds.setUser(user);
                ds.setPassword(p);
            } catch (Exception e) {
                logger.error("Exception " + e);
                System.out.println("DAL:Initialize:Exception:" + e);
            }
        } else {
            try {
                //load JDBC driver class for MySQL
                Class.forName("com.mysql.jdbc.Driver");
                //create a connection to the db 
                DBConn = DriverManager.getConnection(sourceURL, user, p);
            } catch (Exception e) {
                logger.error("Exception " + e);
                System.out.println("DAL:Initialize:Exception:" + e);
                return false;
            } // end try-catch
        }
        return logger.exit(true);
    }

    public boolean Uninitialize() {
        logger.entry();
        System.out.println("DAL:Uninitialize()");
        try {
            if (DBConn != null) {
                DBConn.close();
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:Uninitialize:Exception:" + e);
            return logger.exit(false);
        } // end try-catch
        return logger.exit(true);
    }

    public synchronized ArrayList<Customer> GetCustomers() {
        logger.entry();
        System.out.println("DAL:GetCustomers()");
        ArrayList<Customer> custList = null;
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select * from customer";
            rs = s.executeQuery(SQLStatement);

            custList = FillCustomerList(rs);
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetCustomers:Exception:" + e);
            CleanUp(rs, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return custList;
    }

    private void CreateStmnt() throws SQLException {
        if (CONNECTION_POOL == true) {
            con = ds.getConnection(userName, pwd);
            s = con.createStatement();
        } else {
            s = DBConn.createStatement();
        }
    }

    private void CleanUp(ResultSet rs, Statement stmt) {
        logger.entry();
        // it is a good idea to release
        // resources in a finally{} block
        // in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Exception " + e);
                System.out.println("DAL:Cleanup:Exception:" + e);
            } // ignore

            rs = null;
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("Exception " + e);
            } // ignore
            stmt = null;
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Exception " + e);
            } // ignore
            con = null;
        }
    }

    private ArrayList<Customer> FillCustomerList(ResultSet rs) throws SQLException {
        logger.entry();
        ArrayList<Customer> custList = new ArrayList<>();
        // Get the information from the database. Display the
        // first and last name, address, phone number, address, and
        // order date. Same the ordertable name - this is the name of
        // the table that is created when an order is submitted that
        // contains the list of order items.
        while (rs.next()) {
            Customer c = new Customer();
            c.setPhone(rs.getString(1));
            c.setFname(rs.getString(2));
            c.setLname(rs.getString(3));
            c.setAddress(rs.getString(4));
            c.setEmail(rs.getString(5));
            custList.add(c);
        } // for each element in the return SQL query
        return custList;
    }

    public synchronized int AddOrder(List<OrderDetails> orderList, Customer cust) {
        if (orderList == null) {
            System.out.println("DAL:AddOrder:Orders are empty. So Order cannot be added");
            return -1;
        }
        if (cust == null) {
            System.out.println("DAL:AddOrder:Customer is null. So Order cannot be added");
            return -1;
        }
        return AddOrder(orderList, cust.getFname(), cust.getLname(), cust.getPhone(), cust.getAddress(), cust.getEmail());
    }

    ///Return OrderNo if success. If failure, return -1
    private int AddOrder(List<OrderDetails> orderList, String fname, String lname, String phone, String address, String email) {
        logger.entry();
        System.out.println("DAL:AddOrder: FName:" + fname + ", LName:" + lname + ", Phone:" + phone + ", Address:" + address + ", Email:" + email);
        int orderNo = 0;
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            CreateStmnt();

            try {
                //If the customer phone is already existing; this insert will fail. 
                //Ok no problem.. continue with the other insertions....
                SQLStatement = "insert into customer values ('" + phone + "','" + fname + "','" + lname + "','" + address + "','" + email + "');";
                executeUpdateVal = s.executeUpdate(SQLStatement);
            } catch (Exception e) {
                logger.error("Exception " + e);
                System.out.println("DAL:AddOrder:Insert customer:Exception:" + e);
                System.out.println("DAL:AddOrder:Insert customer:Exception:Customer is already existing. So adding the order to this customer");
            }

            SQLStatement = "insert into orderinfo(phone) values ('" + phone + "');";
            executeUpdateVal = s.executeUpdate(SQLStatement);

            SQLStatement = "(select max(orderNo) from orderinfo)";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                orderNo = res.getInt(1); //get integer from the first column of the result
            }
            //SQLStatement = "insert into orderdetails values (" + orderNo +"," + widgetId + "," + quant + ");";
            String stmnt = "insert into orderdetails values ";
            int i = 0;
            int count = orderList.size();
            for (OrderDetails od : orderList) {
                i++;
                String str = "(";
                str += orderNo + "," + od.getWidgetId() + "," + od.getQuantity();
                if (i == count) {
                    str += ");";
                } else {
                    str += "),";
                }
                stmnt += str;
            }

            executeUpdateVal = s.executeUpdate(stmnt);
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:AddOrder:Exception:" + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        System.out.println("DAL:AddOrder:Order added successfully. Order No is " + orderNo);
        return orderNo;
    }

    ///Return OrderNo if success. If failure, return -1
    public synchronized int AddWidget(String widgetName, String widgetDesc, int quant, int stationId, double cost) {
        logger.entry();
        System.out.println("DAL:AddWidget:widgetName: " + widgetName + ", widgetDesc: " + widgetDesc + ", quantity:" + quant + ", stationId:" + stationId);
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            CreateStmnt();
            SQLStatement = "insert into widget (name, description, quantity, stationId, cost) values ('" + widgetName + "','" + widgetDesc + "'," + quant + "," + stationId + "," + cost + ");";
            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:AddWidget:Widget added successfully");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:AddWidget:Exception:" + e);
            CleanUp(null, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(null, s);
        }
        return 0;
    }

    public synchronized int IncWidgets(String widgetName, int increment) {
        logger.entry();
        System.out.println("DAL:IncWidgets:widgetName " + widgetName + ", increment " + increment);
        int quant = 0;
        try {
            CreateStmnt();

            int executeUpdateVal;           // Return value from execute indicating effected rows
            SQLStatement = "select quantity from widget where name = '" + widgetName + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quant = res.getInt(1);
            }

            SQLStatement = "update widget set quantity = " + (quant + increment) + " where name = '" + widgetName + "';";
            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:IncWidgets:Incremented Widget quantity successfully");
            } else {
                System.out.println("DAL:IncWidgets:Increment Widget quantity failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:IncWidgets:Exception:" + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return 0;
    }

    public synchronized int DecWidgets(String widgetName, int decrement) {
        logger.entry();
        System.out.println("DAL:DecWidgets:widgetName " + widgetName + ", decrement " + decrement);
        int quant = 0;
        try {
            CreateStmnt();

            SQLStatement = "select quantity from widget where name = '" + widgetName + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quant = res.getInt(1);
            }

            int executeUpdateVal;           // Return value from execute indicating effected rows

            SQLStatement = "update widget set quantity = " + (quant - decrement) + " where name = '" + widgetName + "';";
            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:DecWidgets:Decremented Widget quantity successfully");
            } else {
                System.out.println("DAL:DecWidgets:Decrement Widget quantity failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:DecWidgets:Exception:" + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return 0;
    }

    public synchronized int DecWidgets(int widgetId, int decrement) {
        logger.entry();
        System.out.println("DAL:DecWidgets:widgetId " + widgetId + ", decrement " + decrement);
        int quant = 0;
        try {
            CreateStmnt();

            SQLStatement = "select quantity from widget where widgetId = '" + widgetId + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quant = res.getInt(1);
            }

            int executeUpdateVal;           // Return value from execute indicating effected rows

            SQLStatement = "update widget set quantity = " + (quant - decrement) + " where widgetId = '" + widgetId + "';";
            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:DecWidgets:Decremented Widget quantity by ID successfully");
            } else {
                System.out.println("DAL:DecWidgets:Decrement Widget quantity by ID failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:DecWidgets:Exception:" + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return 0;
    }

    public synchronized OrderStatus GetOrderStatus(int orderNo) {
        System.out.println("DAL:GetOrderuStatus:OrderNo:" + orderNo);
        logger.entry();
        int status = OrderStatus.None.ordinal();
        try {
            CreateStmnt();

            SQLStatement = "select status from orderinfo where orderno = " + orderNo + ";";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                status = res.getInt(1);
                System.out.println("DAL:GetOrderuStatus:Got the status of Order# " + orderNo + " successfully");
            } else {
                System.out.println("DAL:GetOrderuStatus:Getting the status of Order# " + orderNo + " failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetOrderuStatus:Exception:" + e);
            CleanUp(res, s);
            return OrderStatus.None;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return OrderStatus.values[status];
    }

    private ArrayList<OrderInfo> GetOrders(String sqlStmnt) {
        logger.entry();
        ArrayList<OrderInfo> orderList = null;
        System.out.println("DAL:GetOrders:SQL Statement:" + sqlStmnt);
        try {
            CreateStmnt();
            SQLStatement = sqlStmnt;
            res = s.executeQuery(SQLStatement);

            orderList = new ArrayList<>();
            while (res.next()) {
                OrderInfo oi = new OrderInfo();
                oi.setOrderNo(res.getInt(1));
                oi.setOrderTime(res.getString(2));
                oi.setShippingTime(res.getString(3));
                oi.setStatus(res.getInt(4));
                String strPhone = res.getString(5);//phone number
                GetCustomerAndOrderDetails(strPhone, oi);

                orderList.add(oi);
            }
            System.out.println("DAL:GetOrders:Orders count:" + orderList.size());
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetOrders:Exception:" + e);
            CleanUp(res, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return orderList;
    }

    private void GetCustomerAndOrderDetails(String strPhone, OrderInfo oi) {
        logger.entry();
        ResultSet rs1 = null;
        try {
            CreateStmnt();
            ArrayList<Customer> cust = null;
            String stmnt = "select * from customer where phone = '" + strPhone + "';";
            rs1 = s.executeQuery(stmnt);
            cust = FillCustomerList(rs1);
            if (cust.size() > 0) {
                oi.setCust(cust.get(0));//will return only 1 customer as we are querying by strPhone which is unique
            } else {
                System.out.println("DAL:GetCustomerAndOrderDetails:No customers with phone " + strPhone);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetCustomerAndOrderDetails:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs1, s);
        }
        GetOrderDetails(oi);
    }

    public synchronized OrderInfo PickFirstOrder() {
        logger.entry();
        ArrayList<OrderInfo> orderList = GetOrders(
                "SELECT * FROM orderinfo WHERE (status = 0) ORDER BY status DESC, orderTime LIMIT 1");

        if (orderList.size() == 1) {
            GetOrderDetails(orderList.get(0));
            return (OrderInfo) orderList.get(0);
        }

        return null;
    }

    private void GetOrderDetails(OrderInfo oi) {
        logger.entry();
        ResultSet rs2 = null;
        try {
            CreateStmnt();
            String stmnt = "select * from orderdetails where orderno = " + oi.getOrderNo() + ";";
            rs2 = s.executeQuery(stmnt);
            oi.setListOrderDetails(new ArrayList<OrderDetails>());
            while (rs2.next()) {
                OrderDetails od = new OrderDetails();
                od.setWidgetId(rs2.getInt(2));
                GetWidgetName(od);
                od.setQuantity(rs2.getInt(3));
                oi.getListOrderDetails().add(od);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetOrderDetails:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs2, s);
        }
    }

    private void GetWidgetName(OrderDetails od) throws SQLException {
        Statement s1 = null;
        ResultSet rs3 = null;
        try {
            //Get the widget name from the widget id - Start
            s1 = DBConn.createStatement();
            String stmnt1 = "select name from widget where widgetId = " + od.getWidgetId() + ";";
            rs3 = s1.executeQuery(stmnt1);
            if (rs3.next()) {
                od.setWidgetName(rs3.getString(1));
            }
            //Get the widget name from the widget id - End
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWidgetName:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs3, s1);
        }
    }

    public synchronized ArrayList<OrderInfo> GetOrders(OrderStatus orderStatus) {
        logger.entry();
        if (orderStatus != OrderStatus.All) {
            return GetOrders("select * from orderinfo where status = " + orderStatus.ordinal() + ";");
        } else {
            return GetOrders("select * from orderinfo");
        }
    }

    public synchronized ArrayList<OrderInfo> GetOrdersByPhone(String phone /*, Enum orderStatus*/) {
        logger.entry();
        return GetOrders("select * from orderinfo where phone = '" + phone + "';");
    }

    public synchronized ArrayList<Widget> GetWidgets() {
        logger.entry();
        ArrayList<Widget> widgetList = null;
        try {
            CreateStmnt();

            SQLStatement = "select * from widget";
            res = s.executeQuery(SQLStatement);

            widgetList = new ArrayList<>();
            while (res.next()) {
                Widget w = new Widget();
                w.setWidgetId(res.getInt(1));
                w.setName(res.getString(2));
                w.setDesc(res.getString(3));
                w.setQuantity(res.getInt(4));
                w.setStationId(res.getInt(5));
                w.setCost(res.getFloat(6));
                widgetList.add(w);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWidgets:Exception:" + e);
            CleanUp(res, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return widgetList;
    }

    public int GetWidgetQuantity(String widgetName) {
        logger.entry();
        int quantity = 0;
        try {
            CreateStmnt();

            SQLStatement = "select quantity from widget where name = '" + widgetName + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quantity = res.getInt(1);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWidgetQuantity:Exception:" + e);
            CleanUp(res, s);
            return -9999999;//not a good approach.. Any better approach? 
            //Better approach: Return bool and pass quantity as out param. 
            //Unfortunately java does not support int to be passed as reference. Ah... :( or I dont know
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        System.out.println("DAL:GetWidgetQuantity:Quantity retreived successfully. It is " + quantity);
        return quantity;
    }

    public synchronized int GetWidgetQuantity(int widgetId) {
        logger.entry();
        int quantity = 0;
        try {
            CreateStmnt();

            SQLStatement = "select quantity from widget where widgetId = '" + widgetId + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quantity = res.getInt(1);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWidgetQuantity:Exception:" + e);
            CleanUp(res, s);
            return -9999999;//not a good approach.. Any better approach? 
            //Better approach: Return bool and pass quantity as out param. 
            //Unfortunately java does not support int to be passed as reference. Ah... :( or I dont know
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        System.out.println("DAL:GetWidgetQuantity:Quantity by ID retreived successfully. It is " + quantity);
        return quantity;
    }

    public synchronized int GetWidgetStation(int widgetId) {
        logger.entry();
        int stationId = 0;
        try {
            CreateStmnt();

            SQLStatement = "select stationId from widget where widgetId = '" + widgetId + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                stationId = res.getInt(1);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWidgetQuantity:Exception:" + e);
            CleanUp(res, s);
            return -9999999;//not a good approach.. Any better approach? 
            //Better approach: Return bool and pass quantity as out param. 
            //Unfortunately java does not support int to be passed as reference. Ah... :( or I dont know
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        System.out.println("DAL:GetWidgetQuantity:StationId retreived successfully. It is " + stationId);
        return stationId;
    }

    public synchronized boolean UpdateOrderStatus(int orderNo, Enum orderStatus) {
        logger.entry();
        System.out.println("DAL:UpdateOrderStatus:OrderNo: " + orderNo + ", Status: " + orderStatus.toString());
        try {
            if (orderStatus != OrderStatus.All) {
                int ret;
                CreateStmnt();
                if (orderStatus == OrderStatus.Complete) {
                    SQLStatement = "update orderinfo set status = " + orderStatus.ordinal() + ", shippingTime = now() where orderNo = " + orderNo + ";";
                } else {
                    SQLStatement = "update orderinfo set status = " + orderStatus.ordinal() + " where orderNo = " + orderNo + ";";
                }

                ret = s.executeUpdate(SQLStatement);
                if (ret > 0) {
                    System.out.println("update of orderstatus is successful");
                }
            } else {
                System.out.println("update of orderstatus is failed");
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:UpdateOrderStatus:Exception:" + e);
            CleanUp(res, s);
            return false;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return true;
    }

    ///Return 0 if success. If failure, return -1
    @Override
    public synchronized int AddRobotStatus(RobotStatus robotStatus) {
        logger.entry();
        System.out.println("DAL:AddRobotStatus:orderNo: " + robotStatus.getOrderNo());
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            CreateStmnt();
            SQLStatement
                    /* = "insert into robotstatus (orderNo, stn1Visited, stn2Visited, stn3Visited, stn4Visited, stn1Need, stn2Need, stn3Need, stn4Need, nextStn, state) values ('"
                     + robotStatus.getOrderNo() + "','"
                     + robotStatus.getStn1Visited() + "','"
                     + robotStatus.getStn2Visited() + "','"
                     + robotStatus.getStn3Visited() + "','"
                     + robotStatus.getStn4Visited() + "','"
                     + robotStatus.getStn1Need() + "','"
                     + robotStatus.getStn2Need() + "','"
                     + robotStatus.getStn3Need() + "','"
                     + robotStatus.getStn4Need() + "','"
                     + robotStatus.getNextStn() + "','"
                     + robotStatus.getState().ordinal() + "'"
                     + ");";*/
                    = "insert into robotmoves (robotId, orderNo, stationsToVisit, currentStation, nextStation) values ('"
                    + robotStatus.getRobotId() + "','"
                    + robotStatus.getOrderNo() + "','"
                    + GetStringFromArrayWithComma(robotStatus.getStationsToVisit()) + "','"
                    + robotStatus.getCurrentStation() + "','"
                    + robotStatus.getNextStation() + "'"
                    + ");";

            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:RobootStatus added successfully");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:Add:RobotStatus:Exception:" + e);
            CleanUp(null, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(null, s);
        }
        return 0;
    }

    private String GetStringFromArrayWithComma(ArrayList<Integer> l) {
        String s = "";
        int count = 0;
        int size = l.size();
        for (Integer i : l) {
            count++;
            s += i.toString();
            if (size != count) {
                s += ",";
            }
        }
        return s;
    }

    //Obsolete method. Do not use it. Instead use GetRobotMoves
    public synchronized RobotStatus GetRobotStatus(int orderNo) {
        logger.entry();
        RobotStatus robotStatus = null;

        try {
            CreateStmnt();

            String stmnt = "select * from robotstatus where orderNo = '" + orderNo + "';";
            res = s.executeQuery(stmnt);

            if (res.next()) {
                robotStatus = new RobotStatus();

                robotStatus.setRobotId(res.getInt(1));
                robotStatus.setOrderNo(res.getInt(2));
                /*   robotStatus.setStn1Visited(res.getInt(2));
                 robotStatus.setStn2Visited(res.getInt(3));
                 robotStatus.setStn3Visited(res.getInt(4));
                 robotStatus.setStn4Visited(res.getInt(5));
                 robotStatus.setStn1Need(res.getInt(6));
                 robotStatus.setStn2Need(res.getInt(7));
                 robotStatus.setStn3Need(res.getInt(8));
                 robotStatus.setStn4Need(res.getInt(9));
                 robotStatus.setNextStn(res.getInt(10));
                 robotStatus.setState(res.getInt(11));*/
                System.out.println("DAL:GetRobotStatus:RobotStatus retreived successfully.");
            } else {
                System.out.println("DAL:GetRobotStatus:RobotStatus retreived failed.");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobotStatus:Exception:" + e);
            CleanUp(res, s);
        } // end try-catch
        finally {
            CleanUp(res, s);
        }

        return robotStatus;
    }

    public synchronized int UpdateRobotStatus(RobotStatus robotStatus) {
        logger.entry();
        int ret = -1;

        try {
            CreateStmnt();

            int executeUpdateVal;           // Return value from execute indicating effected rows
            String stmnt;
            ArrayList<Integer> tmpList = robotStatus.getStationsVisited();

            if(tmpList == null || tmpList.size() == 0) {
                stmnt
                        = "update robotmoves set stationsToVisit = '" + GetStringFromArrayWithComma(robotStatus.getStationsToVisit())
                        + "', currentStation = " + robotStatus.getCurrentStation()
                        + ", nextStation = " + robotStatus.getNextStation()
                        + " where orderNo = '" + robotStatus.getOrderNo() + "' and robotId = " + robotStatus.getRobotId() + ";";
            } else {
                stmnt
                        = "update robotmoves set stationsToVisit = '" + GetStringFromArrayWithComma(robotStatus.getStationsToVisit())
                        + "', stationsVisited = '" + GetStringFromArrayWithComma(robotStatus.getStationsVisited())
                        + "', currentStation = " + robotStatus.getCurrentStation()
                        + ", nextStation = " + robotStatus.getNextStation()
                        + " where orderNo = '" + robotStatus.getOrderNo() + "' and robotId = " + robotStatus.getRobotId() + ";";
            }

            executeUpdateVal = s.executeUpdate(stmnt);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:UpdateRobotStatus executed successfully");
                ret = 0;
            } else {
                System.out.println("DAL:UpdateRobotStatus failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:UpdateRobotStatus:Exception:" + e);
            CleanUp(res, s);
            return ret;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }

        System.out.println("DAL:UpdateRobotStatus finished successfully.");
        return ret;
    }

    public boolean IsDBAvailable() {
        ResultSet rs = null;
        try {
            CreateStmnt();
            String stmnt = "select 1";
            rs = s.executeQuery(stmnt);
        } catch (Exception e) {
            CleanUp(rs, s);
            return false;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return true;
    }

    public synchronized ArrayList<Robot> GetRobots() {
        logger.entry();
        System.out.println("DAL:GetRobots()");
        ArrayList<Robot> robotList = null;
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select * from robot";
            rs = s.executeQuery(SQLStatement);
            robotList = new ArrayList();
            while (rs.next()) {
                Robot r = new Robot();
                r.setRobotId(rs.getInt(1));
                r.setWarehouseId(rs.getInt(2));
                r.setName(rs.getString(3));
                r.setDesc(rs.getString(4));
                r.setIpaddress(rs.getString(5));
                r.setStatus(RobotState.values[rs.getInt(6)]);
                robotList.add(r);
            } // for each ele
            System.out.println("DAL:GetRobots:Success");
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobots:Exception:" + e);
            CleanUp(rs, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return robotList;
    }

    public synchronized boolean IsRobotAvailable(int robotId) {
        logger.entry();
        System.out.println("DAL:IsRobotAvailable()");
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select heartbeatts from robot where robotId = " + robotId + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                long lDateTime = new Date().getTime();
                long lPrevTime = 0;
                System.out.println("Date() - Time in milliseconds: " + lDateTime);
                lPrevTime = rs.getLong(1);
                long ldiff = lDateTime - lPrevTime;
                System.out.println("DAL:IsWarehouseServerAvailable:CurTS: " + lDateTime + ", PrevTS:" + lPrevTime + ", Diff:" + ldiff);
                if (ldiff > ROBOT_HEARTBEAT){
                    System.out.println("DAL:IsRobotAvailable:Not available");
                    return false;
                }
            } // for each ele
            System.out.println("DAL:IsRobotAvailable: Available");
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:IsRobotAvailable:Exception:" + e);
            CleanUp(rs, s);
            return false;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return true;
    }

    //This API should be invoked by Robot every x seconds ( x <= 1 ) preferable x is 1
    public synchronized boolean SetRobotTS(int robotId) {
        logger.entry();
        System.out.println("DAL:SetRobotTS()");
        boolean bRes = false;
        ResultSet rs = null;
        try {
            CreateStmnt();

            long lDateTime = new Date().getTime();
            System.out.println("Date() - Time in milliseconds: " + lDateTime);

            SQLStatement = "update robot set heartbeatts = " + lDateTime + " where robotId = " + robotId + ";";

            int retVal = s.executeUpdate(SQLStatement);
            if (retVal > 0) {
                System.out.println("DAL:SetRobotTS:Success");
                bRes = true;
            } else {
                System.out.println("DAL:SetRobotTS:failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:SetRobotTS:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return bRes;
    }

    public synchronized void SetRobotErr(int robotId, int errCode) {
        logger.entry();
        System.out.println("DAL:SetRobotErr:robotId " + robotId + ", errCode " + errCode);
        try {
            CreateStmnt();

            int executeUpdateVal;           // Return value from execute indicating effected rows

            SQLStatement = "update robot set errcode = " + errCode + ", status = 2 where robotId = '" + robotId + "';";
            executeUpdateVal = s.executeUpdate(SQLStatement);
            if (executeUpdateVal > 0) {
                System.out.println("DAL:IncWidgets:Incremented Widget quantity successfully");
            } else {
                System.out.println("DAL:IncWidgets:Increment Widget quantity failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:SetRobotErr:Exception:" + e);
            CleanUp(res, s);
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
    }

    public String GetRobotErr(int robotId) {
        logger.entry();
        System.out.println("DAL:GetRobotState(): RobotId:" + robotId);
        ResultSet rs = null;
        int errCode = 0;

        try {
            CreateStmnt();
            SQLStatement = "select errcode from robot where status = 2 and robotId = " + robotId + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                errCode = rs.getInt(1);
                System.out.println("DAL:GetRobotErr():Succe: RobotId:" + robotId + ", ErrCode: " + errCode);
            } // for each ele
            else {
                System.out.println("DAL:GetRobotErr(): Failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobotErr:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }

        if(errCode == 0) return null;

        String errMsg = null;
        
        try {
            CreateStmnt();
            SQLStatement = "select msg from roboterr where errcode = " + errCode + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                errMsg = rs.getString(1);
                System.out.println("DAL:GetRobotErr():Succe: RobotId:" + robotId + ", ErrMsg: " + errMsg);
            } // for each ele
            else {
                System.out.println("DAL:GetRobotErr(): Failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobotErr:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }

        return errMsg;
    }

    public synchronized RobotState GetRobotState(int robotId) {
        logger.entry();
        System.out.println("DAL:GetRobotState(): RobotId:" + robotId);
        ResultSet rs = null;
        RobotState r = null;
        try {
            CreateStmnt();
            SQLStatement = "select status from robot where robotId = " + robotId + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                r = RobotState.values[rs.getInt(1)];
                System.out.println("DAL:GetRobotState():Succe: RobotId:" + robotId + ", Satus: " + r.toString());
            } // for each ele
            else {
                System.out.println("DAL:GetRobotState(): Failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobotState:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return r;
    }

    public boolean SetRobotState(int robotId, RobotState r) {
        logger.entry();
        System.out.println("DAL:SetRobotState(): RobotId:" + robotId);
        try {
            CreateStmnt();
            SQLStatement = "update robot set status = " + r.ordinal() + " where robotId = " + robotId + ";";
            System.out.println("DAL:SetRobotState(): Stmnt: " + SQLStatement);
            int i = s.executeUpdate(SQLStatement);
            if (i > 0) {
                System.out.println("DAL:SetRobotState():Succe: RobotId:" + robotId + ", Satus: " + r.toString());
            } // for each ele
            else {
                System.out.println("DAL:SetRobotState(): Failed");
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:SetRobotState:Exception:" + e);
            CleanUp(null, s);
            return false;
        } // end try-catch
        finally {
            CleanUp(null, s);
        }
        return true;
    }

    public synchronized ArrayList<Station> GetStations() {
        logger.entry();
        System.out.println("DAL:GetStations()");
        ArrayList<Station> stationList = null;
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select * from station";
            rs = s.executeQuery(SQLStatement);
            stationList = new ArrayList();
            while (rs.next()) {
                Station s = new Station();
                s.setStationId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDesc(rs.getString(3));
                s.setWarehouseId(rs.getInt(4));
                s.setType(StationType.values[rs.getInt(5)]);
                stationList.add(s);
            } // for each ele
            System.out.println("DAL:GetStations:Success");
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetStations:Exception:" + e);
            CleanUp(rs, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return stationList;
    }

    public synchronized ArrayList<Warehouse> GetWarehouses() {
        logger.entry();
        System.out.println("DAL:GetWarehouses()");
        ArrayList<Warehouse> warehouseList = null;
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select * from warehouse";
            rs = s.executeQuery(SQLStatement);
            warehouseList = new ArrayList();
            while (rs.next()) {
                Warehouse s = new Warehouse();
                s.setWarehouseId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDesc(rs.getString(3));
                s.setNoOfInvStations(rs.getInt(4));
                s.setNoOfShippingStations(rs.getInt(5));
                s.setNoOfRobots(rs.getInt(6));
                s.setIpaddress(rs.getString(7));
                s.setStatus(rs.getInt(8));
                warehouseList.add(s);
            } // for each ele
            System.out.println("DAL:GetWarehouses:Success");
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetWarehouses:Exception:" + e);
            CleanUp(rs, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return warehouseList;
    }

    public synchronized RobotStatus GetRobotMoves(int robotId, int orderId) {
        logger.entry();
        System.out.println("DAL:GetRobotMoves(): RobotId:" + robotId + ", OrderId:" + orderId);
        ResultSet rs = null;
        RobotStatus r = null;
        try {
            CreateStmnt();
            SQLStatement = "select * from robotmoves where robotId = " + robotId + " and orderNo = " + orderId + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                r = new RobotStatus();
                r.setRobotId(robotId);
                r.setOrderNo(orderId);
                String stnsToVisit = rs.getString(3);
                String stnsVisited = rs.getString(4);
                r.setCurrentStation(rs.getInt(5));
                r.setNextStation(rs.getInt(6));
                r.setStationsToVisit(DelimitStringWithComma(stnsToVisit));
                if(stnsVisited != null) r.setStationsVisited(DelimitStringWithComma(stnsVisited));
                System.out.println("DAL:GetRobotMoves():Success: RobotId:" + robotId + ", StnsToVisit:" + stnsToVisit + ", StnsVisited:" + stnsVisited + ", CurStn:" + r.getCurrentStation() + ", NextStn:" + r.getNextStation());
            } // for each ele
            else {
                System.out.println("DAL:GetRobotMoves(): Failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetRobotMoves:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return r;
    }

    private ArrayList<Integer> DelimitStringWithComma(String st) {
        String[] data = st.split(",");
        ArrayList<Integer> list = null;
        if (data.length > 0) {
            list = new ArrayList();
        } else {
            System.out.println("DAL:DelimitStringWithComma: No comma found in the string " + st);
            return null;
        }
        for (String s : data) {
            try {
                int i = Integer.parseInt(s);
                list.add(i);
            } catch (Exception e) {
                System.out.println("DAL:DelimitStringWithComma: Exception:" + e);
            }
        }
        return list;
    }

    //This API should be invoked by Supervisor app every 2 seconds or greater (recommended is 2 seconds)
    public synchronized boolean IsWarehouseServerAvailable(int warehouseId) {
        logger.entry();
        System.out.println("DAL:IsWarehouseServerAvailable()");
        ResultSet rs = null;
        try {
            CreateStmnt();
            SQLStatement = "select serverheartbeatts from warehouse where warehouseId = " + warehouseId + ";";
            rs = s.executeQuery(SQLStatement);
            if (rs.next()) {
                long lDateTime = new Date().getTime();
                long lPrevTime = 0;
                System.out.println("Date() - Time in milliseconds: " + lDateTime);
                lPrevTime = rs.getLong(1);
                long ldiff = lDateTime - lPrevTime;
                System.out.println("DAL:IsWarehouseServerAvailable:CurTS: " + lDateTime + ", PrevTS:" + lPrevTime + ", Diff:" + ldiff);
                if (ldiff > WAREHOUSE_SERVER_HEARTBEAT){
                    System.out.println("DAL:IsWarehouseServerAvailable:Not available");
                    return false;
                }
            } // for each ele
            System.out.println("DAL:IsWarehouseServerAvailable: Available");
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:IsWarehouseServerAvailable:Exception:" + e);
            CleanUp(rs, s);
            return false;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return true;
    }

    //This API should be invoked by Warehouse server every x seconds ( x <= 1 ) preferable x is 1
    public synchronized boolean SetWarehouseServerTS(int warehouseId) {
        logger.entry();
        System.out.println("DAL:IsWarehouseServerAvailable()");
        ResultSet rs = null;
        boolean bRes = false;
        try {
            CreateStmnt();

            long lDateTime = new Date().getTime();
            System.out.println("Date() - Time in milliseconds: " + lDateTime);

            SQLStatement = "update warehouse set serverheartbeatts = " + lDateTime + " where warehouseId = " + warehouseId + ";";

            int retVal = s.executeUpdate(SQLStatement);
            if (retVal > 0) {
                System.out.println("DAL:IsWarehouseServerAvailable:Success");
                bRes = true;
            } else {
                System.out.println("DAL:IsWarehouseServerAvailable:failed");
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:IsWarehouseServerAvailable:Exception:" + e);
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return bRes;
    }
}
