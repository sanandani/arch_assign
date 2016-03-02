package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DBAccessManager implements DBAccessManagerInterface {
    private final String username = "remote";
    private final String password = "remote_pass";

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
        if(dbName==null) {
            dbName = "orderinfo";
        }
        String sourceURL = "jdbc:mysql://localhost:3306/"+dbName;
        try
        {
            
            DBConn = DriverManager.getConnection(sourceURL, username, password);
        } catch (Exception e) {
            return DBConn;
        } // end try-catch
        return DBConn;
    }
    
    public UserType login(String username, String pwd) {
        Connection conn = getConnection("users");       // Connection
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
        
        Connection conn = getConnection(table);       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ( "INSERT INTO " + table + " "+
                        getParametersInventory(table) +
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
        
        Connection conn = getConnection(table);       // Connection
        Statement s = null;                 // SQL statement pointer
        String SQLstatement = ("UPDATE "+table+" set "+getParameterQuantity(table)+"=("+getParameterQuantity(table)+"-1) where "+getParameterProductID(table)+" = '" + productId + "';");
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
        int res;               // SQL query result set pointer
        Statement s = null;                 // SQL statement pointer

        // If we are connected, then we get the list of "table" from the
        //  database

        if (conn!=null)
        {
            try
            {
                s = conn.createStatement();
                
                res = s.executeUpdate("DELETE FROM "+table+" WHERE "+getParameterProductID(table)+" = '"+productId+"'");
                return res;
                
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
                res = s.executeQuery( "Select * from "+table+" where "+getParameterProductID(table)+"= '"+productId+"'" );
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
        
        //                    SQLstatement = ( "INSERT INTO orders (order_date, " + "first_name, " +
//                        "last_name, address, phone, total_cost, shipped, " +
//                        "ordertable) VALUES ( '" + dateTimeStamp + "', " +
//                        "'" + firstName + "', " + "'" + lastName + "', " +
//                        "'" + customerAddress + "', " + "'" + phoneNumber + "', " +
//                        fCost + ", " + false + ", '" + orderTableName +"' );");
        
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
                        " ("+getParameterProductID(orderTableName)+", description, item_price) " +
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
        String param = attributeMap.get(table);
        if(param == null)
            param = "product_id";
        return param;
    }
    
    public String getParametersInventory (String table) {
        HashMap <String,String> attributeMap = new HashMap <String,String>();
        attributeMap.put("seeds","(product_code, description, quantity, price) ");
        attributeMap.put("shrubs","(product_code, description, quantity, price) ");
        attributeMap.put("trees","(product_code, description, quantity, price) ");
        attributeMap.put("cultureboxes","(productid, productdescription, productquantity, productprice)");
        attributeMap.put("genomics","(productid, productdescription, productquantity, productprice)");
        attributeMap.put("processing","(productid, productdescription, productquantity, productprice)");
        attributeMap.put("referencematerials","(productid, productdescription, productquantity, productprice)");
        String param = attributeMap.get(table);
        if(param == null)
            param = "product_id";
        return param;
    }
    
    public String getParameterQuantity(String table) {
        HashMap <String,String> attributeMap = new HashMap <String,String>();
        attributeMap.put("seeds","quantity");
        attributeMap.put("shrubs","quantity");
        attributeMap.put("trees","quantity");
        attributeMap.put("cultureboxes","productquantity");
        attributeMap.put("genomics","productquantity");
        attributeMap.put("processing","productquantity");
        attributeMap.put("referencematerials","productquantity");
        String param = attributeMap.get(table);
        if(param == null)
            param = "product_id";
        return param;
    }
    
    public ResultSet selectOrder(String table, String orderId) {
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
                res = s.executeQuery( "Select * from "+table+" where order_id= "+orderId);
                return res;
                
            } catch (Exception e) {
                return null;
                //Print error 

            } // end try-catch
        } // if connect check
        return null;
    }
}
