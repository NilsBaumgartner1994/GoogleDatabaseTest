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
		EasyFrame frame2 = new EasyFrame("MasterServerTest2");
		
		
		
		serverHandler = new ServerListGoogleSheets();
		Logger.println("ServersRegistered: " + serverHandler.getAmountServers());
		Logger.println("Register now Server");
		serverHandler.registerServer();
		serverHandler.getServers();
	}

	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Logger.print("Hallo");
		}
	};
}