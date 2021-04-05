package gui.models;

import bll.StudRegManager;

public class StudRegModel {

    private StudRegManager studRegManager = new StudRegManager();

    public boolean checkLogin(String userName, String password, String role) {
        return studRegManager.checkLogin(userName, password, role);
    }
}
