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

public class EasyServerCommunication {

	public static enum COMMUNICATIONTYP {
		HOST, CLIENT
	}

	private COMMUNICATIONTYP typ;
	private ServerInterface server;

	public EasyServerCommunication(ServerInterface server, COMMUNICATIONTYP typ) {
		this.typ = typ;
		this.server = server;

		Logger.println("Setup depending on Typ");
		Logger.println("Typ: "+typ.name());
		if (isHost()) {
			setupAsHost();
		} else if (isClient()) {
			setupAsClient();
		}
	}

	private boolean isHost() {
		return this.typ == COMMUNICATIONTYP.HOST;
	}

	private boolean isClient() {
		return this.typ == COMMUNICATIONTYP.CLIENT;
	}

	private void setupAsHost() {
		Logger.println("Method setupAsHost");
		try {
			Logger.println("Setup as Host");
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(server.getPort()));
			Logger.println("Listen for a Connection");
			Socket clientSocket = serverSocket.accept();
			Logger.println("Found a Connection");
			
			OutputStream socketoutstr = clientSocket.getOutputStream();
			OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
			BufferedWriter bw = new BufferedWriter(osr);

			InputStream socketinstr = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(socketinstr);
			BufferedReader br = new BufferedReader(isr);

			String anfrage;
			String antwort;

			anfrage = br.readLine();
			antwort = "Antwort auf " + anfrage;
			bw.write(antwort);
			bw.newLine();
			bw.flush();

			bw.close();
			br.close();
			clientSocket.close();
			serverSocket.close();
		} catch (UnknownHostException uhe) {
			System.out.println(uhe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private void setupAsClient() {
		try {
			Socket meinEchoSocket = new Socket(server.getIP(), Integer.parseInt(server.getPort()));

			OutputStream socketoutstr = meinEchoSocket.getOutputStream();
			OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
			BufferedWriter bw = new BufferedWriter(osr);

			InputStream socketinstr = meinEchoSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(socketinstr);
			BufferedReader br = new BufferedReader(isr);

			String anfrage = "Hallo";
			String antwort;

			bw.write(anfrage);
			bw.newLine();
			bw.flush();
			antwort = br.readLine();

			System.out.println("Host = " + server.getIP());
			System.out.println("Echo = " + antwort);

			bw.close();
			br.close();
			meinEchoSocket.close();
		} catch (UnknownHostException uhe) {
			System.out.println(uhe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

}