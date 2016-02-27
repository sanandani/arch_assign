
/**
 *
 * @author Penny
 */
public class AuthManager implements AuthManagerInterface {

    public static UserObject login(String username, String pwd) {
        //login(String username, String pwd)
        //new DBAccessManager();
        return new UserObject(login(String username
        , String pwd
        ), generateToken(username)
    

    );
    }

    public static String generateToken(String username) {
        int hash = 7;
        //this hash is a simulation 
        hash = username.charAt(0) + username.charAt(2);
        return "u_" + hash + "_signed";
    }

    public static boolean isValidToken(String token) {
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
}
