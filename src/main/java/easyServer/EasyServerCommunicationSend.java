package easyServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import easyBasic.Logger;

public class EasyServerCommunicationSend {

	private EasyServerInformationInterface server;
	private EasyServerCommunicationTyp typ;
	private EasyServerCommunicationReceive callback;
	private EasyServerCommunicationSendThread send;
	private Thread thread;

	public EasyServerCommunicationSend(EasyServerInformationInterface server, EasyServerCommunicationTyp typ,
			EasyServerCommunicationReceive callback) {
		send = new EasyServerCommunicationSendThread(server, typ, callback);

		if (isValidSetup()) {
			thread = new Thread(send);
			thread.start();
		}
	}

	public void sendMessage(String message) {
		send.sendMessage(message);
	}

	public boolean isValidSetup() {
		return send.isValidSetup();
	}

}