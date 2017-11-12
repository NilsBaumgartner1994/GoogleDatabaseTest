package easyServer;
import java.util.ArrayList;
import java.util.List;

import easyBasic.Logger;

public class ServerListGoogleSheets implements ServerListInterface{

	EasyGoogleSheetsHandler handler;
	
	private static final String masterServerListSpreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";
	
	public ServerListGoogleSheets() {
		Logger.println("Connection To Master Server");
		connectToMasterServer();
	}
	
	@Override
	public boolean connectToMasterServer() {
		Logger.println("Setting up EasyGoogleSheetsHandler");
		handler = new EasyGoogleSheetsHandler(masterServerListSpreadSheetId);
		Logger.println("EasyGoogleSheetsHandler set up succesfully: "+handler.validSetup());
		return handler.validSetup();
	}
	
	@Override
	public boolean isConnectionToMasterServerEstablished() {
		return handler.validSetup();
	}
	
	@Override
	public int getAmountServers() {
		return Integer.parseInt(handler.readCell("D2"));
	}
	
	public static enum RUNFUNCTION {
		REGISTERSERVER
	};

	@Override
	public Runnable createRunnable(final RUNFUNCTION func) {

		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case REGISTERSERVER: registerServer(); break;
				}
			}
		};

		return aRunnable;

	}

	@Override
	public List<ServerInterface> getServers() {
		List<ServerInterface> servers = new ArrayList<ServerInterface>();
		
		List<List<Object>> serversObjectList = handler.readCells("A6:D");
		for(List<Object> serverObject : serversObjectList ) {
			ServerInformation server = parseGoogleSheetServerObjectListToServer(serverObject);
			if(server!=null) servers.add(server);
		}
		
		return servers;
	}
	
	private ServerInformation parseGoogleSheetServerObjectListToServer(List<Object> serverRow) {
		if(serverRow.size()>=4) {
			return new ServerInformation(""+serverRow.get(0),""+serverRow.get(1),Long.parseLong((String)serverRow.get(2)),""+serverRow.get(3));
		}
		return null;
	}

	@Override
	public boolean registerServer() {
		String displayName = ServerHelpers.getHostname();
		String ip = ServerHelpers.getOwnIP();
		String timeUpdate = ServerHelpers.getTimeNow();
		String owner = ServerHelpers.getUsername();
		return handler.appendRow(displayName,ip,timeUpdate,owner);
	}

	@Override
	public boolean unregisterServer() {
		// TODO Auto-generated method stub
		return false;
	}




}