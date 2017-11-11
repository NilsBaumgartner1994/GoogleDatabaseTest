package easyFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import easyBasic.Logger;

public class EasyProgressStatus {

	private String status;

	public EasyProgressStatus(String status) {
		updateStatus(status);
	}
	
	public EasyProgressStatus(String status, int percent) {
		updateStatus(status,percent);
	}

	public EasyProgressStatus() {
		this("");
	}

	public void updateStatus(String status) {
		this.status = status;
	}

	public void updateStatus(String status, int percent) {
		updateStatus(status + " : " + percent);
	}

}
