package model;

/**
 * Model for a User Account for the inStock system
 */
public class UserAccount {
    private String email;
    private String password;

    private LimitedSizeStockQueue recentlyViewedStocks; // the 5 most recently viewed Stocks

    /**
     * Default constructor for a UserAccount.
     * Sets email and password to null, and initializes
     * recentlyViewedStocks with a new LimitedSizeStockQueue object.
     */
    public UserAccount() {
        email = null;
        password = null;
        recentlyViewedStocks = new LimitedSizeStockQueue();
    }

    /**
     * Constructor for a UserAccount.
     * Sets email and password to the given values, and initializes
     * recentlyViewedStocks with a new LimitedSizeStockQueue object.
     *
     * @param email - email of the User
     * @param password - password for the User
     */
    public UserAccount(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.recentlyViewedStocks = new LimitedSizeStockQueue();
    }

    /**
     * Constructor for a UserAccount.
     * Sets email, password and recentlyViewedStocks
     * to the given values.
     *
     * @param email - email of the User
     * @param password - password for the User
     * @param recentlyViewedStocks - LimitedSizeStockQueue
     */
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