package Security;


import DataAccess.User;
import java.sql.ResultSet;

/*
*AuthManagerInteface an interface where all actions call for authentication of user requests
*/
public interface AuthManagerInterface {
    //login
    public User login(String username, String pwd);
    //logout
    public void logout(String token);
    //insert record to inventory
    public int insertInventory(String table, String productId, String description, int quantity, float perUnitCost, String token) ;

    //inventory: reduce quantity by 1;
    public int reduceQuantityByOne(String table, String productId,String token);

    //return numebers of items deleted
    public int delete(String table, String productId,String token);

    // select query
    public ResultSet select(String table,String token);
    // select order
    public ResultSet selectOrder(String table, String orderId,String token);
    //select
    public ResultSet select(String table, String productId,String token);

   
    public int createOrderTable(String orderTableName,String token);
    
   // the shipped is always false
    public int insertOrder(String dateTimeStamp, String firstName, 
            String lastName, String customerAddress, String phoneNumber, 
            float fCost, Boolean shipped, String orderTableName,String token);
    
     public int dropOrderTable(String orderTableName,String token);
     public int insertOrder(String orderTableName, String productId, 
             String description, float perUnitCost,String token);
     
     /***********Shippings****************/
     //update "orders" table
     public int setOrderShipped(String orderId, Boolean shipped, String token);
}
