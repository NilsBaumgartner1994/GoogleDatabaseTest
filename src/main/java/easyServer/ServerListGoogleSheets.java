package easyServer;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;

import easyBasic.Logger;

public class ServerListGoogleSheets implements ServerListInterface {

	EasyGoogleSheetsHandler handler;

	private static final String masterServerListSpreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";

	private ServerInterface connectedServer;

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

	public static enum RUNFUNCTION {
		REGISTERSERVER, UNREGISTERSERVER
	};

	@Override
	public Runnable createRunnable(final RUNFUNCTION func) {

		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
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

		List<List<Object>> serversObjectList = handler.readCells("A6:D");
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
	public void registerServer(ServerInterface server) {
		connectToMasterServerIfNotConnected();

		EasyUpdateAction updateAction = handler.addInRandomRowData(parseServerToGoogleSheetServerObjectList(server));
		if (updateAction == null)
			return;
		BatchUpdateValuesResponse response = updateAction.getResponse();
		if (response == null)
			return;
		server.setUniqueID(updateAction.getRow());
		this.connectedServer = server;
		return;
	}

	@Override
	public boolean unregisterServer() {
		if(isConnectedToAServer()) {
			return unregisterServer(this.connectedServer);
		}

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnectFromMasterServer() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean unregisterServer(ServerInterface server) {
		connectToMasterServerIfNotConnected();
		
		handler.writeIntoRow("B"+server.getUniqueID(), new String[]{"","","",""});
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnectedToAServer() {
		return this.connectedServer != null;
	}

}