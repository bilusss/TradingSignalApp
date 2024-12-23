package com.signalapp.tradingsignalapp.Transaction;

import java.time.LocalDateTime;

public class Transaction {
    public Integer id;
    public String title;
    public Integer userId;
    public String cryptoIdBought;
    public String cryptoIdSold;
    public Double amountBought;
    public Double amountSold;
    public LocalDateTime completed_at;
    public Double price;
    public String description;

    public Transaction( Integer id, String title, Integer userId, String cryptoIdBought, String cryptoIdSold, Double amountBought, Double amountSold, LocalDateTime completed_at, Double price, String description) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.cryptoIdBought = cryptoIdBought;
        this.cryptoIdSold = cryptoIdSold;
        this.amountBought = amountBought;
        this.amountSold = amountSold;
        this.completed_at = completed_at;
        this.price = price;
        this.description = description;
    }

    //Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getCryptoIdBought() { return cryptoIdBought; }
    public void setCryptoIdBought(String cryptoIdBought) { this.cryptoIdBought = cryptoIdBought; }
    public String getCryptoIdSold() { return cryptoIdSold; }
    public void setCryptoIdSold(String cryptoIdSold) { this.cryptoIdSold = cryptoIdSold; }
    public Double getAmountBought() { return amountBought; }
    public void setAmountBought(Double amountBought) { this.amountBought = amountBought; }
    public Double getAmountSold() { return amountSold; }
    public void setAmountSold(Double amountSold) { this.amountSold = amountSold; }
    public LocalDateTime getCompleted_at() { return completed_at; }
    public void setCompleted_at(LocalDateTime completed_at) { this.completed_at = completed_at; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id:" + id +
                "title:" + title +
                "userId:" + userId +
                "cryptoIdBought:" + cryptoIdBought +
                "cryptoIdSold:" + cryptoIdSold +
                "amountBought:" + amountBought +
                "amountSold:" + amountSold +
                "completed_at:" + completed_at +
                "price:" + price +
                "description:" + description +"}";
    }

}