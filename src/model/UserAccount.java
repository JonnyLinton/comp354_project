package model;

public class UserAccount {
    private String email;
    private String password;

    private LimitedSizeQueue<Stock> recentlyViewedStocks;

    public UserAccount() {
        email = null;
        password = null;
        recentlyViewedStocks = new LimitedSizeQueue<>();
    }

    public UserAccount(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.recentlyViewedStocks = new LimitedSizeQueue<>();
    }

    public UserAccount(String email, String password, LimitedSizeQueue<Stock> recentlyViewedStocks) {
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

    public LimitedSizeQueue<Stock> getRecentlyViewedStocks() {
        return recentlyViewedStocks;
    }

    public void setRecentlyViewedStocks(LimitedSizeQueue<Stock> recentlyViewedStocks) {
        this.recentlyViewedStocks = recentlyViewedStocks;
    }
}