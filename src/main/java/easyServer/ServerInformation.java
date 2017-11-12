package easyServer;
public class ServerInformation implements ServerInterface{

	private String displayName;
	private String ip;
	private long updateTime;
	private String owner;
	
	
	public ServerInformation(String displayName, String ip, long updateTime, String owner) {
		this.displayName = displayName;
		this.ip = ip;
		this.updateTime = updateTime;
		this.owner = owner;
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
	public long getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}
	

}