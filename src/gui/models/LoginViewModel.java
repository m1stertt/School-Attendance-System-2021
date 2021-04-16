package gui.models;

import bll.StudRegManager;

public class LoginViewModel {

    private StudRegManager studRegManager = new StudRegManager();

    public boolean checkLogin(String userName, String password, String role) {
        return studRegManager.checkLogin(userName, password, role);
    }
}
