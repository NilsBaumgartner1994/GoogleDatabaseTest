package easyServer;
public interface ServerInterface {

	public void connect();
	public void disconnect();
	
	public String getDisplayName();
	public String getIP();
	public long getUpdateTime();
	public String getOwner();
	

}