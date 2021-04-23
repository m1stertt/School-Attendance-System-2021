package gui.models;

import bll.StudRegManager;
import dal.StudRegDAO;

public class LoginViewModel {

    private StudRegManager studRegManager = StudRegManager.createStudRegManager();

    public boolean checkLogin(String userName, String password, String role) {
        return studRegManager.checkLogin(userName, password, role);
    }
}
