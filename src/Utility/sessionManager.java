/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author ridho.naibaho
 */
public class sessionManager {
    private static String loggedInUserId = null;
    private static String loggedInUserName = null;

    public static void setUserLoggedIn(String userId) {
        loggedInUserId = userId;
    }

    public static String getLoggedInUserId() {
        return loggedInUserId;
    }
    
    public static void setNameUserLoggedIn(String username) {
        loggedInUserName = username; 
    }
    
    public static String getLoggedInUserName() {
        return loggedInUserName;
    }

    public static boolean isLoggedIn() {
        return loggedInUserId != null;
    }

    public static void logoutUser() {
        loggedInUserId = null;
    }
}