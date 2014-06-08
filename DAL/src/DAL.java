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
import java.math.*;
import java.util.ArrayList;
import java.util.List;

public class DAL {
    Connection DBConn = null;           // MySQL connection handle    
    Statement s = null;                 // SQL statement pointer
    ResultSet res = null;               // SQL query result set pointer
    String SQLStatement;                // SQL query
    String errString = null;            // String for displaying errors

    public boolean Initialize(String serverIPAddress, String userName, String pwd) {
        String SQLServerIP = serverIPAddress;
        String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/spartan";
        try {
            //load JDBC driver class for MySQL
            Class.forName("com.mysql.jdbc.Driver");

            //create a connection to the db - note the default account is "remote"
            //and the password is "remote_pass" - you will have to set this
            //account up in your database
            //DBConn = DriverManager.getConnection(sourceURL, "remote", "remote_pass");
            DBConn = DriverManager.getConnection(sourceURL, userName, pwd);
        } catch (Exception e) {
            errString = "\nProblem connecting to spartan database:: " + e;
            return false;
        } // end try-catch
        return true;
    }

    public boolean Uninitialize() {
        try {
            if (DBConn != null) {
                DBConn.close();
            }
        } catch (Exception e) {
            errString = "\nProblem disconnecting to spartan database:: " + e;
            return false;
        } // end try-catch
        return true;
    }

    public ArrayList<Customer> GetCustomers() {
        ArrayList<Customer> custList = null;
        try {
            s = DBConn.createStatement();
            SQLStatement = "select * from customer";
            ResultSet rs = s.executeQuery(SQLStatement);

            custList = FillCustomerList(rs);

            rs.close();
        } catch (Exception e) {
            errString = "\nProblem getting customers:: " + e;
            return null;
        } // end try-catch
        finally {
            //s.close();
        }
        return custList;
    }

    private ArrayList<Customer> FillCustomerList(ResultSet rs) throws SQLException {
        ArrayList<Customer> custList = new ArrayList<>();
        // Get the information from the database. Display the
        // first and last name, address, phone number, address, and
        // order date. Same the ordertable name - this is the name of
        // the table that is created when an order is submitted that
        // contains the list of order items.
        while (rs.next()) {
            Customer c = new Customer();
            c.phone = rs.getString(1);
            c.fname = rs.getString(2);
            c.lname = rs.getString(3);
            c.address = rs.getString(4);
            custList.add(c);
        } // for each element in the return SQL query
        return custList;
    }

    ///Return OrderNo if success. If failure, return -1
    public int AddOrder(List<OrderDetails> orderList, String fname, String lname, String phone, String address) {
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
                errString = "Exception throw while inserting customer" + e;
            }

            SQLStatement = "insert into orderinfo(phone) values ('" + phone + "');";
            executeUpdateVal = s.executeUpdate(SQLStatement);

            SQLStatement = "(select max(orderNo) from orderinfo)";
            res = s.executeQuery(SQLStatement);
            if (res.next()) {
                orderNo = res.getInt(1); //get integer from the first column of the result
            }
            //SQLStatement = "insert into orderdetails values (" + orderNo +"," + widgetId + "," + quant + ");";
            String stmnt = "insert into orderdetails values " ;
            int i = 0;
            int count = orderList.size();
            for(OrderDetails od: orderList)
            {
                i++;
                String str = "(";
                str += orderNo + "," + od.widgetId + "," + od.quantity;
                if(i == count)
                    str += ");";
                else
                    str += "),";
                stmnt += str;
            }
            
