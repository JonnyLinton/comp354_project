package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.UserAccount;

import java.io.IOException;

public class StocksRUs extends Application {
    private static UserAccount currentUser;

    public static void main(String args[]) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("In Stock");
        Parent loginView = null;
        try {
            loginView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginScene = new Scene(loginView, 1280, 720);
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static boolean setCurrentUser(String email, String password) {
        currentUser = new UserAccount(email, password);

        return currentUser != null;
    }

    public static boolean setCurrentUser(String email, String password, String favoriteStocks[]) {
    	currentUser = new UserAccount(email, password, favoriteStocks);
    	
    	return currentUser != null;
    }
}
