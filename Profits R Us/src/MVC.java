import javafx.application.Application;
import javafx.stage.Stage;

public class MVC extends Application
{
	
	//This is the display of the application
	private Stage primaryStage;

	// MAIN METHOD, the launch(args) runs the start(Stage) method
	public static void main(String[] args)
	{
		launch(args);
	}
	
	//Start method creates objects of loginModel, LoginView, graphModel, graphView and pass them to the controllers with the stage
	//so the controllers can access the models and view and displays information to the stage (switch scenes, handle functions)
	public void start(Stage primaryStage) throws Exception
	{
		LoginModel loginModel = new LoginModel();
		LoginView loginView = new LoginView();
		GraphModel graphModel = new GraphModel();
		GraphView graphView = new GraphView();
		LoginController loginController = new LoginController(loginView, loginModel,graphView, primaryStage);
		GraphController graphController = new GraphController(graphModel, graphView, loginView, primaryStage);
		primaryStage.show();
	}
}
