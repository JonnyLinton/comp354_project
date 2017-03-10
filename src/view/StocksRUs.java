package view;


import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserAccount;

import java.io.IOException;

public class StocksRUs extends Application {
    private UserAccount currentUser;

    public static void main(String args[]) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("StocksRUs");
//        LoginView loginView = new LoginView();
//        LoginController loginController = new LoginController(primaryStage, loginView);
//        primaryStage.setScene(loginView.getScene());
//        MainView mainView = new MainView();
//        primaryStage.setScene(mainView.getScene());
        Parent loginView = null;
        try {
            loginView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginScene = new Scene(loginView, 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}
