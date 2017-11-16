package easyServer;

import java.util.ArrayList;
import java.util.List;

import easyBasic.Logger;

public class EasyServerCommunicationReceive {

	public static enum TYPE {
		SETTINGUP, WAITING, ESTABLISHED, LOST, CLOSED, MESSAGE, ERROR
	}

	public EasyServerCommunicationReceive() {
		Logger.println("Create Functions List");
		functions = new ArrayList<EasyRunnableParameters<TYPE>>();
		for(TYPE typ : TYPE.values()) {
			functions.add(null);
		}
		Logger.println("Size: "+functions.size());
	}

	List<EasyRunnableParameters<TYPE>> functions;

	public void setCallbackRunnable(TYPE function, EasyRunnableParameters<TYPE> runnable) {
		functions.set(function.ordinal(), runnable);
	}

	public void callback(TYPE function, Object param) {
		EasyRunnableParameters<TYPE> runnable = functions.get(function.ordinal());
		if (runnable != null) {
			runnable.setParam(param);
			runnable.setType(function);
			runnable.run();
		}
	}

	public void connectionSettingUp(String optionalMessage) {
		Logger.println(optionalMessage);
		callback(TYPE.SETTINGUP, optionalMessage);
	}

	public void connectionWaiting(String optionalMessage) {
		Logger.println(optionalMessage);
		callback(TYPE.WAITING, optionalMessage);
	}

	public void connectionEstablished(String optionalMessage) {
		Logger.println(optionalMessage);
		callback(TYPE.ESTABLISHED, optionalMessage);
	}

	public void connectionLost(String optionalMessage) {
		Logger.println(optionalMessage);
		callback(TYPE.LOST, optionalMessage);
	}

	public void connectionClosed(String optionalMessage) {
		Logger.println(optionalMessage);
		callback(TYPE.CLOSED, optionalMessage);
	}

	public void receiveMessage(String message) {
		Logger.println(message);
		callback(TYPE.MESSAGE, message);
	}

	public void error(String message) {
		Logger.println(message);
		callback(TYPE.ERROR, message);
	}

}