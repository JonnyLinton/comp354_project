package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {

	// Instantiating attributes
    private Button loginButton;
    private Button registerButton;
    private TextField emailTexField;
    private PasswordField passwordTextField;
    private Scene loginScene;
    private GridPane grid;

    /**
     * Creates a LoginView oject with all of its components
     */
    
    public LoginView() {
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        emailTexField = new TextField();
        emailTexField.setPromptText("john.doe@me.com");
        passwordTextField = new PasswordField();

        // Create grid pane with 2 columns and 3 rows
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

        //grid.setGridLinesVisible(true);
    }
    
    /**
     * Get the scene for the LoginView
     * @return loginScene - Scene object
     */
    
    public Scene getScene() {
        return loginScene;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }


    public TextField getEmailTexField() {
        return emailTexField;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }
}