package easyBasic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import easyFrame.EasyFrame;
import easyFrame.EasyFrameInterface;
import easyServer.EasyGoogleSheetsHandler;
import easyServer.EasyServerCommunicationReceive;
import easyServer.EasyServerListGoogleSheet;
import easyServer.EasyServerListInterface;

public class Main {

	public static EasyServerGUI serverGui;
	public static EasyServerCommunicationReceive receive;
	
	public static void main(String[] args) {
		receive = new EasyServerCommunicationReceive();
		serverGui = new EasyServerGUI(receive);
	}
	
}