package com.signalapp.tradingsignalapp.Wallet;

public class Wallet {
    private Integer id;
    private Integer userId;
    private Integer cryptoId;
    private Double amount;

    public Wallet() {
    }

    public Wallet(Integer userId, Integer cryptoId, Double amount) {
        this.userId = userId;
        this.cryptoId = cryptoId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCryptoId() {
        return this.cryptoId;
    }

    public void setCryptoId(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String toString() {
        return String.format("Wallet[id=%d, userId=%d, cryptoId=%d, amount=%f]", this.id, this.userId, this.cryptoId, this.amount);
    }

}
