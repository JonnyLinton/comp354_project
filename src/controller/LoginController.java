package controller;

import model.UserAccount;

import java.io.*;

public class LoginController {
    private UserAccount currentUser;

    public LoginController() {
        currentUser = new UserAccount();

    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(UserAccount currentUser) {
        this.currentUser = currentUser;
    }


    private boolean validateUser(String email, String password) {
        boolean userExists = userAccountExists(email);

        return true;
    }

    private boolean userAccountExists(String email) {
        boolean emailExists = false;

        try (InputStream in = new FileInputStream("accounts.txt");
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(":");
                if (accountInfo[0].equals(email))
                    emailExists = true;
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }

        return emailExists;
    }

    public boolean login(String email, String password) {

        return true;
    }

    public boolean registerUser(String email, String password) {
        // verify email not already in accounts
        if (userAccountExists(email)) {
            displayError("This email is already used!");
        }


        return true;
    }

    private void navigateToMain() {

    }

    private void displayError(String s) {

    }
}