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

import java.io.*;

public class LoginController {

    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordTextField;

    private boolean userInfoValid(String email, String password) {
        String[] accountInfo = retrieveUserInfo(email);

        if(accountInfo == null)
            return false;
        else if(accountInfo[0].equals(email) && accountInfo[1].equals(password))
            return true;

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
    // This is a test

    @FXML
    public void login(ActionEvent loginButtonPressed) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if (!userInfoValid(email, password)) // if user info is not valid, display error.
            displayError("Provided information is invalid.");
        else // if no error, login the user.
            navigateToMain(loginButtonPressed);
    }

    @FXML
    public void register(ActionEvent registerButtonPressed) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // verify email not already in accounts
        if (retrieveUserInfo(email) != null) {
            displayError("The email " +email +" is already used!");
        }
        // user email DNE, create the account in the file.
        String userInfo = email +":" +password +"\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/accounts.txt", true))) {
            bw.append(userInfo);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
        
        //credentials are now saved, so automatically login the user
        login(registerButtonPressed);
    }

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

    private void displayError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error occurred! Details below.");
        alert.setContentText(content);

        alert.showAndWait();
    }
}