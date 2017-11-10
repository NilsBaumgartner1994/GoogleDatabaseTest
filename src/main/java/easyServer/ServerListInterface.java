package easyServer;
import java.util.List;

public interface ServerListInterface {

	public boolean connectToMasterServer();
	public boolean isConnectionToMasterServerEstablished();
	public int getAmountServers();
	public List<ServerInterface> getServers();	
	public boolean registerServer();
	public boolean unregisterServer();
	
}