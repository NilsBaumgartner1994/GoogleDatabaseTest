package easyServer;
import java.util.List;

import easyServer.ServerListGoogleSheets.RUNFUNCTION;

public interface ServerListInterface {

	public boolean connectToMasterServer();
	public void disconnectFromMasterServer();
	public boolean connectToMasterServerIfNotConnected();
	public String getServiceName();
	public boolean isConnectionToMasterServerEstablished();
	public int getAmountServers();
	public List<ServerInterface> getServers();	
	public void registerServer();
	public void registerServer(ServerInterface server);
	public boolean unregisterServer();
	public boolean unregisterServer(ServerInterface server);
	Runnable createRunnable(RUNFUNCTION func);
	public boolean isConnectedToAServer();
}