
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Penny
 */
public class AuthManager implements AuthManagerInterface{

    private static DBAccessManagerInterface dbm = new DBAccessManager();

    public  UserObject login(String username, String pwd) {
        UserType ut = dbm.login(username, pwd);
        if (ut != null) {
            Logger.log("user " + username + " is logging in to the system at " + getTime());
            return new UserObject(dbm.login(username, pwd), generateToken(username));
        } else {
            return new UserObject(null, null);
        }
    }

    public  void logout(String token) {
        Logger.log("user " + decryptToken(token) + " is logging out the system at " + getTime());

    }

    //simulation assume username has at least 5 characters
    private static String generateToken(String username) {
        //this hash is a simulation 
        return "u_" + username.substring(0, 3) + "_signed" + username.substring(3);
    }

    private static String decryptToken(String token) {

        String username = token.substring(2, 5) + token.substring(12);
        return "u_" + username.substring(0, 3) + "_signed" + username.substring(3);
    }

    private static boolean isValidToken(String token) {
        try {
            if (token.substring(5,12).equals("_signed")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static String getTime() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    public boolean insertInventory(String table, String productId, String description, String quantity, String perUnitCost, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int reduceQuantityByOne(String table, String productId, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int delete(String table, String productId, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet select(String table, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet select(String table, String productId, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int createOrderTable(String orderTableName, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertOrder(String dateTimeStamp, String firstName, String lastName, String customerAddress, String phoneNumber, float fCost, Boolean shipped, String orderTableName, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int dropOrderTable(String orderTableName, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertOrder(String orderTableName, String productId, String description, Float perUnitCost, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean setOrderShipped(String orderId, Boolean shipped, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
