package easyFrame;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EasyFrameButton extends JButton implements EasyFrameButtonInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8708995754205161333L;

	public EasyFrameButton(JPanel panel) {
		this(panel,"");
	}

	public EasyFrameButton(JPanel panel, String string) {
		super(string);
		// TODO Auto-generated constructor stub
		// JButton wird dem Panel hinzugefügt
        panel.add(this);
	}
	
}
