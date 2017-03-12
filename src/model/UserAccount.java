package model;

public class UserAccount {
    private String email;
    private String password;
    private String favoriteStocks[];

    public UserAccount() {
        email = null;
        password = null;
        favoriteStocks = new String[10];
    }
    
    public UserAccount(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.favoriteStocks = new String[10];
    }
    
    public UserAccount(String email, String password, String favoriteStocks[]) {
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
    
    public String[] getFavoriteStocks() {
    	return favoriteStocks;
    }
    
    public void setFavoriteStocks(String[] favoriteStocks) {
    	this.favoriteStocks = favoriteStocks;
    }
}