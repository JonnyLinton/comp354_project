package view;

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

    public LoginView() {
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        emailTexField = new TextField();
        emailTexField.setPromptText("john.doe@me.com");
        passwordTextField = new PasswordField();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(emailTexField, 0, 0, 2, 1);
        gridPane.add(passwordTextField, 0, 1, 2, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(registerButton, 1, 2);

        loginScene = new Scene(gridPane, 800, 600);

        gridPane.setGridLinesVisible(true);
    }
    public Scene getLoginScene() {
        return loginScene;
    }

}