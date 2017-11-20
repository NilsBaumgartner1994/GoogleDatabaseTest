package easyFrameServerGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import easyBasic.Logger;
import easyFrame.EasyFrameSwing;
import easyFrame.EasyFrameButton;
import easyFrame.EasyFrameInterface;
import easyFrame.EasyProgressStatus;
import easyServer.EasyRunnableParameters;
import easyServer.EasyServerCommunicationReceive;
import easyServer.EasyServerCommunicationSend;
import easyServer.EasyServerInformationInterface;
import easyServer.EasyServerListGoogleSheet;
import easyServer.EasyServerListInterface;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EasyServerGUI {

	public EasyServerListInterface serverHandler;
	private Runnable connectToMasterServer;
	private EasyFrameFXButtonBootstrap connectToMasterServerButton;

	private Runnable listAllServer;
	private EasyFrameFXButtonBootstrap listAllServerButton;

	private Runnable registerServer;
	private EasyFrameFXButtonBootstrap registerServerButton;

	private Runnable unregisterServer;
	private EasyFrameFXButtonBootstrap unregisterServerButton;

	private List<EasyFrameFXButtonBootstrap> connectToServerButtons = new ArrayList<EasyFrameFXButtonBootstrap>();

	private EasyServerCommunicationReceive receive;
	private EasyServerCommunicationSend connection;
	
	public Stage stage;
	public Scene scene;


	public EasyServerGUI(EasyServerCommunicationReceive receive, Stage stage) {
		this.receive = receive;
		new EasyProgressStatus("SettingupGUI");
		this.stage = stage;
		
		scene = createServerGUIScene();
		scene.getStylesheets().addAll("bootstrapfx.css", "org/kordamp/bootstrapfx/sampler.css",
				"org/kordamp/bootstrapfx/xml-highlighting.css");
		
		addAllCallbackToDisplay(this.receive);
		serverHandler = new EasyServerListGoogleSheet(this.receive); //after addAllCallback
	}
	
	public void show() {
		this.stage.setScene(scene);
		this.stage.show();
	}
	
	public Scene createServerGUIScene() {
		BorderPane border = new BorderPane();
		border.setRight(createRightSide());
		return new Scene(border);
	}
	
	private Node createRightSide() {
		FlowPane flow = new FlowPane();
		
		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setPrefWrapLength(70); // preferred width allows for two columns
		flow.setStyle("-fx-background-color: DAE6F3;");
		
		flow.getChildren().add(createDebugPanel());
		flow.getChildren().add(createChatPanel());

		return flow;
	}
	
	TextArea debugTextArea;
	private Node createDebugPanel() {
		Panel debugPanel = new Panel("DebugLog");
        debugPanel.getStyleClass().add("panel-primary"); 
		
		debugTextArea = new TextArea();
		debugTextArea.setMaxWidth(170);
		debugTextArea.setDisable(true);
		
		debugPanel.setBody(debugTextArea);
		return debugPanel;
	}
	
	private Node createChatPanel() {
		Panel debugPanel = new Panel("Chatbox");
        debugPanel.getStyleClass().add("panel-primary"); 
		
		TextArea debugTextArea = new TextArea();
		debugTextArea.setMaxWidth(170);
		debugTextArea.setDisable(true);
		
		debugPanel.setBody(debugTextArea);
		return debugPanel;
	}
	
	private void addAllCallbackToDisplay(EasyServerCommunicationReceive receive) {
		for(EasyServerCommunicationReceive.TYPE typ : EasyServerCommunicationReceive.TYPE.values()) {
			addCallbackToDisplay(typ, receive);
		}
	}
	
	private void addCallbackToDisplay(EasyServerCommunicationReceive.TYPE typ, EasyServerCommunicationReceive receive) {
		receive.setCallbackRunnable(typ, createRunnableReceiveMessage());
	}
	
	private EasyRunnableParameters createRunnableReceiveMessage() {
		EasyRunnableParameters<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			Object param;
			EasyServerCommunicationReceive.TYPE type;
			
			public void run() {
				if(param!=null && param instanceof String) {
					debugTextArea.appendText((String) param+"\n");
				}
			}

			@Override
			public void setType(EasyServerCommunicationReceive.TYPE type) {
				this.type = type;
			}

			@Override
			public void setParam(Object param) {
				this.param = param;
			}
		};

		return aRunnable;
	}

	private void createButtons() {
		connectToMasterServer = createRunnable(RUNFUNCTION.CONNECTTOMASTERSERVER);
		connectToMasterServerButton = new EasyFrameFXButtonBootstrap("Connect", connectToMasterServer);

		registerServer = createRunnable(RUNFUNCTION.REGISTERSERVER);
		registerServerButton = new EasyFrameFXButtonBootstrap("Register", registerServer);

		unregisterServer = createRunnable(RUNFUNCTION.UNREGISTERSERVER);
		unregisterServerButton = new EasyFrameFXButtonBootstrap("Unregister", unregisterServer);

		listAllServer = createRunnable(RUNFUNCTION.LISTALLSERVER);
		listAllServerButton = new EasyFrameFXButtonBootstrap("Get all Server", listAllServer);
	}

	static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER, LISTALLSERVER, CONNECTTOSERVER
	}

	public Runnable createRunnable(final RUNFUNCTION func) {
		return createRunnable(func, null);
	}

	public Runnable createRunnable(final RUNFUNCTION func, final Object param) {
		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case CONNECTTOMASTERSERVER:
					if (serverHandler.connectToMasterServer()) {
						frame.addButton(registerServerButton);
						frame.addButton(listAllServerButton);
						frame.removeButton(connectToMasterServerButton);
					}
					break;
				case REGISTERSERVER:
					connection = serverHandler.registerServer(receive);
					if (connection != null) {
						// receive.setEasyServerCommunicationSend(connection);
						frame.addButton(unregisterServerButton);
						frame.removeButton(listAllServerButton);
						removeAllConnectToServerButtons();
						frame.removeButton(registerServerButton);
					}
					break;
				case UNREGISTERSERVER:
					serverHandler.unregisterServer();
					if (!serverHandler.isConnectedToAServer()) {
						frame.addButton(registerServerButton);
						frame.addButton(listAllServerButton);
						frame.removeButton(unregisterServerButton);
					}
					break;
				case LISTALLSERVER:
					removeAllConnectToServerButtons();
					List<EasyServerInformationInterface> servers = serverHandler.getServers();
					addAllConnectToServerButtons(servers);
					break;
				case CONNECTTOSERVER:
					if (param instanceof EasyServerInformationInterface) {
						EasyServerInformationInterface server = (EasyServerInformationInterface) param;
						connection = serverHandler.connectTo(server);
						if (connection != null) {
							// receive.setEasyServerCommunicationSend(connection);
						}
					}
					break;
				}
			}
		};

		return aRunnable;
	}

	private void removeAllConnectToServerButtons() {
		for (EasyFrameFXButtonBootstrap button : connectToServerButtons) {
			frame.removeButton(button);
		}
		connectToServerButtons = new ArrayList<EasyFrameFXButtonBootstrap>();
	}

	private void addAllConnectToServerButtons(List<EasyServerInformationInterface> servers) {
		for (EasyServerInformationInterface server : servers) {
			String label = server.getUniqueID() + " IP: " + server.getIP();
			Runnable connectToServer = createRunnable(RUNFUNCTION.CONNECTTOSERVER, server);
			EasyFrameButton serverButton = new EasyFrameButton(label, connectToServer);
			connectToServerButtons.add(serverButton);
			frame.addButton(serverButton);
		}
	}

}