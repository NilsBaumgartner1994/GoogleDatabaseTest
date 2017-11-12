package easyBasic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import easyFrame.EasyFrame;
import easyFrame.EasyFrameInterface;
import easyFrame.EasyProgressStatus;
import easyServer.ServerListGoogleSheets;
import easyServer.ServerListInterface;

public class EasyServerGUI {

	public ServerListInterface serverHandler;
	public EasyFrameInterface frame;
	private EasyProgressStatus status; 

	public EasyServerGUI() {
		status = new EasyProgressStatus("SettingupGUI");
		frame = new EasyFrame("MasterServerTest");
		
		serverHandler = new ServerListGoogleSheets();
		Logger.println("ServersRegistered: " + serverHandler.getAmountServers());
		
		Runnable registerServer = serverHandler.createRunnable(ServerListGoogleSheets.RUNFUNCTION.REGISTERSERVER);		
		frame.addButton("Register", registerServer);
		
	}
	
}