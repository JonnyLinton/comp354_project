package model;

public class UserAccount {
    private String email;
    private String password;

    private LimitedSizeQueue<String> recentlyViewedStocks;

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

    public UserAccount(String email, String password, LimitedSizeQueue<String> recentlyViewedStocks) {
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

    public LimitedSizeQueue<String> getRecentlyViewedStocks() {
        return recentlyViewedStocks;
    }

    public void setRecentlyViewedStocks(LimitedSizeQueue<String> recentlyViewedStocks) {
        this.recentlyViewedStocks = recentlyViewedStocks;
    }
}