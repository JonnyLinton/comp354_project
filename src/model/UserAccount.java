package model;

public class UserAccount {
    private String email;
    private String password;

    private LimitedSizeStockQueue recentlyViewedStocks;

    public UserAccount() {
        email = null;
        password = null;
        recentlyViewedStocks = new LimitedSizeStockQueue();
    }

    public UserAccount(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.recentlyViewedStocks = new LimitedSizeStockQueue();
    }

    public UserAccount(String email, String password, LimitedSizeStockQueue recentlyViewedStocks) {
        this.email = email;
        this.password = password;
        this.recentlyViewedStocks = recentlyViewedStocks;
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

    public LimitedSizeStockQueue getRecentlyViewedStocks() {
        return recentlyViewedStocks;
    }

    public void setRecentlyViewedStocks(LimitedSizeStockQueue recentlyViewedStocks) {
        this.recentlyViewedStocks = recentlyViewedStocks;
    }
}