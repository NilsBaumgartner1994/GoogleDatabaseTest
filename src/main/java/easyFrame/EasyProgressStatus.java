package easyFrame;

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
