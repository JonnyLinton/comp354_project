import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class LoginController
{
	
	private LoginView loginView;
	private LoginModel loginModel;
	private GraphView graphView;
	private Stage stage;
	
	public LoginController(LoginView loginView, LoginModel loginModel, GraphView graphView, Stage stage)
	{
		this.loginView = loginView;
		this.loginModel = loginModel;
		this.graphView = graphView;
		this.stage = stage;
		this.loginView.addLoginHandler(new LoginHandler());		
		this.loginView.addRegisterHandler(new RegisterHandler());
		this.stage.setScene(loginView.welcomeScene());
	}

	public class RegisterHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent arg0) {
		
			//HANDLE REGISTER BUTTON HERE
			
			//Move to graphView
			stage.setScene(graphView.graphScene());
			
		}
	}
	public class LoginHandler implements EventHandler<ActionEvent>
	{
			
		public void handle(ActionEvent arg0) {
		    
			//HANDLE LOGIN BUTTON HERE
			
			//Move to graphView
			stage.setScene(graphView.graphScene());
		}
	}
	

}
