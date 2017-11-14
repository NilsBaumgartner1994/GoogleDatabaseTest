package easyServer;
import java.util.List;

public interface ServerListInterface {
	
	public static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER
	};

	public boolean connectTo(ServerInterface server);
	public boolean connectToMasterServer();
	public void disconnectFromMasterServer();
	public boolean connectToMasterServerIfNotConnected();
	public String getServiceName();
	public boolean isConnectionToMasterServerEstablished();
	public int getAmountServers();
	public List<ServerInterface> getServers();	
	public void registerServer();
	public ServerInterface registerServer(ServerInterface server);
	public boolean unregisterServer();
	public boolean unregisterServer(ServerInterface server);
	Runnable createRunnable(RUNFUNCTION func);
	public boolean isConnectedToAServer();
}