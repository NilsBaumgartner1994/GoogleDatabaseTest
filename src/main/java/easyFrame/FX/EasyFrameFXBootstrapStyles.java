package easyFrame.FX;

import javafx.scene.control.Button;

public class EasyFrameFXBootstrapStyles extends Button {

	public static enum COLOUR {
		DEFAULT, PRIMARY, SUCCESS, INFO, WARNING, DANGER
	}

	public static enum SIZE {
		LARGE, MEDIUM, SMALL
	}

	public static String getSpecificColorString(String nodeClass, COLOUR colour) {
		if(colour==null) return (nodeClass + "-default");
		switch (colour) {
		case PRIMARY:
			return (nodeClass + "-primary");
		case SUCCESS:
			return (nodeClass + "-success");
		case INFO:
			return (nodeClass + "-info");
		case WARNING:
			return (nodeClass + "-warning");
		case DANGER:
			return (nodeClass + "-danger");
		case DEFAULT:
			return (nodeClass + "-default");
		default:
			return (nodeClass + "-default");
		}
	}
	
	public static String getSpecificSizeString(String nodeClass, SIZE size) {
		if(size==null) return (nodeClass + "-sm");
		switch (size) {
		case LARGE:
			return (nodeClass + "-lg");
		case MEDIUM:
			return (nodeClass + "-sm");
		case SMALL:
			return (nodeClass + "-xs");
		default:
			return (nodeClass + "-sm");
		}
	}

}
