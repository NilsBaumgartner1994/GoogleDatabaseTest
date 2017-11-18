package easyFrame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.guigarage.responsive.ResponsiveHandler;

public class EasyFrameSwing implements EasyFrameInterface {

	private JFrame frame;
	private JPanel panel;
	private EasyProgressStatus externStatus;
	private EasyProgressStatus ownStatus;

	private static final int defaultWidth = 640 * 2;
	private static final int defaultHeight = 480 * 2;
	private static final String defaultTitle = "MyEasyFrame";

	public EasyFrameSwing(String title, int width, int height) {
		ownStatus = new EasyProgressStatus("Create EasyFrame",0);
		
		
		
		frame = new JFrame();
		Image icon = new javax.swing.ImageIcon("assets/images/icon.png").getImage();
		frame.setIconImage(icon);
		frame.setLayout(new GridLayout(2, 1));

		// make sure the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setSize(width, height);

		// This will center the JFrame in the middle of the screen
		frame.setLocationRelativeTo(null);
		

		frame.setVisible(true);
		
		initFrame();
		
		this.updateOwnProgressStatus("Create EasyFrame", 100);
	}

	public EasyFrameSwing() {
		this(defaultTitle, defaultWidth, defaultHeight);
	}

	public EasyFrameSwing(String title) {
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
		
		panel = new JPanel(new GridLayout(0, 1, 10, 10));
	
		// JButton mit Text "Dr�ck mich" wird erstellt
		EasyFrameButton button = addAttribute(new EasyFrameButton("Dr�ck mich"));
		EasyTextField field = addAttribute(new EasyTextField(panel,"TestField"));
		field.setFontSize(24);
		
		frame.add(panel);
		// Fenstergr��e wird so angepasst, dass
		// der Inhalt reinpasst
		frame.pack();
		
		this.updateOwnProgressStatus("Init Framecontent", 100);
	}

	@Override
	public void addPanel(JPanel panel){
		frame.add(panel);
		frame.pack();
	}
	
	@Override
	public <T extends JComponent> T addAttribute(T attributeClass){
		panel.add(attributeClass);
		frame.pack();
		return attributeClass;
	}
	
	@Override
	public void removeAttribute(JComponent attributeClass){
		panel.remove(attributeClass);
		frame.pack();
	}
	
	@Override
	public EasyFrameButton addButton(String text, Runnable function) {
		return addButton(new EasyFrameButton(text, function));
	}
	
	@Override
	public EasyFrameButton addButton(EasyFrameButton button) {
		return addAttribute(button);
	}
	
	@Override
	public void removeButton(EasyFrameButton button) {
		removeAttribute(button);
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
