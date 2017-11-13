package easyServer;
public interface ServerInterface {

	public void connect();
	public void disconnect();
	
	public String getDisplayName();
	public String getIP();
	public String getUpdateTime();
	public String getOwner();
	public String getUniqueID();
	public void setDisplayName(String displayName);
	public void setIP(String ip);
	public void setUpdateTime(String updateTime);
	public void setOwner(String owner);
	public void setUniqueID(String uniqueID);

}