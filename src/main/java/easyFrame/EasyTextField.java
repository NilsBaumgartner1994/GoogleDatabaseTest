package easyFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import easyBasic.Logger;

public class EasyTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814832604375114030L;
	
	public EasyTextField(JPanel panel) {
		this(panel,"");
	}

	public EasyTextField(JPanel panel, String string) {
		super(string,20);
        panel.add(this);
	}
	
	public void setFontSize(int fontSize) {
		Font font = this.getFont();
		Font font1 = new Font(font.getFontName(), Font.PLAIN, fontSize);
		this.setFont(font1);
	}

}
