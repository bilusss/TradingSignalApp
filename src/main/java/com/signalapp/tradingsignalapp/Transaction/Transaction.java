package com.signalapp.tradingsignalapp.Transaction;


public class Transaction {
    public Integer id;
    public String title;
    public Integer userId;
    public Integer cryptoIdBought;
    public Integer cryptoIdSold;
    public Double amountBought;
    public Double amountSold;
    public Double price;
    public String description;

    public Transaction() {
    }
    public Transaction( Integer id, String title, Integer userId, Integer cryptoIdBought, Integer cryptoIdSold, Double amountBought, Double amountSold/*, LocalDateTime completedAt*/, Double price, String description) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.cryptoIdBought = cryptoIdBought;
        this.cryptoIdSold = cryptoIdSold;
        this.amountBought = amountBought;
        this.amountSold = amountSold;
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
    public Integer getCryptoIdBought() { return cryptoIdBought; }
    public void setCryptoIdBought(Integer cryptoIdBought) { this.cryptoIdBought = cryptoIdBought; }
    public Integer getCryptoIdSold() { return cryptoIdSold; }
    public void setCryptoIdSold(Integer cryptoIdSold) { this.cryptoIdSold = cryptoIdSold; }
    public Double getAmountBought() { return amountBought; }
    public void setAmountBought(Double amountBought) { this.amountBought = amountBought; }
    public Double getAmountSold() { return amountSold; }
    public void setAmountSold(Double amountSold) { this.amountSold = amountSold; }
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
                "price:" + price +
                "description:" + description +"}";
    }

}