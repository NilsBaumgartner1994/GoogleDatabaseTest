package easyBasic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.FindReplaceRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;

import easyFrame.EasyFrame;
import easyFrame.EasyFrameButton;
import easyFrame.EasyFrameInterface;
import easyFrame.EasyProgressStatus;
import easyServer.ServerListGoogleSheets;
import easyServer.ServerListInterface;

public class EasyServerGUI {

	public ServerListInterface serverHandler;
	public EasyFrameInterface frame;
	private EasyProgressStatus status;

	private Runnable connectToMasterServer;
	private EasyFrameButton connectToMasterServerButton;
	
	private Runnable disconnectToMasterServer;
	private EasyFrameButton disconnectToMasterServerButton;
	
	private Runnable registerServer;
	private EasyFrameButton registerServerButton;
	
	private Runnable unregisterServer;
	private EasyFrameButton unregisterServerButton;
	
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
		
		disconnectToMasterServer = createRunnable(RUNFUNCTION.DISCONNECTFROMMASTERSERVER);
		disconnectToMasterServerButton = new EasyFrameButton("Disconnect",disconnectToMasterServer);
		
		registerServer = createRunnable(RUNFUNCTION.REGISTERSERVER);
		registerServerButton = new EasyFrameButton("Register",registerServer);
		
		unregisterServer = createRunnable(RUNFUNCTION.UNREGISTERSERVER);
		unregisterServerButton = new EasyFrameButton("Unregister",unregisterServer);
	}

	static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER
	}

	public Runnable createRunnable(final RUNFUNCTION func) {
		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case CONNECTTOMASTERSERVER:
					if (serverHandler.connectToMasterServer()) {
						Logger.println("Change view buttons to register");
						frame.addButton(registerServerButton);
						frame.removeButton(connectToMasterServerButton);
					} 
					break;
				case REGISTERSERVER:
					serverHandler.registerServer();
					if(serverHandler.isConnectedToAServer()) {
						frame.addButton(unregisterServerButton);
						frame.removeButton(registerServerButton);
					}
					break;
				case UNREGISTERSERVER:
					serverHandler.unregisterServer();
					if(!serverHandler.isConnectedToAServer()) {
						frame.addButton(registerServerButton);
						frame.removeButton(unregisterServerButton);
					}
					break;
				}
			}
		};

		return aRunnable;
	}

}