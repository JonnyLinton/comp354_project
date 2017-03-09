package view;


import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.UserAccount;

public class StocksRUs extends Application {
    private UserAccount currentUser;

    public static void main(String args[]) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("StocksRUs");
//        LoginView loginView = new LoginView();
//        LoginController loginController = new LoginController(primaryStage);
//        primaryStage.setScene(loginView.getScene());
        MainView mainView = new MainView();
        primaryStage.setScene(mainView.getScene());
        primaryStage.show();
    }
}
