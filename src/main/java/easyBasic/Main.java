package easyBasic;
import easyServer.ServerListGoogleSheets;
import easyServer.ServerListInterface;

public class Main {

	public static ServerListInterface serverHandler;
	
	public static void main(String[] args) {
		Logger.println("Setting Up ServerHandler");
		serverHandler = new ServerListGoogleSheets();
		Logger.println("ServersRegistered: "+serverHandler.getAmountServers());
		Logger.println("Register now Server");
		serverHandler.registerServer();
		serverHandler.getServers();

	}
}