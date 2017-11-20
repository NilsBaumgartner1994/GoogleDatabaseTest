package easyBasic;

import easyFrame.EasyFrameInterface;
import easyFrameServerGUI.EasyFrameFX;
import easyFrameServerGUI.EasyServerGUI;
import easyServer.EasyServerCommunicationReceive;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static EasyServerGUI serverGui;
	public static EasyServerCommunicationReceive receive;

	public static void main(String[] args) {
		receive = new EasyServerCommunicationReceive();
		launch(args);
	}

	private static final String pathToFiles = "src/main/resources/org/kordamp/bootstrapfx/";

	@Override
	public void start(Stage stage) throws Exception {
		serverGui = new EasyServerGUI(receive, stage);
		serverGui.show();
	}
	
	@Override
	public void stop(){
	    System.out.println("Stage is closing");
	    // Save file
	}

}