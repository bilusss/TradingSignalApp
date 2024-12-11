package com.signalapp.tradingsignalapp.Transaction;

public class Transaction {
    public static Integer id;
    private Integer userId;
    private Integer currencyIdGet;
    private Integer currencyIdPut;
    private Double amountGet;
    private Double amountPut;
    private Double price;
    private String action; // BUY or SELL
    private String description;

    public Transaction(Integer userId, Integer currencyIdGet,Integer currencyIdPut,Double amountGet,Double amountPut,Double price,String action,String description) {
        this.userId = userId;
        this.currencyIdGet = currencyIdGet;
        this.currencyIdPut = currencyIdPut;
        this.amountGet = amountGet;
        this.amountPut = amountPut;
        this.price = price;
        this.action = action;
        this.description = description;
    }
    //Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getCurrencyIdGet() {
        return currencyIdGet;
    }
    public void setCurrencyIdGet(Integer currencyIdGet) {
        this.currencyIdGet = currencyIdGet;
    }
    public Integer getCurrencyIdPut() {
        return currencyIdPut;
    }
    public void setCurrencyIdPut(Integer currencyIdPut) {
        this.currencyIdPut = currencyIdPut;
    }
    public Double getAmountGet() {
        return amountGet;
    }
    public void setAmountGet(Double amountGet) {
        this.amountGet = amountGet;
    }
    public Double getAmountPut() {
        return amountPut;
    }
    public void setAmountPut(Double amountPut) {
        this.amountPut = amountPut;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id:" + id +
                "userId:" + userId +
                "currencyIdGet:" + currencyIdGet +
                "currencyIdPut:" + currencyIdPut +
                "amountGet:" + amountGet +
                "amountPut:" + amountPut +
                "price:" + price +
                "action:" + action +
                "description:" + description +"}";
    }

}