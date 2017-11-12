package easyServer;
import java.util.List;

import easyServer.ServerListGoogleSheets.RUNFUNCTION;

public interface ServerListInterface {

	public boolean connectToMasterServer();
	public boolean isConnectionToMasterServerEstablished();
	public int getAmountServers();
	public List<ServerInterface> getServers();	
	public boolean registerServer();
	public boolean unregisterServer();
	Runnable createRunnable(RUNFUNCTION func);
	
}