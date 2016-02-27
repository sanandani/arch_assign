
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Penny
 */
public class AuthManager implements AuthManagerInterface{

    private static DBAccessManager dbm = new DBAccessManager();

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
            if (token.substring(4).equals("_signed")) {
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
}
