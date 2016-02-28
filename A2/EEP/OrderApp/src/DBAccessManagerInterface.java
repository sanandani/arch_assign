
import java.sql.ResultSet;

public interface DBAccessManagerInterface {

    public UserType login(String username, String pwd);

    //TODO: keep log in db?
    /**
     * ******EEP Inventor*****
     */
    //insert trees, shrubs,seeds
    public int insertInventory(String table, String productId, String description, int quantity, float perUnitCost);

    //inventory: reduce quantity by 1;
    public int reduceQuantityByOne(String table, String productId);

    //return numbers of items deleted; -1 if nothing was deleted
    public int delete(String table, String productId);

    public ResultSet select(String table);

    public ResultSet select(String table, String productId);

    /**
     * ******EEP Order*****
     */
    
    /**
     * ******insertOrder*****
     * @param dateTimeStamp
     * @param firstName
     * @param lastName
     * @param customerAddress
     * @param phoneNumber
     * @param fCost
     * @param shipped
     * @param orderTableName
     * @return 

     */
    //TODO:create order table in below format:
    /*"CREATE TABLE " + orderTableName +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );"  */
    public int createOrderTable(String orderTableName);
    
   // the shipped is always false
    public int insertOrder(String dateTimeStamp, String firstName, 
            String lastName, String customerAddress, String phoneNumber, 
            float fCost, Boolean shipped, String orderTableName);
    
     public int dropOrderTable(String orderTableName);
     public int insertOrder(String orderTableName, String productId, 
             String description, float perUnitCost);
     
     /***********Shippings****************/
     //update "orders" table
     public int setOrderShipped(String orderId, Boolean shipped);
     
}
