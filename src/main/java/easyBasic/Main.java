package easyBasic;

import easyFrame.EasyFrameInterface;
import easyFrame.FX.EasyFrameFX;
import easyServer.EasyServerCommunicationReceive;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static EasyServerGUI serverGui;
	public static EasyServerCommunicationReceive receive;
	public static EasyFrameInterface frame;

	public static void main(String[] args) {
		receive = new EasyServerCommunicationReceive();
		launch(args);
	}

	private static final String pathToFiles = "src/main/resources/org/kordamp/bootstrapfx/";

	@Override
	public void start(Stage stage) throws Exception {
		frame = new EasyFrameFX(stage);
		serverGui = new EasyServerGUI(receive, frame);
	}

}