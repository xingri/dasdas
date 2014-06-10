package com.lge.spartan.dal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vijay.rachabattuni
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//public class MySQLDALImpl implements IDAL  {
public class MySQLDALImpl implements DAL {

    // Define a static logger variable so that it references the
// Logger instance named "MyApp".
static final Logger logger = LogManager.getLogger(MySQLDALImpl.class.getName());


    Connection DBConn = null;           // MySQL connection handle    
    Statement s = null;                 // SQL statement pointer
    ResultSet res = null;               // SQL query result set pointer
    String SQLStatement;                // SQL query

    public boolean Initialize(String serverIPAddress, String userName, String pwd) {
         logger.entry();
         boolean b = logger.isDebugEnabled();
        String SQLServerIP = serverIPAddress;
        String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/spartan";
        try {
            //load JDBC driver class for MySQL
            Class.forName("com.mysql.jdbc.Driver");

            //create a connection to the db 
            DBConn = DriverManager.getConnection(sourceURL, userName, pwd);
        } catch (Exception e) {
            logger.error("Exception " + e);
            return false;
        } // end try-catch
        return logger.exit(true);
    }

    public boolean Uninitialize() {
        logger.entry();
        try {
            if (DBConn != null) {
                DBConn.close();
            }
        } catch (Exception e) {
           logger.error("Exception " + e);
            return logger.exit(false);
        } // end try-catch
        return logger.exit(true);
    }

    public ArrayList<Customer> GetCustomers() {
        logger.entry();
        ArrayList<Customer> custList = null;
        ResultSet rs = null;
        try {
            s = DBConn.createStatement();
            SQLStatement = "select * from customer";
            rs = s.executeQuery(SQLStatement);

            custList = FillCustomerList(rs);
        } catch (Exception e) {
            logger.error("Exception " + e);
            CleanUp(rs, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(rs, s);
        }
        return custList;
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

    ///Return OrderNo if success. If failure, return -1
    public int AddOrder(List<OrderDetails> orderList, String fname, String lname, String phone, String address) {
         logger.entry();
        int orderNo = 0;
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            s = DBConn.createStatement();

            try {
                //If the customer phone is already existing; this insert will fail. 
                //Ok no problem.. continue with the other insertions....
                SQLStatement = "insert into customer values ('" + phone + "','" + fname + "','" + lname + "','" + address + "');";
                executeUpdateVal = s.executeUpdate(SQLStatement);
            } catch (Exception e) {
                 logger.error("Exception " + e);
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
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return orderNo;
    }

    ///Return OrderNo if success. If failure, return -1
    public int AddWidget(String widgetName, String widgetDesc, int quant, int stationId) {
         logger.entry();
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            s = DBConn.createStatement();
            SQLStatement = "insert into widget (name, description, quantity, stationId) values ('" + widgetName + "','" + widgetDesc + "'," + quant + "," + stationId + ");";
            executeUpdateVal = s.executeUpdate(SQLStatement);
        } catch (Exception e) {
             logger.error("Exception " + e);
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
        int quant = 0;
        try {
            s = DBConn.createStatement();

            int executeUpdateVal;           // Return value from execute indicating effected rows
            SQLStatement = "select quantity from widget where name = '" + widgetName + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quant = res.getInt(1);
            }

            SQLStatement = "update widget set quantity = " + (quant + increment) + ";";
            executeUpdateVal = s.executeUpdate(SQLStatement);
        } catch (Exception e) {
             logger.error("Exception " + e);
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
        int quant = 0;
        try {
            s = DBConn.createStatement();

            SQLStatement = "select quantity from widget where name = '" + widgetName + "';";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                quant = res.getInt(1);
            }

            int executeUpdateVal;           // Return value from execute indicating effected rows

            SQLStatement = "update widget set quantity = " + (quant - decrement) + ";";
            executeUpdateVal = s.executeUpdate(SQLStatement);
        } catch (Exception e) {
             logger.error("Exception " + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return 0;
    }
    
     public int GetOrderStatus(int orderNo) {
          logger.entry();
           int status = 0;
        try {
            s = DBConn.createStatement();

            SQLStatement = "select status from orderinfo where orderno = " + orderNo +";";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                status = res.getInt(1);
            }
        } catch (Exception e) {
             logger.error("Exception " + e);
            CleanUp(res, s);
            return -1;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return status;
     }

    private ArrayList<OrderInfo> GetOrders(String sqlStmnt) {
         logger.entry();
        ArrayList<OrderInfo> orderList = null;
        try {
            s = DBConn.createStatement();

            SQLStatement = sqlStmnt;
            res = s.executeQuery(SQLStatement);

            orderList = new ArrayList<>();
            while (res.next()) {
                OrderInfo oi = new OrderInfo();
                oi.setOrderNo(res.getInt(1));
                oi.setOrderTime(res.getString(2));
                oi.setShippingTime(res.getString(3));
                oi.setOrderStatus(res.getInt(4));
                String strPhone = res.getString(5);//phone number
                GetCustomerAndOrderDetails(strPhone, oi);

                orderList.add(oi);
            }
        } catch (Exception e) {
             logger.error("Exception " + e);
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
        try {
            s = DBConn.createStatement();
            ArrayList<Customer> cust = null;
            String stmnt = "select * from customer where phone = '" + strPhone + "';";
            ResultSet rs1 = s.executeQuery(stmnt);
            cust = FillCustomerList(rs1);
            oi.setCust(cust.get(0));//will return only 1 customer as we are querying by strPhone which is unique
            rs1.close();
            s.close();

            GetOrderDetails(oi);
        } catch (Exception e) {
             logger.error("Exception " + e);
             //CleanUp(rs1, s);
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
    }

    private void GetOrderDetails(OrderInfo oi) {
         logger.entry();
        try {
            s = DBConn.createStatement();
            String stmnt = "select * from orderdetails where orderno = " + oi.getOrderNo() + ";";
            ResultSet rs2 = s.executeQuery(stmnt);
            oi.setListOrderDetails(new ArrayList<>());
            while (rs2.next()) {
                OrderDetails od = new OrderDetails();
                od.setWidgetId(rs2.getInt(2));

                //Get the widget name from the widget id - Start
                s = DBConn.createStatement();
                String stmnt1 = "select name from widget where widgetId = " + od.getWidgetId() + ";";
                ResultSet rs3 = s.executeQuery(stmnt1);
                if (rs3.next()) {
                    od.setWidgetName(rs3.getString(1));
                }
                rs3.close();
                s.close();
                //Get the widget name from the widget id - End

                od.setQuantity(rs2.getInt(3));
                oi.getListOrderDetails().add(od);
            }
            rs2.close();
            s.close();
        } catch (Exception e) {
             logger.error("Exception " + e);
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
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

    public ArrayList<Widget> GetWidgets() {
         logger.entry();
        ArrayList<Widget> widgetList = null;
        try {
            s = DBConn.createStatement();

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
            CleanUp(res, s);
            return null;
        } // end try-catch
        finally {
            CleanUp(res, s);
        }
        return widgetList;
    }
}
