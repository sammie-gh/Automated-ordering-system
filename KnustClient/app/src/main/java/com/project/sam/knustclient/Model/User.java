package com.project.sam.knustclient.Model;

/**
 * Created by A.Richard on 02/09/2017.
// */
//
//public class User {
//
//    private String tablenumber;
//
//
//    public User() {
//
//    }
//
//    public User(String tablenumber) {
//        this.tablenumber = tablenumber;
//    }
//
//    public String getTablenumber() {
//        return tablenumber;
//    }
//
//    public void setTablenumber(String tablenumber) {
//        this.tablenumber = tablenumber;
//    }
//}

public class User {

    private String Name;
    private String Password;
    private String tableNumber;
    private String IsStaff;


    public User() {

    }


    public User(String name, String password  ) {
        Name = name;
        Password = password;
        IsStaff = "false";

    }


    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
