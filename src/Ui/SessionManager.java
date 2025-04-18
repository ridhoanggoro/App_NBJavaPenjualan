/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

/**
 *
 * @author ridho.naibaho
 */
class SessionManager {
    private static String loggedInUserId = null;

    public static void setUserLoggedIn(String userId) {
        loggedInUserId = userId;
    }

    public static String getLoggedInUserId() {
        return loggedInUserId;
    }

    public static boolean isLoggedIn() {
        return loggedInUserId != null;
    }

    public static void logoutUser() {
        loggedInUserId = null;
    }
}