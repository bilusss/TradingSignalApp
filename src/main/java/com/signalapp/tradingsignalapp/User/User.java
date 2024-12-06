package com.signalapp.tradingsignalapp.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class User {
    private Integer id;
    private String username;
    private String hash;
    // Amount of money user has
    private Double credit;


//    public User(Integer id, String username, String hash, Double credit) {
//        this.id = id;
//        this.username = username;
//        this.hash = hash;
//        this.credit = credit;
//    }
    // TODO: find a way to make the constructor overload work - 'No primary or single unique constructor found'
    // Constructor overloaded with a default credit
//    public User(Integer id, String username, String hash) {
//        this.id = id;
//        this.username = username;
//        this.hash = hash;
//        this.credit = 0.0;
//    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        // Strength - number of bits hash algorithm uses
        // Preferably > 10 , we do use 6 for saving time and resources :)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6);
        this.hash = encoder.encode(password);
    }
    public boolean checkPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6);
        return encoder.matches(password, this.hash);
    }
    public String getHash() {
        return this.hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public Double getCredit() {
        return this.credit;
    }
    public void setCredit(Double credit) {
        this.credit = credit;
    }
    public void addCredit(Float money) {
        this.credit += money;
    }
    public void subCredit(Float money) {
        this.credit -= money;
    }
    public String toString() {
        return String.format("User[id=%d,username=%s,credit=%f]", this.id, this.username, this.credit);
    }

}
