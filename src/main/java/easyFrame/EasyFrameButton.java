package easyFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import easyBasic.Logger;

public class EasyFrameButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708995754205161333L;

	public EasyFrameButton(String string) {
		super(string);
	}

	public EasyFrameButton(String string, final Runnable function) {
		super(string);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function.run();
			}
		};

		this.addActionListener(listener);
	}

}
