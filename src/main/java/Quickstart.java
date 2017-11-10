import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.BatchUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Quickstart {
	/** Application name. */
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static String spreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

	public static int maxRetriesForConnectionReset = 10;

	private static String clientID = "None";

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = Quickstart.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();

		clientID = flow.getClientId();

		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());

		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * 
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();

		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();

	}

	public static String getOwnIP() throws UnknownHostException {
		InetAddress add = InetAddress.getLocalHost();
		return add.getHostAddress();
	}

	public static String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public static String getUsername() {
		return System.getProperty("user.name");
	}

	public static String getTimeNow() {
		return "" + new Date().getTime();
	}

	private static Sheets service;

	public static void main(String[] args) throws IOException {
		// Build a new authorized API client service.
		service = getSheetsService();

		/**
		 * writeIntoCell("A2",""); Logger.println(readCell("A2"));
		 * writeIntoCell("A2","Hallo"); Logger.println(readCell("A2"));
		 */
		Logger.println(readCell("F1"));

		appendRow(getHostname(), getOwnIP(), getTimeNow(), getUsername());
		appendRow("Spiderman", "127.0.0.2", getTimeNow(), "PeterParker");
		appendRow("Batman", "127.0.0.3", getTimeNow(), "BruceWayne");
		writeIntoRow("A3", "CapAmerica", "127.0.0.4", getTimeNow(), "Steve");

		Logger.println(readCell("F1"));
		
		

	}

	public static void appendRow(String... datas) {

		List<RowData> rowData = new ArrayList<RowData>();
		List<CellData> cellData = new ArrayList<CellData>();

		for (String data : datas) {
			CellData dateField = new CellData();
			dateField.setUserEnteredValue(new ExtendedValue().setStringValue(data));
			cellData.add(dateField);
		}

		rowData.add(new RowData().setValues(cellData));

		BatchUpdateSpreadsheetRequest batchRequests = new BatchUpdateSpreadsheetRequest();
		BatchUpdateSpreadsheetResponse response;
		List<Request> requests = new ArrayList<Request>();

		AppendCellsRequest appendCellReq = new AppendCellsRequest();
		appendCellReq.setSheetId(0);
		appendCellReq.setRows(rowData);
		appendCellReq.setFields("userEnteredValue");

		requests = new ArrayList<Request>();
		requests.add(new Request().setAppendCells(appendCellReq));
		batchRequests = new BatchUpdateSpreadsheetRequest();
		batchRequests.setRequests(requests);

		
		
		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				response = service.spreadsheets().batchUpdate(spreadSheetId, batchRequests).execute();
				retries = 0;
			} catch (SocketException | SocketTimeoutException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				Logger.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				retries = 0;
			}
		}
		/**
		System.out.println("Request \n\n");
		System.out.println(batchRequests.toPrettyString());
		System.out.println("\n\nResponse \n\n");
		System.out.println(response.toPrettyString());
		*/
	}

	public static String getCellFromOffset(String startCell, int colInt) {
		String colString = startCell.replaceAll("\\d", "");
		Logger.println(colString);

		String row = startCell.replaceAll(colString, "");
		int position = 0;
		while (colInt > 0) {
			int rest = colInt % 26;
			int charPos = colString.length() - 1 - position;
			char posChar = colString.charAt(charPos);
			int posCharInt = Character.codePointAt(String.valueOf(posChar), 0);
			char newChar = Character.toChars(posCharInt + rest)[0];
			StringBuilder tempString = new StringBuilder(colString);
			tempString.setCharAt(charPos, newChar);
			colInt = 0;
			colString = tempString.toString();
		}
		return colString + row;
	}

	public static String readCell(String cell) {
		List<List<Object>> values = readCells(cell);

		if (values == null) {
			return "";
		}
		if (values.isEmpty()) {
			return "";
		}
		if (values.get(0).isEmpty()) {
			return "";
		}

		return values.get(0).get(0).toString();
	}

	public static List<List<Object>> readCells(String range) {

		ValueRange response = null;
		List<List<Object>> values = null;
		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				response = service.spreadsheets().values().get(spreadSheetId, range).execute();
				values = response.getValues();
				if (values == null || values.size() == 0) {
					return null;
				} else {
					return values;
				}
			} catch (SocketException | SocketTimeoutException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				Logger.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/**
	 * List<List<Object>> values = response.getValues(); if (values == null ||
	 * values.size() == 0) { System.out.println("No data found."); } else {
	 * System.out.println("Name, Major"); for (List row : values) { // Print columns
	 * A and E, which correspond to indices 0 and 4. System.out.printf("%s, %s\n",
	 * row.get(0), row.get(4)); } }
	 */

	public static void findAndReplace(String find, String replace) {
		List<Request> requests = new ArrayList<>();
		// Change the spreadsheet's title.
		requests.add(new Request().setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
				.setProperties(new SpreadsheetProperties().setTitle("IP-List")).setFields("title")));
		// Find and replace text.
		requests.add(new Request()
				.setFindReplace(new FindReplaceRequest().setFind(find).setReplacement(replace).setAllSheets(true)));

		// Add additional requests (operations) ...

		BatchUpdateSpreadsheetResponse response = null;

		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
				response = service.spreadsheets().batchUpdate(spreadSheetId, body).execute();

			} catch (SocketException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				Logger.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		if (response == null)
			return;

		FindReplaceResponse findReplaceResponse = response.getReplies().get(1).getFindReplace();
		System.out.printf("%d replacements made.", findReplaceResponse.getOccurrencesChanged());
	}

	public static void writeIntoCell(String cell, String data) {
		List<Object> data1 = new ArrayList<Object>();
		data1.add(data);

		List<List<Object>> data2 = new ArrayList<List<Object>>();
		data2.add(data1);

		writeIntoCells(cell, data2);
	}

	public static void writeIntoRow(String startCell, String... dataArr) {
		int cellsEffected = dataArr.length;

		String endCell = getCellFromOffset(startCell, cellsEffected);

		List<Object> data1 = new ArrayList<Object>();
		data1.addAll(Arrays.asList(dataArr));

		List<List<Object>> data2 = new ArrayList<List<Object>>();
		data2.add(data1);

		writeIntoCells(getRange(startCell, endCell), data2);
	}

	public static String getRange(String start, String end) {
		return start + ":" + end;
	}

	public static BatchUpdateValuesResponse writeIntoCells(String range, List<List<Object>> arrData) {
		ValueRange oRange = new ValueRange();
		oRange.setRange(range); // I NEED THE NUMBER OF THE LAST ROW
		oRange.setValues(arrData);

		List<ValueRange> oList = new ArrayList<>();
		oList.add(oRange);

		BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest();
		// USER_ENTERED or RAW
		oRequest.setValueInputOption("USER_ENTERED");
		oRequest.setData(oList);

		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				BatchUpdateValuesResponse oResp1 = service.spreadsheets().values().batchUpdate(spreadSheetId, oRequest)
						.execute();
				return oResp1;
			} catch (SocketException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				Logger.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		// incase something realy mess up
		return null;
	}

}