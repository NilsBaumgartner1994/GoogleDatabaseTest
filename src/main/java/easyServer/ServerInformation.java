package easyServer;
public class ServerInformation implements ServerInterface{

	private String displayName;
	private String ip;
	private String updateTime;
	private String owner;
	private String uniqueID;
	
	public ServerInformation(String uniqueID, String displayName, String ip, String updateTime, String owner) {
		this.setDisplayName(displayName);
		this.setIP(ip);
		this.setOwner(owner);
		this.setUniqueID(uniqueID);
		this.setUpdateTime(updateTime);
	}
	
	@Override
	public void connect() {
		
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public String getIP() {
		return this.ip;
	}

	@Override
	public String getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}

	@Override
	public String getUniqueID() {
		return this.uniqueID;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setIP(String ip) {
		this.ip = ip;
	}

	@Override
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	

}