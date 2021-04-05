package bll;

public class LoginSession {


    private static int userId;
    private static String userName;
    private static boolean isLoggedIn = false;

    public static void setUserId(int userId) {
        LoginSession.userId = userId;
    }

    public static void setUserName(String userName) {
        LoginSession.userName = userName;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        LoginSession.isLoggedIn = isLoggedIn;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }



}
