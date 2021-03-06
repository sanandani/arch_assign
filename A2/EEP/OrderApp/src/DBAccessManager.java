
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

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
    public Connection getConnection (String table) {
        Connection DBConn = null;           // MySQL connection handle
        HashMap <String,String> tableMap = new HashMap <String,String>();
        tableMap.put("seeds","inventory");
        tableMap.put("shrubs","inventory");
        tableMap.put("trees","inventory");
        tableMap.put("cultureboxes","leaftech");
        tableMap.put("genomics","leaftech");
        tableMap.put("processing","leaftech");
        tableMap.put("referencematerials","leaftech");
        tableMap.put("orders","orderinfo");
        tableMap.put("users","userinfo");
        String dbName = tableMap.get(table);
        String sourceURL = "jdbc:mysql://localhost:3306/"+dbName;
        try
        {
            DBConn = DriverManager.getConnection(sourceURL,"root","");
        } catch (Exception e) {
            return DBConn;
        } // end try-catch
        return DBConn;
    }
    
    public UserType login(String username, String pwd) {
        System.out.println(pwd);
        Connection conn = getConnection("users");       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        String str = null;
        // If we are connected, then we get the list of "table" from the
        //  database
        if(conn==null){
            System.out.println("null");
        }
        if (conn!=null)
        {
            try
            {
                
                s = conn.createStatement();
                res = s.executeQuery( "Select * from users where username = '"+username+"' and password = '"+pwd+"'" );
                System.out.println(res);
                if (res.next()){
                    
                    str = (String) res.getObject(4);//user type
                    System.out.println("str"+str);
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
        
        Connection conn = getConnection(table);       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO " + table +
                        " (product_id, description, quantity, item_price) " +
                        "VALUES ( '" + getParameterProductID(table) + "', " + "'" +
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
        
        Connection conn = getConnection(table);       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ("UPDATE "+table+" set quantity=(quantity-1) where product_code = '" + getParameterProductID(table) + "';");

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
        Connection conn = getConnection(table);       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "DELETE FROM "+table+" WHERE productid = '"+getParameterProductID(table)+"'");
                return res.getFetchSize();
                
            } catch (Exception e) {
                return -1;
                //Print error 

            } // end try-catch
        } // if connect check
        return -1;
    }

    public ResultSet select(String table) {
        Connection conn = getConnection(table);       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        
        // If we are connected, then we get the list of "table" from the
        //  database
        
        if (conn!=null)
        {
            try
            {
                
                s = conn.createStatement();
                System.out.println("ss"+table);
                res = s.executeQuery( "Select * from "+table );
                System.out.println("res"+res);
               while (res.next()) {
                   System.out.println("res ob "+ res.getObject(1));
               }
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
        Connection conn = getConnection(table);       // Connection
        ResultSet res = null;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer
        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                res = s.executeQuery( "Select * from "+table+" where productid= '"+getParameterProductID(table)+"'" );
                return res;
                
            } catch (Exception e) {
                return null;
                //Print error 

            } // end try-catch
        } // if connect check
        return null;
    }

    public int createOrderTable(String orderTableName) {
        Connection conn = getConnection(orderTableName);       // Connection
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
        Connection conn = getConnection("orders");       // Connection
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
        Connection conn = getConnection(orderTableName);       // Connection
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
        Connection conn = getConnection(orderTableName);       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO " + orderTableName +
                        " (product_id, description, item_price) " +
                        "VALUES ( '" + getParameterProductID(orderTableName) + "', " + "'" +
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
        
        Connection conn = getConnection("orders");       // Connection
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
    public String getParameterProductID (String table) {
        HashMap <String,String> attributeMap = new HashMap <String,String>();
        attributeMap.put("seeds","product_code");
        attributeMap.put("shrubs","product_code");
        attributeMap.put("trees","product_code");
        attributeMap.put("cultureboxes","productid");
        attributeMap.put("genomics","productid");
        attributeMap.put("processing","productid");
        attributeMap.put("referencematerials","productid");
        return attributeMap.get(table);
    }

}
