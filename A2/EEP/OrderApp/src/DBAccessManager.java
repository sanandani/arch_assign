
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubham
 */
public class DBAccessManager implements DBAccessManagerInterface {
    public Connection getConnection () {
        Connection DBConn = null;           // MySQL connection handle
        String sourceURL = "localhost";            //TODO hard code this
        try
        {
            DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
        } catch (Exception e) {
            return DBConn;
        } // end try-catch
        return DBConn;
    }
    
    public UserType login(String username, String pwd) {
        Connection conn = getConnection();       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        String str = null;
        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "Select * from users where username = '"+username+"' and password = '"+pwd+"'" );
                
                if (res.next()){
                    str = (String) res.getObject(4);//user type
                    if(str.equalsIgnoreCase("IT"))
                        return UserType.IT;
                    else if(str.equalsIgnoreCase("SHIPPING"))
                        return UserType.SHIPPING;
                    else if(str.equalsIgnoreCase("ORDER"))
                        return UserType.ORDER;
                }
            } catch (Exception e) {
                return null;
            } // end try-catch
        } // if connect check
        return null;
    }
    
    public int insertInventory(String table, String productId, String description, int quantity, float perUnitCost) {
        
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO " + table +
                        " (product_id, description, quantity, item_price) " +
                        "VALUES ( '" + productId + "', " + "'" +
                        description + "', " + "'" +
                        quantity + "', " + perUnitCost + " );");

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;    
    }

    public int reduceQuantityByOne(String table, String productId) {
        
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ("UPDATE "+table+" set quantity=(quantity-1) where product_code = '" + productId + "';");

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }

    public int delete(String table, String productId) {
        Connection conn = getConnection();       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "DELETE FROM "+table+" WHERE productid = '"+productId+"'");
                return res.getFetchSize();
                
            } catch (Exception e) {
                return -1;
                //Print error 

            } // end try-catch
        } // if connect check
        return -1;
    }

    public ResultSet select(String table) {
        Connection conn = getConnection();       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "Select * from "+table );
                return res;
                
            } catch (Exception e) {
                return null;
                //Print error 

            } // end try-catch
        } // if connect check
        return null;
    }

    public ResultSet select(String table, String productId) {
        Connection conn = getConnection();       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "Select * from "+table+" where productid= '"+productId+"'" );
                return res;
                
            } catch (Exception e) {
                return null;
                //Print error 

            } // end try-catch
        } // if connect check
        return null;
    }

    public int createOrderTable(String orderTableName) {
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "CREATE TABLE " + orderTableName +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );");

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }

    public int insertOrder(String dateTimeStamp, String firstName, String lastName, String customerAddress, String phoneNumber, float fCost, Boolean shipped, String orderTableName) {
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO orders (order_date, " + "first_name, " +
                        "last_name, address, phone, total_cost, shipped, " +
                        "ordertable) VALUES ( '" + dateTimeStamp + "', " +
                        "'" + firstName + "', " + "'" + lastName + "', " +
                        "'" + customerAddress + "', " + "'" + phoneNumber + "', " +
                        fCost + ", " + false + ", '" + orderTableName +"' );");

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }

    public int dropOrderTable(String orderTableName) {
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "DROP TABLE " + orderTableName + ";" );

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }

    public int insertOrder(String orderTableName, String productId, String description, float perUnitCost) {
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO " + orderTableName +
                        " (product_id, description, item_price) " +
                        "VALUES ( '" + productId + "', " + "'" +
                        description + "', " + perUnitCost + " );");

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }

    public int setOrderShipped(String orderId, Boolean shipped) {
        
        Connection conn = getConnection();       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ("UPDATE orders set shipped=false where order_id = '" + orderId + "';");
        
        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                return s.executeUpdate(SQLstatement);
            } catch (Exception e) {
                return 0;
                //Print error 

            } // end try-catch
        } // if connect check
        return 0;
    }
}
