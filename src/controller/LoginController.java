package controller;

import model.UserAccount;

import java.io.*;

public class LoginController {
    private UserAccount currentUser; // not sure if we need this attribute...?

    public LoginController() {
        currentUser = new UserAccount();

    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
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

    public boolean login(String email, String password) {
        if (!userInfoValid(email, password)) { // if user info is not valid, display error.
            displayError("Provided information is invalid.");
            return false;
        }

        return true;
    }

    public boolean registerUser(String email, String password) {
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

        return true;
    }

    private void navigateToMain() {

    }

    private void displayError(String s) {
        System.out.println(s); // TODO: temporary work around
    }
}