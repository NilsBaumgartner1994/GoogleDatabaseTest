package easyFrameServerGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import easyFrame.EasyProgressStatus;
import easyServer.EasyRunnableParameters;
import easyServer.EasyServerCommunicationReceive;
import easyServer.EasyServerCommunicationSend;
import easyServer.EasyServerInformationInterface;
import easyServer.EasyServerListGoogleSheet;
import easyServer.EasyServerListInterface;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EasyServerGUI {
	
	private static final Logger LOGGER = java.util.logging.Logger.getLogger(EasyServerGUI.class.getName());
	private static FileHandler fh;
	static {
		try {
			fh = new FileHandler("./ServerGUI.log");
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
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
		LOGGER.info("Setting up ServerGUI");
		this.receive = receive;
		new EasyProgressStatus("SettingupGUI");
		this.stage = stage;

		scene = createServerGUIScene(this.receive);
		LOGGER.info("Set Stylesheets to scene");
		scene.getStylesheets().addAll("bootstrapfx.css", "org/kordamp/bootstrapfx/sampler.css",
				"org/kordamp/bootstrapfx/xml-highlighting.css");

		LOGGER.info("Create new ServerHandler");
		serverHandler = new EasyServerListGoogleSheet(this.receive); // after addAllCallback
	}

	public void show() {
		LOGGER.info("Show Scene");
		this.stage.setScene(scene);
		this.stage.show();
	}

	public Scene createServerGUIScene(EasyServerCommunicationReceive receive) {
		BorderPane border = new BorderPane();
		border.setRight(createRightSide(receive));
		border.setCenter(createSenterSide(receive));
		return new Scene(border);
	}

	private Node createSenterSide(EasyServerCommunicationReceive receive) {
		FlowPane flow = new FlowPane();

		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setPrefWrapLength(70); // preferred width allows for two columns
		flow.setStyle("-fx-background-color: DAE6F3;");

		flow.getChildren().add(createControlPanel(receive));
		flow.getChildren().add(createServerListPanel());

		return flow;
	}
	
	Pane serverPane;
	private Node createServerListPanel() {
		Panel debugPanel = new Panel("Servers");
		debugPanel.getStyleClass().add("panel-primary");
		
		serverPane = new FlowPane();
		
		debugPanel.setBody(serverPane);
		
		return debugPanel;
	}
	
	private Node createControlPanel(EasyServerCommunicationReceive receive) {
		Panel debugPanel = new Panel("Control");
		debugPanel.getStyleClass().add("panel-primary");
		
		FlowPane flow = new FlowPane();
		
		connectToMasterServerButton = new EasyFrameFXButtonBootstrap("Connect to Master",createControlButtonRunnable(RUNFUNCTION.CONNECTTOMASTERSERVER, flow));
		listAllServerButton = new EasyFrameFXButtonBootstrap("Get Servers",createControlButtonRunnable(RUNFUNCTION.LISTALLSERVER, flow));
		registerServerButton = new EasyFrameFXButtonBootstrap("Register",createControlButtonRunnable(RUNFUNCTION.REGISTERSERVER, flow));
		unregisterServerButton = new EasyFrameFXButtonBootstrap("Unregister",createControlButtonRunnable(RUNFUNCTION.UNREGISTERSERVER, flow));
		
		flow.getChildren().add(connectToMasterServerButton);
		
		debugPanel.setBody(flow);
		
		return debugPanel;
	}
	
	static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER, LISTALLSERVER, CONNECTTOSERVER
	}

	public Runnable createControlButtonRunnable(final RUNFUNCTION func, final Pane parent) {
		return createControlButtonRunnable(func, null, parent);
	}

	public Runnable createControlButtonRunnable(final RUNFUNCTION func, final Object param, final Pane parent) {
		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case CONNECTTOMASTERSERVER:
					if (serverHandler.connectToMasterServer()) {
						parent.getChildren().add(registerServerButton);
						parent.getChildren().add(listAllServerButton);
						parent.getChildren().remove(connectToMasterServerButton);
					}
					break;
				case REGISTERSERVER:
					connection = serverHandler.registerServer(receive);
					if (connection != null) {
						// receive.setEasyServerCommunicationSend(connection);
						parent.getChildren().add(unregisterServerButton);
						parent.getChildren().remove(listAllServerButton);
						removeAllConnectToServerButtons();
						parent.getChildren().remove(registerServerButton);
					}
					break;
				case UNREGISTERSERVER:
					serverHandler.unregisterServer();
					if (!serverHandler.isConnectedToAServer()) {
						parent.getChildren().add(registerServerButton);
						parent.getChildren().add(listAllServerButton);
						parent.getChildren().remove(unregisterServerButton);
					}
					break;
				case LISTALLSERVER:
					removeAllConnectToServerButtons();
					List<EasyServerInformationInterface> servers = serverHandler.getServers();
					addAllConnectToServerButtons(servers);
					break;
				case CONNECTTOSERVER:
					LOGGER.log(Level.INFO, "Connect to Server");
					if (param instanceof EasyServerInformationInterface) {
						LOGGER.log(Level.INFO, "Param object is a Server", new Object[]{param});
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

	protected void addAllConnectToServerButtons(List<EasyServerInformationInterface> servers) {
		LOGGER.log(Level.INFO, "add All Server Buttons", new Object[]{servers});
		for(EasyServerInformationInterface server : servers) {
			LOGGER.log(Level.INFO, "Add a Server Button", new Object[]{server});
			String ip = server.getIP();
			Runnable runnable = createControlButtonRunnable(RUNFUNCTION.CONNECTTOSERVER,server,serverPane);
			EasyFrameFXButtonBootstrap serverButton = new EasyFrameFXButtonBootstrap(ip,runnable);
			serverPane.getChildren().add(serverButton);
		}
	}

	protected void removeAllConnectToServerButtons() {
		LOGGER.log(Level.INFO, "remove All Server Buttons");
		serverPane.getChildren().removeAll(serverPane.getChildren());
	}

	
	
	
	private int rightSideMaxWidth = 170;

	private Node createRightSide(EasyServerCommunicationReceive receive) {
		FlowPane flow = new FlowPane();

		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setPrefWrapLength(70); // preferred width allows for two columns
		flow.setStyle("-fx-background-color: DAE6F3;");

		flow.getChildren().add(createDebugPanel(receive));
		flow.getChildren().add(createChatPanel(receive));

		return flow;
	}

	TextArea debugTextArea;

	private Node createDebugPanel(EasyServerCommunicationReceive receive) {
		Panel debugPanel = new Panel("DebugLog");
		debugPanel.getStyleClass().add("panel-primary");

		debugTextArea = new TextArea();
		debugTextArea.setMaxWidth(rightSideMaxWidth);
		debugTextArea.setDisable(true);
		
		setDebugReceiveCallback(debugTextArea,receive);

		debugPanel.setBody(debugTextArea);
		return debugPanel;
	}

	private void setDebugReceiveCallback(TextArea debugArea ,EasyServerCommunicationReceive receive) {
		EasyServerCommunicationReceive.TYPE[] types = {EasyServerCommunicationReceive.TYPE.CLOSED,
				EasyServerCommunicationReceive.TYPE.ERROR,
				EasyServerCommunicationReceive.TYPE.ESTABLISHED,
				EasyServerCommunicationReceive.TYPE.LOST,
				EasyServerCommunicationReceive.TYPE.SETTINGUP,
				EasyServerCommunicationReceive.TYPE.WAITING};
		
		for(EasyServerCommunicationReceive.TYPE type : types) {
			setTextAreaReceiveCallback(debugArea,receive,type);
		}
	}

	
	private Node createChatPanel(EasyServerCommunicationReceive receive) {

		Panel chatPanel = new Panel("Chatbox");
		chatPanel.getStyleClass().add("panel-primary");

		TextArea chatReceiveTextArea = new TextArea();
		chatReceiveTextArea.setMaxWidth(rightSideMaxWidth);
		chatReceiveTextArea.setDisable(true);
		setTextAreaReceiveCallback(chatReceiveTextArea, receive, EasyServerCommunicationReceive.TYPE.MESSAGE);
		
		TextField chatInputTextArea = new TextField();
		chatInputTextArea.setMaxWidth(rightSideMaxWidth);
		setChatInputAreaKeyListener(chatInputTextArea,receive);

		chatPanel.setBody(chatReceiveTextArea);
		chatPanel.setFooter(chatInputTextArea);

		return chatPanel;
	}
	
	private void setChatInputAreaKeyListener(TextField inputArea,EasyServerCommunicationReceive receive) {
		inputArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		            String text = inputArea.getText();
		            
		            if(connection != null && connection.isValidSetup()) {
		            	if(connection.sendMessage(text)) {
		            		inputArea.setText("");
		            	}
		            }
		        }
		    }
		});
	}

	private void setTextAreaReceiveCallback(TextArea textArea, EasyServerCommunicationReceive receive,
			EasyServerCommunicationReceive.TYPE type) {
		receive.setCallbackRunnable(type, createRunnableReceiveMessage(textArea));
	}

	private EasyRunnableParameters createRunnableReceiveMessage(TextArea textArea) {
		EasyRunnableParameters<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			Object param;
			EasyServerCommunicationReceive.TYPE type;

			public void run() {
				if (param != null && param instanceof String) {
					textArea.appendText((String) param + "\n");
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

	
}