            executeUpdateVal = s.executeUpdate(stmnt);
        } catch (Exception e) {
            errString = "\nProblem adding order:: " + e;
            return -1;
        } // end try-catch
        finally {
            // s.close();
        }
        return orderNo;
    }

    ///Return OrderNo if success. If failure, return -1
    public int AddWidget(String widgetName, String widgetDesc, int quant, int stationId) {
        try {
            int executeUpdateVal;           // Return value from execute indicating effected rows
            s = DBConn.createStatement();
            SQLStatement = "insert into widget (name, description, quantity, stationId) values ('" + widgetName + "','" + widgetDesc + "'," + quant + "," + stationId + ");";
            executeUpdateVal = s.executeUpdate(SQLStatement);
        } catch (Exception e) {
            errString = "\nProblem adding widget:: " + e;
            return -1;
        } // end try-catch
        finally {
            // s.close();
        }
        return 0;
    }

    public int IncWidgets(String widgetName, int increment) {
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
            errString = "\nProblem incrementing widget:: " + e;
            return -1;
        } // end try-catch
        finally {
            // s.close();
        }
        return 0;
    }

    public int DecWidgets(String widgetName, int decrement) {
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
            errString = "\nProblem incrementing widget:: " + e;
            return -1;
        } // end try-catch
        finally {
            // s.close();
        }
        return 0;
    }

    private ArrayList<OrderInfo> GetOrders(String sqlStmnt) {
        ArrayList<OrderInfo> orderList = null;
        try {
            s = DBConn.createStatement();

            SQLStatement = sqlStmnt;
            res = s.executeQuery(SQLStatement);

            orderList = new ArrayList<>();
            while (res.next()) {
                OrderInfo oi = new OrderInfo();
                oi.orderNo = res.getInt(1);
                oi.orderTime = res.getString(2);
                oi.shippingTime = res.getString(3);
                oi.orderStatus = res.getInt(4);
                String strPhone = res.getString(5);//phone number
                GetCustomerAndOrderDetails(strPhone, oi);

                orderList.add(oi);
            }
        } catch (Exception e) {
            errString = "\nProblem incrementing widget:: " + e;
            return null;
        } // end try-catch
        finally {
            // s.close();
        }
        return orderList;
    }

    private void GetCustomerAndOrderDetails(String strPhone, OrderInfo oi) {
        try {
            s = DBConn.createStatement();
            ArrayList<Customer> cust = null;
            String stmnt = "select * from customer where phone = '" + strPhone + "';";
            ResultSet rs1 = s.executeQuery(stmnt);
            cust = FillCustomerList(rs1);
            oi.cust = cust.get(0);//will return only 1 customer as we are querying by strPhone which is unique
            rs1.close();
            s.close();
            
            GetOrderDetails(oi);
        } catch (Exception e) {
            errString = "\nProblem " + e;
        } // end try-catch
        finally {
            // s.close();
        }
    }

    private void GetOrderDetails(OrderInfo oi) {
        try {
            s = DBConn.createStatement();
            String stmnt = "select * from orderdetails where orderno = " + oi.orderNo + ";";
            ResultSet rs2 = s.executeQuery(stmnt);
            oi.listOrderDetails = new ArrayList<>();
            while (rs2.next()) {
                OrderDetails od = new OrderDetails();
                od.widgetId = rs2.getInt(2);

                //Get the widget name from the widget id - Start
               s = DBConn.createStatement();
               String stmnt1 = "select name from widget where widgetId = " + od.widgetId + ";";
                ResultSet rs3 = s.executeQuery(stmnt1);
                if (rs3.next())  od.widgetName = rs3.getString(1);
                rs3.close();
                s.close();
                //Get the widget name from the widget id - End

                od.quantity = rs2.getInt(3);
                oi.listOrderDetails.add(od);
            }
            rs2.close();
            s.close();
        } catch (Exception e) {
            errString = "\nProblem " + e;
        } // end try-catch
        finally {
            // s.close();
        }
    }

    public ArrayList<OrderInfo> GetShippedOrders() {
        return GetOrders("select * from orderinfo where status = 3");
    }

    public ArrayList<OrderInfo> GetPendingOrders() {
        return GetOrders("select * from orderinfo where status <> 3");
    }

    public ArrayList<OrderInfo> GetBackorderedOrders() {
        return GetOrders("select * from orderinfo where status = 2"); //2 - Backorder
    }

    public ArrayList<Widget> GetWidgets() {
        ArrayList<Widget> widgetList = null;
        try {
            s = DBConn.createStatement();

            SQLStatement = "select * from widget";
            res = s.executeQuery(SQLStatement);

            widgetList = new ArrayList<>();
            while (res.next()) {
                Widget w = new Widget();
                w.widgetId = res.getInt(1);
                w.name = res.getString(2);
                w.desc = res.getString(3);
                w.quantity = res.getInt(4);
                w.stationId = res.getInt(5);
                widgetList.add(w);
            }
        } catch (Exception e) {
            errString = "\nProblem incrementing widget:: " + e;
            return null;
        } // end try-catch
        finally {
            // s.close();
        }
        return widgetList;
    }
}
