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
import com.lge.spartan.data.Widget;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.ArrayList;
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

    public ArrayList<Customer> GetCustomers() {
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
            custList.add(c);
        } // for each element in the return SQL query
        return custList;
    }

    public int AddOrder(List<OrderDetails> orderList, Customer cust) {
        if (orderList == null) {
            System.out.println("DAL:AddOrder:Orders are empty. So Order cannot be added");
            return -1;
        }
        if (cust == null) {
            System.out.println("DAL:AddOrder:Customer is null. So Order cannot be added");
            return -1;
        }
        return AddOrder(orderList, cust.getFname(), cust.getLname(), cust.getPhone(), cust.getAddress());
    }

    ///Return OrderNo if success. If failure, return -1
    private int AddOrder(List<OrderDetails> orderList, String fname, String lname, String phone, String address) {
        logger.entry();
        System.out.println("DAL:AddOrder: FName:" + fname + ", LName:" + lname + ", Phone:" + phone + ", Address:" + address);
        int orderNo = 0;
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            CreateStmnt();

            try {
                //If the customer phone is already existing; this insert will fail. 
                //Ok no problem.. continue with the other insertions....
                SQLStatement = "insert into customer values ('" + phone + "','" + fname + "','" + lname + "','" + address + "');";
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
    public int AddWidget(String widgetName, String widgetDesc, int quant, int stationId) {
        logger.entry();
        System.out.println("DAL:AddWidget:widgetName: " + widgetName + ", widgetDesc: " + widgetDesc + ", quantity:" + quant + ", stationId:" + stationId);
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            CreateStmnt();
            SQLStatement = "insert into widget (name, description, quantity, stationId) values ('" + widgetName + "','" + widgetDesc + "'," + quant + "," + stationId + ");";
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

    public int IncWidgets(String widgetName, int increment) {
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

    public int DecWidgets(String widgetName, int decrement) {
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

    public int DecWidgets(int widgetId, int decrement) {
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

    public int GetOrderStatus(int orderNo) {
        System.out.println("DAL:GetOrderuStatus:OrderNo:" + orderNo);
        logger.entry();
        int status = 0;
        try {
            CreateStmnt();

            SQLStatement = "select status from orderinfo where orderno = " + orderNo + ";";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                status = res.getInt(1);
            }
        } catch (Exception e) {
            logger.error("Exception " + e);
            System.out.println("DAL:GetOrderuStatus:Exception:" + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        System.out.println("DAL:GetOrderuStatus:Got the status of Order# " + orderNo + " successfully");
        return status;
    }

    private ArrayList<OrderInfo> GetOrders(String sqlStmnt) {
        logger.entry();
        ArrayList<OrderInfo> orderList = null;
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

    public OrderInfo PickFirstOrder() {
        logger.entry();
        ArrayList<OrderInfo> orderList = GetOrders(
        "SELECT * FROM orderinfo WHERE (status = 0 OR status = 2) ORDER BY status DESC, orderTime LIMIT 1");

        if(orderList.size() == 1) {
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
                //GetWidgetName(od);
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

    public ArrayList<OrderInfo> GetProgressOrders() {
        logger.entry();
        return GetOrders("select * from orderinfo where status = 1");
    }

    public ArrayList<OrderInfo> GetShippedOrders() {
        logger.entry();
        return GetOrders("select * from orderinfo where status = 3");
    }

    public ArrayList<OrderInfo> GetPendingOrders() {
        logger.entry();
        return GetOrders("select * from orderinfo where status <> 3");
    }

    public ArrayList<OrderInfo> GetBackorderedOrders() {
        logger.entry();
        return GetOrders("select * from orderinfo where status = 2"); //2 - Backorder
    }

    public ArrayList<OrderInfo> GetOrdersByPhone(String phone /*, Enum orderStatus*/) {
        logger.entry();
        return GetOrders("select * from orderinfo where phone = '" + phone + "';");
    }

    public ArrayList<Widget> GetWidgets() {
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

    public int GetWidgetQuantity(int widgetId) {
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

    public int GetWidgetStation(int widgetId) {
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

    public boolean UpdateOrderStatus(int orderNo, Enum orderStatus) {
        logger.entry();
        System.out.println("DAL:UpdateOrderStatus:OrderNo: " + orderNo + ", Status: " + orderStatus.toString());
        try {
            if (orderStatus != OrderStatus.All) {
                int ret;
                CreateStmnt();
                SQLStatement = "update orderinfo set status = " + orderStatus.ordinal() + " where orderNo = " + orderNo + ";";
                ret = s.executeUpdate(SQLStatement);
                if (ret > 0) {
                    System.out.println("update of orderstatus is successful");
                }
            } else {
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
}
