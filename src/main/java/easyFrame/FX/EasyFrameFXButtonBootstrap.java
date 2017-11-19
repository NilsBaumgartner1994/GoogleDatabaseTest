package easyFrame.FX;

import easyFrame.FX.EasyFrameFXBootstrapStyles.COLOUR;
import easyFrame.FX.EasyFrameFXBootstrapStyles.SIZE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EasyFrameFXButtonBootstrap extends Button {
	
	private static final String styleType = "btn";
	
	private COLOUR colour;
	private SIZE size;

	public EasyFrameFXButtonBootstrap(String string, final Runnable function) {
		super(string);
		setBootstrapColour(COLOUR.WARNING);
		setBootstrapSize(SIZE.LARGE);
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
	
	private void setBootstrapColour(EasyFrameFXBootstrapStyles.COLOUR colour) {
		this.colour = colour;
		applyStyle();
	}
	
	private void setBootstrapSize(EasyFrameFXBootstrapStyles.SIZE size) {
		this.size = size;
		applyStyle();
	}

	private void applyStyle() {
		String colour = EasyFrameFXBootstrapStyles.getSpecificColorString(styleType, this.colour);
		String size = EasyFrameFXBootstrapStyles.getSpecificSizeString(styleType, this.size);
		
		this.getStyleClass().setAll(styleType, colour, size);
	}

}
