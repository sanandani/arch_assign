
public interface AuthManagerInterface {
    public UserObject login(String username, String pwd);
    public void logout(String token);
}
