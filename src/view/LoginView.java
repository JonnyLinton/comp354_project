package view;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {

    private Button loginButton;
    private Button registerButton;
    private TextField emailTexField;
    private PasswordField passwordTextField;
    private Scene loginScene;
    private GridPane grid;

    public LoginView() {
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        emailTexField = new TextField();
        emailTexField.setPromptText("john.doe@me.com");
        passwordTextField = new PasswordField();

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(emailTexField, 0, 0, 2, 1);
        grid.add(passwordTextField, 0, 1, 2, 1);
        grid.add(loginButton, 0, 2);
        grid.add(registerButton, 1, 2);

        loginScene = new Scene(grid, 800, 600);

        grid.setGridLinesVisible(true);
    }
    public Scene getScene() {
        return loginScene;
    }

}