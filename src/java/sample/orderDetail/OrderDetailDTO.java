/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.orderDetail;

/**
 *
 * @author phuon
 */
public class OrderDetailDTO {

    private int detailID;
    private float price;
    private int quantity;
    private int orderID;
    private int productID;

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public OrderDetailDTO() {
        this.detailID = -1;
        this.price = -1;
        this.quantity = -1;
        this.orderID = -1;
        this.productID = -1;
    }

    public OrderDetailDTO(int detailID, float price, int quantity, int orderID, int productID) {
        this.detailID = detailID;
        this.price = price;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
    }
}
