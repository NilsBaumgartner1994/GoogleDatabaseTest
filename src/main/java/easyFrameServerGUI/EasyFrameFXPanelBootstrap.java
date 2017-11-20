package easyFrameServerGUI;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EasyFrameFXPanelBootstrap extends Panel {
	
	private static final String styleType = "panel";

	public static enum STYLE {
		DEFAULT, PRIMARY, SUCCESS, INFO, WARNING, DANGER
	};
	
	private STYLE style;

	public EasyFrameFXPanelBootstrap(String string) {
		super(string);
		setBootstrapStyle(STYLE.DEFAULT);
	}

	public void setBootstrapStyle(STYLE style) {
		this.style = style;
		applyStyle();
	}
	
	private void applyStyle() {
		switch (style) {
		case PRIMARY:
			setBootstrapStyle(styleType+"-primary");
			break;
		case SUCCESS:
			setBootstrapStyle(styleType+"-success");
			break;
		case INFO:
			setBootstrapStyle(styleType+"-info");
			break;
		case WARNING:
			setBootstrapStyle(styleType+"-warning");
			break;
		case DANGER:
			setBootstrapStyle(styleType+"-danger");
			break;
		default:
			setBootstrapStyle(styleType+"-default");
			break;
		}
	}

	private void setBootstrapStyle(String style) {
		this.getStyleClass().setAll(styleType, style);
	}

}
