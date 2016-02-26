/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Penny
 */
public class UserObject {
    private Enum userType;
    private String token;

    /**
     * @return the userType
     */
    public Enum getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(Enum userType) {
        this.userType = userType;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
    
}
