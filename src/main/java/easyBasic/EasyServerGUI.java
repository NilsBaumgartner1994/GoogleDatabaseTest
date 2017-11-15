package easyBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easyFrame.EasyFrame;
import easyFrame.EasyFrameButton;
import easyFrame.EasyFrameInterface;
import easyFrame.EasyProgressStatus;
import easyServer.EasyServerCommunication;
import easyServer.ServerInterface;
import easyServer.ServerListGoogleSheets;
import easyServer.ServerListInterface;

public class EasyServerGUI {

	public ServerListInterface serverHandler;
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
	
	public EasyServerGUI() {
		status = new EasyProgressStatus("SettingupGUI");
		frame = new EasyFrame("MasterServerTest");

		serverHandler = new ServerListGoogleSheets();
		createButtons();
		Logger.println("ServersRegistered: " + serverHandler.getAmountServers());

		frame.addButton(connectToMasterServerButton);
	}
	
	private void createButtons() {
		connectToMasterServer = createRunnable(RUNFUNCTION.CONNECTTOMASTERSERVER);
		connectToMasterServerButton = new EasyFrameButton("Connect",connectToMasterServer);
		
		registerServer = createRunnable(RUNFUNCTION.REGISTERSERVER);
		registerServerButton = new EasyFrameButton("Register",registerServer);
		
		unregisterServer = createRunnable(RUNFUNCTION.UNREGISTERSERVER);
		unregisterServerButton = new EasyFrameButton("Unregister",unregisterServer);
		
		listAllServer = createRunnable(RUNFUNCTION.LISTALLSERVER);
		listAllServerButton = new EasyFrameButton("Get all Server",listAllServer);
	}

	static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER, LISTALLSERVER, CONNECTTOSERVER
	}

	public Runnable createRunnable(final RUNFUNCTION func) {
		return createRunnable(func,null);
	}
	
	EasyServerCommunication connection;
	
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
					connection = serverHandler.registerServer();
					if(connection!=null) {
						frame.addButton(unregisterServerButton);
						frame.removeButton(listAllServerButton);
						removeAllConnectToServerButtons();
						frame.removeButton(registerServerButton);
						
					}
					break;
				case UNREGISTERSERVER:
					serverHandler.unregisterServer();
					if(!serverHandler.isConnectedToAServer()) {
						frame.addButton(registerServerButton);
						frame.addButton(listAllServerButton);
						frame.removeButton(unregisterServerButton);
					}
					break;
				case LISTALLSERVER:
					removeAllConnectToServerButtons();
					List<ServerInterface> servers = serverHandler.getServers();
					addAllConnectToServerButtons(servers);
					break;
				case CONNECTTOSERVER:
					if(param instanceof ServerInterface){
						ServerInterface server = (ServerInterface) param;
						connection = serverHandler.connectTo(server);
						if(connection!=null){
							
						}
					}
					break;
				}
			}
		};

		return aRunnable;
	}
	
	private void removeAllConnectToServerButtons(){
		for(EasyFrameButton button : connectToServerButtons){
			frame.removeButton(button);
		}
		connectToServerButtons = new ArrayList<EasyFrameButton>();
	}
	
	private void addAllConnectToServerButtons(List<ServerInterface> servers){
		for(ServerInterface server : servers){
			String label = server.getUniqueID()+" IP: "+server.getIP();
			Runnable connectToServer = createRunnable(RUNFUNCTION.CONNECTTOSERVER,server);
			EasyFrameButton serverButton = new EasyFrameButton(label,connectToServer);
			connectToServerButtons.add(serverButton);
			frame.addButton(serverButton);
		}
	}

}