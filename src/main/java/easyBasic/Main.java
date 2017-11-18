package easyBasic;

import java.util.Arrays;
import java.util.List;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.responsive.ResponsiveHandler;

import easyFrame.EasyFrameInterface;
import easyFrame.EasyFrameSwing;
import easyFrame.FX.EasyFrameFX;
import easyServer.EasyServerCommunicationReceive;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

	public static EasyServerGUI serverGui;
	public static EasyServerCommunicationReceive receive;
	public static EasyFrameInterface frame;

	public static void main(String[] args) {
		receive = new EasyServerCommunicationReceive();
//		frame = new EasyFrameSwing("MasterServerTest");
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		frame = new EasyFrameFX(stage);
		serverGui = new EasyServerGUI(receive,frame);
		// TODO Auto-generated method stub
		
//		stage.setTitle("Hello World!");
//		Button btn = new Button();
//		btn.setText("Say 'Hello World'");
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
//
//		
//		
//		StackPane root = new StackPane();
//		root.getChildren().add(btn);
//		stage.setScene(new Scene(root, 300, 250));
//		ResponsiveHandler.addResponsiveToWindow(stage);
//		stage.show();
		
		
		
		
		

	}



}