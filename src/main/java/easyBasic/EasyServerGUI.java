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