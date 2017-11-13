package easyServer;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;

public class EasyUpdateAction {

	private String column;
	private String row;
	private BatchUpdateValuesResponse response;

	public EasyUpdateAction(String column, String row, BatchUpdateValuesResponse response) {
		this.column = column;
		this.row = row;
		this.response = response;
	}

	public String getColumn() {
		return this.column;
	}

	public String getRow() {
		return this.row;
	}

	public BatchUpdateValuesResponse getResponse() {
		return this.response;
	}

}