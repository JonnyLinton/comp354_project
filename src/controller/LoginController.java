package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.LimitedSizeStockQueue;
import model.Stock;
import view.StocksRUs;

import java.io.*;

/**
 * Receives information from the LoginView, and relays it to the database. Controls the navigation to the MainView.
 */
public class LoginController {

    /**
     * The Email Text Field from the LoginView
     */
    @FXML
    TextField emailTextField;
    /**
     * The Password Text Field from the LoginView
     */
    @FXML
    PasswordField passwordTextField;

    /**
     * Checks if the given email exists, and verifies that the password
     * matches the one in the database.
     *
     * @param email - user email to check
     * @param password - user password entered
     * @return True if valid, False if not
     */
    private boolean userInfoValid(String email, String password) {
        String[] accountInfo = retrieveUserInfo(email);

        if(accountInfo == null)
            return false;
        else if(accountInfo[0].equals(email) && accountInfo[1].equals(password))
            return true;

        return false;
    }

    /**
     * @param email - the email of the user
     * @return A two element array containing the user's email ([0]) and password ([1]) in the database
     */
    private String[] retrieveUserInfo(String email) {
        String[] accountInfo;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/accounts.txt"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                accountInfo = line.split(":");
                if (accountInfo[0].equals(email)) { // if the account exists, return the info
                    return accountInfo;
                }
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }

        return null;
    }

    /**
     * Gets the text from the email and password fields in the LoginView.
     * If this info is valid, it navigates to the MainView, else displays an error.
     *
     * @param loginButtonPressed - the ActionEvent from the login button.
     */
    @FXML
    public void login(ActionEvent loginButtonPressed) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if (!userInfoValid(email, password)) // if user info is not valid, display error.
        {
            displayError("Provided information is invalid.");
        }
        else // if no error, login the user.
        {
            // check if the user has any recently viewed Stocks, set the attribute
            LimitedSizeStockQueue recentlyViewedStocks = initializeRecentlyViewedStocks(email);
            StocksRUs.setCurrentUser(email, password, recentlyViewedStocks);
            navigateToMain(loginButtonPressed);
        }
    }

    private LimitedSizeStockQueue initializeRecentlyViewedStocks(String email) {
        String[] recentlyViewedStockInfo;
        LimitedSizeStockQueue recentlyViewedStocks = new LimitedSizeStockQueue();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/recentlyViewedStocks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                recentlyViewedStockInfo = line.split("::");
                if (recentlyViewedStockInfo[0].equals(email)) {
                    // found a match
                    String[] stockNameTickerPairs = recentlyViewedStockInfo[1].split(":");
                    for (String stockNameTickerPair : stockNameTickerPairs) {
                        String[] stockNameTickerPairInfo = stockNameTickerPair.split(",");
                        recentlyViewedStocks.add(new Stock(stockNameTickerPairInfo[0], stockNameTickerPairInfo[1])); // will fetch the Stock data in the Stock constructor
                    }
                }
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
        return recentlyViewedStocks;
    }


    /**
     * Gets the text from the email and password fields in the LoginView.
     * Checks if the email is already used in the system, and displays an error if it is.
     * For new emails, it writes to the database, and logs in the user.
     *
     * @param registerButtonPressed - the ActionEvent from the register button
     */
    @FXML
    public void register(ActionEvent registerButtonPressed) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // verify email not already in accounts
        if (retrieveUserInfo(email) != null) {
            displayError("The email " +email +" is already used!");
        }
        else {
            // user email DNE, create the account in the file.
            String userInfo = email + ":" + password + "\n";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/accounts.txt", true))) {
                bw.append(userInfo);
            } catch (Exception e) {
                displayError(e.getMessage());
            }

            //credentials are now saved, so automatically login the user
            login(registerButtonPressed);
        }
    }

    /**
     * Loads an instance of the MainView, sets the size, and displays it to the user.
     *
     * @param event - ActionEvent passed to it from the login() method
     */
    private void navigateToMain(ActionEvent event) {
        Parent mainView = null;
        try {
            mainView = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainScene = new Scene(mainView, 1280, 720);
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Displays an Alert to the user containing the specified content.
     *
     * @param content - string that will be displayed
     */
    private void displayError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error occurred! Details below.");
        alert.setContentText(content);

        alert.showAndWait();
    }
}