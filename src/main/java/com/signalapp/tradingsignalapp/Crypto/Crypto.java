package com.signalapp.tradingsignalapp.Crypto;


public class Crypto {
    private Integer id;
    private String name;
    private String symbol;
    private String description;
    private String logoUrl;

    public Crypto() {}
    public Crypto(String name, String symbol, String description, String logoUrl) {
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLogoUrl(){
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl){
        this.logoUrl = logoUrl;
    }
}