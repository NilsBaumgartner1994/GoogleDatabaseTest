package easyFrame;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EasyFrame implements EasyFrameInterface {

	private JFrame frame;
	private JPanel panel;
	private EasyProgressStatus externStatus;
	private EasyProgressStatus ownStatus;

	private static final int defaultWidth = 640 * 2;
	private static final int defaultHeight = 480 * 2;
	private static final String defaultTitle = "MyEasyFrame";

	public EasyFrame(String title, int width, int height) {
		ownStatus = new EasyProgressStatus("Create EasyFrame",0);
		
		frame = new JFrame();

		// make sure the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.setSize(width, height);

		// This will center the JFrame in the middle of the screen
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

		initFrame();
		
		this.updateOwnProgressStatus("Create EasyFrame", 100);
	}

	public EasyFrame() {
		this(defaultTitle, defaultWidth, defaultHeight);
	}

	public EasyFrame(String title) {
		this(title, defaultWidth, defaultHeight);
	}

	public void setExternProgressStatus(EasyProgressStatus status) {
		this.externStatus = status;
	}
	
	@Override
	public EasyProgressStatus getExternProgressStatus() {
		return this.externStatus;
	}
	
	private void updateOwnProgressStatus(String status, int percent) {
		if (externStatus != null) {
			externStatus.updateStatus(status, percent);
		}
	}
	
	@Override
	public EasyProgressStatus getOwnProgressStatus() {
		return this.ownStatus;
	}

	private void initFrame() {
		this.updateOwnProgressStatus("Init Framecontent", 0);
		
		panel = new JPanel();
	
		// JButton mit Text "Drück mich" wird erstellt
		EasyFrameButton button = addAttribute(new EasyFrameButton("Drück mich"));
		EasyTextField field = addAttribute(new EasyTextField(panel,"TestField"));
		field.setFontSize(24);
		
		frame.add(panel);
		// Fenstergröße wird so angepasst, dass
		// der Inhalt reinpasst
		frame.pack();
		
		this.updateOwnProgressStatus("Init Framecontent", 100);
	}
	
	public <T extends JComponent> T addAttribute(T attributeClass){
		panel.add(attributeClass);
		return attributeClass;
	}

	@Override
	public void openWindow() {
		frame.setExtendedState(JFrame.NORMAL);
	}

	@Override
	public void closeWindow() {
		setWindowVisible(false); // you can't see me!
		frame.dispose(); // Destroy the JFrame object
	}

	@Override
	public void minimizeWindow() {
		frame.setExtendedState(JFrame.ICONIFIED);
	}

	@Override
	public void maximizeWindow() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	@Override
	public void setWindowVisible(boolean visible) {
		frame.setVisible(visible);
	}

	@Override
	public void setWindowSize(int width, int height) {
		frame.setSize(new Dimension(width,height));
	}



}
