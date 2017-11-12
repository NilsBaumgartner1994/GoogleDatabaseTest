package easyFrame;

import javax.swing.JComponent;

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
	public void addButton(String text, Runnable function);

}
