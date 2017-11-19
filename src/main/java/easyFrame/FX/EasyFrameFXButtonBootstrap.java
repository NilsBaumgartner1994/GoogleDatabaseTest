package easyFrame.FX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EasyFrameFXButtonBootstrap extends Button {
	
	private static final String styleType = "btn";

	public static enum STYLE {
		DEFAULT, PRIMARY, SUCCESS, INFO, WARNING, DANGER
	};
	
	public static enum SIZE {
		LG, SM, XS
	}
	
	private String style;
	private String size;

	public EasyFrameFXButtonBootstrap(String string, final Runnable function) {
		super(string);
		setBootstrapStyle(STYLE.WARNING);
		setBootstrapSize(SIZE.LG);
		setRunnableFunction(function);
	}
	
	public void setRunnableFunction(final Runnable function) {
		this.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (function != null) {
					function.run();
				}
			}
		});
	}
	
	private void setBootstrapStyle(STYLE style) {
		switch (style) {
		case PRIMARY:
			setBootstrapStyle("primary");
			break;
		case SUCCESS:
			setBootstrapStyle("success");
			break;
		case INFO:
			setBootstrapStyle("info");
			break;
		case WARNING:
			setBootstrapStyle("warning");
			break;
		case DANGER:
			setBootstrapStyle("danger");
			break;
		default:
			setBootstrapStyle("default");
			break;
		}
		
		applyStyle();
	}
	
	private void setBootstrapStyle(String style) {
		this.style = "btn-"+style;
	}
	
	private void setBootstrapSize(SIZE size) {
		switch (size) {
		case LG:
			setColorStyle("lg");
			break;
		case SM:
			setColorStyle("sm");
			break;
		case XS:
			setColorStyle("xs");
			break;
		default:
			setColorStyle("sm");
			break;
		}
		
		applyStyle();
	}
	
	private void setColorStyle(String size) {
		this.size = styleType+"-"+size;
	}

	private void applyStyle() {
		this.getStyleClass().setAll(styleType, this.style, "btn-lg");
	}
		
	private void setSizeStyle(String size) {
		this.style = styleType+"-"+style;
	}

}
