package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import view.LoginView;
import view.MainView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class LoginController {
    private Stage primaryStage;
    private LoginView loginView;

    public LoginController(Stage primaryStage, LoginView loginView) {
        this.primaryStage = primaryStage;
        this.loginView = loginView;

        loginView.getLoginButton().setOnAction(this::login);
        loginView.getRegisterButton().setOnAction(this::registerUser);
    }


    private boolean userInfoValid(String email, String password) {
        String[] accountInfo = retrieveUserInfo(email);

        if(accountInfo == null) {
            return false;
        }
        else if(accountInfo[0].equals(email) && accountInfo[1].equals(password)) {
            return true;
        }

        return false;
    }

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

    public boolean login(ActionEvent loginButtonPressed) {
//        if (loginButtonPressed.isConsumed()) {}
        String email = loginView.getEmailTexField().getText();
        String password = loginView.getPasswordTextField().getText();

        if (!userInfoValid(email, password)) { // if user info is not valid, display error.
            displayError("Provided information is invalid.");
            return false;
        }

        navigateToMain();

        return true;
    }

    public boolean registerUser(ActionEvent registerButtonPressed) {
        String email = loginView.getEmailTexField().getText();
        String password = loginView.getPasswordTextField().getText();

        // verify email not already in accounts
        if (retrieveUserInfo(email) != null) {
            displayError("The email " +email +" is already used!");
            return false;
        }
        // user email DNE, create the account in the file.
        String userInfo = email +":" +password +"\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/accounts.txt", true))) {
            bw.append(userInfo);
        } catch (Exception e) {
            displayError(e.getMessage());
            return false;
        }

        if (!userInfoValid(email, password)) { // if user info is not valid, display error.
            displayError("Provided information is invalid.");
        }

        navigateToMain();

        return true;
    }

    private void navigateToMain() {
        primaryStage.setScene(new MainView().getScene());
    }

    private void displayError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error occurred! Details below.");
        alert.setContentText(content);

        alert.showAndWait();
    }
}