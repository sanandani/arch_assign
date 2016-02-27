
import java.sql.ResultSet;


public interface AuthManagerInterface {
    public UserObject login(String username, String pwd);
    public void logout(String token);
    public boolean insertInventory(String table, String productId, 
            String description, String quantity, String perUnitCost,String token);

    //inventory: reduce quantity by 1;
    public int reduceQuantityByOne(String table, String productId,String token);

    //return numebers of items deleted
    public int delete(String table, String productId,String token);

    public ResultSet select(String table,String token);

    public ResultSet select(String table, String productId,String token);

   
    public boolean createOrderTable(String orderTableName,String token);
    
   // the shipped is always false
    public boolean insertOrder(String dateTimeStamp, String firstName, 
            String lastName, String customerAddress, String phoneNumber, 
            String fCost, Boolean shipped, String orderTableName,String token);
    
     public boolean dropOrderTable(String orderTableName,String token);
     public boolean insertOrder(String orderTableName, String productId, 
             String description, String perUnitCost,String token);
     
     /***********Shippings****************/
     //update "orders" table
     public boolean setOrderShipped(String orderId, Boolean shipped, String token);
}
