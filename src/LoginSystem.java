/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */
public class LoginSystem {
    private static String currentUserType;
    
    public static boolean login(String username, String password) {
        if("admin".equals(username) && "admin123".equals(password)) {
            currentUserType = "admin";
            return true;
        } else if("staff".equals(username) && "staff123".equals(password)) {
            currentUserType = "staff";
            return true;
        }
        return false;
    }
    
    public static String getCurrentUserType() {
        return currentUserType;
    }
}