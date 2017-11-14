package easyServer;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;

import easyBasic.Logger;

public class ServerListGoogleSheets implements ServerListInterface {

	EasyGoogleSheetsHandler handler;

	private static final String masterServerListSpreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";

	private ServerInterface connectedServer;
	
	public ServerListGoogleSheets(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

	        public void run() {
	            Logger.println("noooo i wanna live !");
	            unregisterServer();
	        }
	    }));
	}

	@Override
	public String getServiceName() {
		return "Google";
	}

	@Override
	public boolean connectToMasterServer() {
		Logger.println("Connection To Master Server");
		handler = new EasyGoogleSheetsHandler(masterServerListSpreadSheetId);
		Logger.println("EasyGoogleSheetsHandler set up succesfully: " + handler.validSetup());
		return handler.validSetup();
	}

	@Override
	public boolean isConnectionToMasterServerEstablished() {
		if (handler == null)
			return false;
		return handler.validSetup();
	}

	@Override
	public boolean connectToMasterServerIfNotConnected() {
		if (isConnectionToMasterServerEstablished()) {
			return true;
		} else {
			return connectToMasterServer();
		}
	}

	@Override
	public int getAmountServers() {
		connectToMasterServerIfNotConnected();
		return Integer.parseInt(handler.readCell("D2"));
	}

	@Override
	public Runnable createRunnable(final RUNFUNCTION func) {

		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case CONNECTTOMASTERSERVER : connectToMasterServer(); break;
				case DISCONNECTFROMMASTERSERVER : disconnectFromMasterServer(); break;
				case REGISTERSERVER:
					registerServer();
					break;
				case UNREGISTERSERVER:
					unregisterServer();
					break;
				}
			}
		};

		return aRunnable;

	}

	@Override
	public List<ServerInterface> getServers() {
		connectToMasterServerIfNotConnected();
		List<ServerInterface> servers = new ArrayList<ServerInterface>();

		List<List<Object>> serversObjectList = handler.readCells("A6:E");
		
		if(serversObjectList==null) return servers;
		for (List<Object> serverObject : serversObjectList) {
			ServerInformation server = parseGoogleSheetServerObjectListToServer(serverObject);
			if (server != null)
				servers.add(server);
		}

		return servers;
	}

	private ServerInformation parseGoogleSheetServerObjectListToServer(List<Object> serverRow) {
		if (serverRow.size() >= 5) {
			String rowNumber = "" + serverRow.get(0);
			String displayName = "" + serverRow.get(1);
			String ip = "" + serverRow.get(2);
			String updateTime = "" + serverRow.get(3);
			String owner = "" + serverRow.get(4);

			return new ServerInformation(rowNumber, displayName, ip, updateTime, owner);
		}
		return null;
	}

	private String[] parseServerToGoogleSheetServerObjectList(ServerInterface server) {
		return new String[] { server.getUniqueID(), server.getDisplayName(), server.getIP(), server.getUpdateTime(),
				server.getOwner() };
	}

	@Override
	public void registerServer() {
		connectToMasterServerIfNotConnected();
		String displayName = ServerHelpers.getHostname();
		String ip = ServerHelpers.getOwnIP();
		String updateTime = ServerHelpers.getTimeNow();
		String owner = ServerHelpers.getUsername();
		ServerInterface server = new ServerInformation(null, displayName, ip, updateTime, owner);
		registerServer(server);
	}

	@Override
	public ServerInterface registerServer(ServerInterface server) {
		connectToMasterServerIfNotConnected();

//		EasyUpdateAction updateAction = handler.append(parseServerToGoogleSheetServerObjectList(server));
		EasyUpdateAction updateAction = handler.addInRandomRowData(parseServerToGoogleSheetServerObjectList(server));
		if (updateAction == null)
			return null;
		BatchUpdateValuesResponse response = updateAction.getResponse();
		if (response == null)
			return null;
		server.setUniqueID(updateAction.getRow());
		this.connectedServer = server;
		return this.connectedServer;
	}

	@Override
	public void disconnectFromMasterServer() {
		handler = null;
	}
	
	@Override
	public boolean unregisterServer() {
		if (isConnectedToAServer()) {
			return unregisterServer(this.connectedServer);
		}

		return true;
	}

	@Override
	public boolean unregisterServer(ServerInterface server) {
		connectToMasterServerIfNotConnected();

		BatchUpdateValuesResponse response = handler.writeIntoRow("B" + server.getUniqueID(), new String[] { "", "", "", "" });
		
		if(response!=null){
			this.connectedServer = null;
		}
		return response!=null;
	}

	@Override
	public boolean isConnectedToAServer() {
		return this.connectedServer != null;
	}

	@Override
	public boolean connectTo(ServerInterface server) {
		this.connectedServer = server;
		Logger.println("Connecting to: "+server.getIP());
		return server!=null;
	}

}