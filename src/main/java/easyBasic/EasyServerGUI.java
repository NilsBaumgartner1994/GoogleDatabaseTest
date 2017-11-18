package easyBasic;

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

public class EasyServerGUI {

	public EasyServerListInterface serverHandler;
	public EasyFrameInterface frame;
	private EasyProgressStatus status;

	private Runnable connectToMasterServer;
	private EasyFrameButton connectToMasterServerButton;

	private Runnable listAllServer;
	private EasyFrameButton listAllServerButton;

	private Runnable registerServer;
	private EasyFrameButton registerServerButton;

	private Runnable unregisterServer;
	private EasyFrameButton unregisterServerButton;

	private List<EasyFrameButton> connectToServerButtons = new ArrayList<EasyFrameButton>();

	private EasyServerCommunicationReceive receive;
	private EasyServerCommunicationSend connection;


	public EasyServerGUI(EasyServerCommunicationReceive receive, EasyFrameInterface frame) {
		this.receive = receive;
		status = new EasyProgressStatus("SettingupGUI");
//		frame = new EasyFrameSwing("MasterServerTest");
		this.frame = frame;

		createTextArea();
		addAllCallbackToDisplay();

		serverHandler = new EasyServerListGoogleSheet(receive);
		createButtons();
		Logger.println("ServersRegistered: " + serverHandler.getAmountServers());

		frame.addButton(connectToMasterServerButton);
	}
	
	JTextArea display;
	
	private void createTextArea() {
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Connection Information"));

		display = new JTextArea(16, 58);
		display.setEditable(false); // set textArea non-editable
		JScrollPane scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Add Textarea in to middle panel
		middlePanel.add(scroll);

		frame.addPanel(middlePanel);
	}
	
	private void addAllCallbackToDisplay() {
		for(EasyServerCommunicationReceive.TYPE typ : EasyServerCommunicationReceive.TYPE.values()) {
			addCallbackToDisplay(typ);
		}
	}
	
	private void addCallbackToDisplay(EasyServerCommunicationReceive.TYPE typ) {
		receive.setCallbackRunnable(typ, createRunnableReceiveMessage());
	}
	
	private EasyRunnableParameters createRunnableReceiveMessage() {
		EasyRunnableParameters<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			Object param;
			EasyServerCommunicationReceive.TYPE type;
			
			public void run() {
				if(param!=null && param instanceof String) {
					display.append((String) param+"\n");
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
		connectToMasterServerButton = new EasyFrameButton("Connect", connectToMasterServer);

		registerServer = createRunnable(RUNFUNCTION.REGISTERSERVER);
		registerServerButton = new EasyFrameButton("Register", registerServer);

		unregisterServer = createRunnable(RUNFUNCTION.UNREGISTERSERVER);
		unregisterServerButton = new EasyFrameButton("Unregister", unregisterServer);

		listAllServer = createRunnable(RUNFUNCTION.LISTALLSERVER);
		listAllServerButton = new EasyFrameButton("Get all Server", listAllServer);
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
		for (EasyFrameButton button : connectToServerButtons) {
			frame.removeButton(button);
		}
		connectToServerButtons = new ArrayList<EasyFrameButton>();
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