package view;


import javafx.application.Application;
import javafx.stage.Stage;

public class StocksRUs extends Application {

    public static void main(String args[]) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        primaryStage.setScene(loginView.getLoginScene());
        primaryStage.show();
    }
}
