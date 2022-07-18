/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Date;

/**
 *
 * @author phuon
 */
public class ProductDTO {

    private String productID;
    private String productName;
    private String image;
    private float price;
    private int quantity;
    private int categoryID;
    private Date importDate;
    private int usingDate;

    public ProductDTO() {
        this.productID = "";
        this.productName = "";
        this.image = "";
        this.price = 0;
        this.quantity = 0;
        this.categoryID = 0;
        this.importDate = null;
        this.usingDate = 0;
    }

    public ProductDTO(String productID, String productName, String image, float price, int quantity,
            int categoryID, Date importDate, int usingDate) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.importDate = importDate;
        this.usingDate = usingDate;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public int getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(int usingDate) {
        this.usingDate = usingDate;
    }

}
