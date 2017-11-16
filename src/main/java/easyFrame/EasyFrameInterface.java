package easyFrame;

import javax.swing.JComponent;
import javax.swing.JPanel;

public interface EasyFrameInterface {
	
	public void openWindow();
	public void closeWindow();
	public void minimizeWindow();
	public void maximizeWindow();
	public void setWindowSize(int width, int height);
	public void setWindowVisible(boolean visible);
	public void setExternProgressStatus(EasyProgressStatus status);
	public EasyProgressStatus getOwnProgressStatus();
	public EasyProgressStatus getExternProgressStatus();
	public EasyFrameButton addButton(String text, Runnable function);
	public EasyFrameButton addButton(EasyFrameButton button);
	public void removeButton(EasyFrameButton button);
	public void removeAttribute(JComponent attributeClass);
	public <T extends JComponent> T addAttribute(T attributeClass);
	public void addPanel(JPanel panel);

}
