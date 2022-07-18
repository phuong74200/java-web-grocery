/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.order;

import java.sql.Date;

/**
 *
 * @author phuon
 */
public class OrderDTO {

    private int orderID;
    private Date orderDate;
    private String userID;
    private float total;

    public OrderDTO() {
        this.orderID = -1;
        this.orderDate = null;
        this.userID = "";
        this.total = 0;
    }

    public OrderDTO(int orderID, Date orderDate, String userID, float total) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.userID = userID;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
