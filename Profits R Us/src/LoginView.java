import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView
{
	
    private TextField emailTextField;
    private PasswordField passwordTextField;
    private Scene welcomeScene;
    private Button loginButton;
    private Button registerButton;

	public LoginView()
	{
		//CREATE LOGIN SCENE
		BorderPane welcomeBorderLayout = new BorderPane();
		
		emailTextField = new TextField();
		passwordTextField = new PasswordField();
		loginButton = new Button("Login");
	    registerButton = new Button("Register");
	    
		HBox emailBox = new HBox();
	    HBox passwordBox = new HBox();
	    HBox loginRegisterBox = new HBox();
	    VBox loginVBox = new VBox();
	    
	    Label loginLabel = new Label("Login");
	    Label emailLabel = new Label ("Email:");
	    Label passwordLabel = new Label ("Password:");
		
	    emailBox.getChildren().addAll(emailLabel, emailTextField);
    	emailBox.setAlignment(Pos.CENTER);
    	passwordBox.getChildren().addAll(passwordLabel, passwordTextField);
    	passwordBox.setAlignment(Pos.CENTER);
    	loginRegisterBox.getChildren().addAll(loginButton, registerButton);
		loginRegisterBox.setAlignment(Pos.CENTER);
    	loginVBox.getChildren().addAll(loginLabel, emailBox, passwordBox, loginRegisterBox);
    	loginVBox.setAlignment(Pos.CENTER);
    	
    	welcomeBorderLayout.setCenter(loginVBox);
       	welcomeScene = new Scene(welcomeBorderLayout, 800,600);
	}
	 
	public void addRegisterHandler(EventHandler<ActionEvent> registerHandler)
	{
	   registerButton.setOnAction(registerHandler);
    }
	
	public void addLoginHandler(EventHandler<ActionEvent> loginHandler)
	{
	   loginButton.setOnAction(loginHandler);
	}
	
	public Scene welcomeScene()
	{
	    return welcomeScene;
	}

}
