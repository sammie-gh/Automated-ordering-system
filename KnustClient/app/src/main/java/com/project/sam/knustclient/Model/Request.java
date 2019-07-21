package com.project.sam.knustclient.Model;

import java.util.List;


//public class    Request {
//        private String tablenumber;//was tableNumber
//        private String total;
//        private String status;
//
//        private List<Order> foods;  // list of food order
//
//        public Request() {
//        }
//
//        public Request(String table, String total, List<Order> foods) {
//
//            this.tablenumber = table;
//            this.total = total;
//            this.foods = foods;
//            this.status = "0";  // Default is 0, 0:Placed , 1:Shipping , 2: shipped
//        }
//
//
//    public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getTablenumber() {
//            return tablenumber;
//        }
//
//        public void setTablenumber(String tablenumber) {
//            this.tablenumber = tablenumber;
//        }
//
//
//    public String getTotal() {
//            return total;
//        }
//
//        public void setTotal(String total) {
//            this.total = total;
//        }
//
//        public List<Order> getFoods() {
//            return foods;
//        }
//
//        public void setFoods(List<Order> foods) {
//            this.foods = foods;
//        }
//    }



public class Request {
    private String tableNumber;
    private String name;
    private String address;
    private String total;
    private String status;

    private List<Order> foods;  // list of food order

    public Request() {
    }

    public Request(String tableNo, String name, String address, String total, List<Order> foods) {

        this.tableNumber = tableNo;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0";  // Default is 0, 0:Placed , 1:Shipping , 2: shipped
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
