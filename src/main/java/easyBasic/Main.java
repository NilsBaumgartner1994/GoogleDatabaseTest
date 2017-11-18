package easyBasic;

import easyFrame.EasyFrameInterface;
import easyFrame.FX.EasyFrameFX;
import easyServer.EasyServerCommunicationReceive;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

import org.kordamp.bootstrapfx.scene.layout.Panel;

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
		
		File f = new File(pathToFiles+"sampler.fxml");

		Logger.println("Start: "+f.exists());
		URL location = f.toURI().toURL();
		Logger.println(location.toString());
		FXMLLoader fxmlLoader = new FXMLLoader(location);
		TabPane tabPane = fxmlLoader.load();

		tabPane.getTabs().add(new DemoTab("Buttons", pathToFiles+"buttons.fxml"));
		tabPane.getTabs().add(new DemoTab("Labels", pathToFiles+"labels.fxml"));
		tabPane.getTabs().add(new DemoTab("Alerts", pathToFiles+"alerts.fxml"));
		tabPane.getTabs().add(new DemoTab("Panels", pathToFiles+"panels.fxml"));
		tabPane.getTabs().add(new DemoTab("Headings", pathToFiles+"text.fxml"));
		tabPane.getTabs().add(new DemoTab("Text ", pathToFiles+"text2.fxml"));
		tabPane.getTabs().add(new DemoTab("Paragraph ", pathToFiles+"paragraph.fxml"));
		tabPane.getTabs().add(new DemoTab("Button Groups ", pathToFiles+"button_groups.fxml"));
		tabPane.getTabs().add(new DemoTab("SplitMenuButtons", pathToFiles+"split_menu_buttons.fxml"));

		Scene scene = new Scene(tabPane);
		scene.getStylesheets().addAll("bootstrapfx.css", "org/kordamp/bootstrapfx/sampler.css",
				"org/kordamp/bootstrapfx/xml-highlighting.css");

		stage.setTitle("BootstrapFX Sampler");
		stage.setScene(scene);
		// primaryStage.sizeToScene();
		stage.setWidth(1024);
		stage.show();

		// frame = new EasyFrameFX(stage);
		// serverGui = new EasyServerGUI(receive,frame);
		// TODO Auto-generated method stub

		// stage.setTitle("Hello World!");
		// Button btn = new Button();
		// btn.setText("Say 'Hello World'");
		// btn.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// System.out.println("Hello World!");
		// }
		// });
		//
		//
		//
		// StackPane root = new StackPane();
		// root.getChildren().add(btn);
		// stage.setScene(new Scene(root, 300, 250));
		// ResponsiveHandler.addResponsiveToWindow(stage);
		// stage.show();

	}

	private static class DemoTab extends Tab {
		private DemoTab(String title, String location) throws Exception {
			super(title);
			setClosable(false);

			TabPane content = new TabPane();
			setContent(content);
			content.setSide(Side.BOTTOM);

			Tab widgets = new Tab("Widgets");
			widgets.setClosable(false);
			File f = new File(location);
			FXMLLoader fxmlLoader = new FXMLLoader(f.toURI().toURL());
			Node node = fxmlLoader.load();
			widgets.setContent(node);

			content.getTabs().addAll(widgets);
		}
	}

}