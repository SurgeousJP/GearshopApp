package com.example.gearshop.model;

public class ProductReview {
    private int ProductID, CustomerID;
    private int Rate;
    private String Comment, Report;

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public ProductReview(int productID, int customerID, int rate, String comment, String report) {
        ProductID = productID;
        CustomerID = customerID;
        Rate = rate;
        Comment = comment;
        Report = report;
    }
}
