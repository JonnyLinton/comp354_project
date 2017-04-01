package model;

import java.util.PriorityQueue;

public class UserAccount {
    private String email;
    private String password;
    private PriorityQueue<String> favoriteStocks;

    public UserAccount() {
        email = null;
        password = null;
        favoriteStocks = new PriorityQueue<>();
    }
    
    public UserAccount(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.favoriteStocks = new PriorityQueue<>();
    }
    
    public UserAccount(String email, String password, PriorityQueue favoriteStocks) {
        this.email = email;
        this.password = password;
        this.favoriteStocks = favoriteStocks;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public PriorityQueue<String> getFavoriteStocks() {
    	return favoriteStocks;
    }
    
    public void setFavoriteStocks(PriorityQueue<String> favoriteStocks) {
    	this.favoriteStocks = favoriteStocks;
    }
}