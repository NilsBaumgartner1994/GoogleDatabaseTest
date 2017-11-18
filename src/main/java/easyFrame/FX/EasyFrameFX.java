package easyFrame.FX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.google.api.client.util.Charsets;
import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.responsive.ResponsiveHandler;

import easyBasic.Logger;
import easyFrame.EasyFrameButton;
import easyFrame.EasyFrameInterface;
import easyFrame.EasyProgressStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EasyFrameFX implements EasyFrameInterface {

	private static final String pathToGrapthis = "assets/graphics/";

	private final TableView<Person> table = new TableView<>();
	private final ObservableList<Person> data = FXCollections.observableArrayList(
			new Person("Jacob", "Smith", "jacob.smith@example.com"),
			new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
			new Person("Ethan", "Williams", "ethan.williams@example.com"),
			new Person("Emma", "Jones", "emma.jones@example.com"),
			new Person("Michael", "Brown", "michael.brown@example.com"));
	final HBox hb = new HBox();

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button buttonCurrent = new Button("Current");
		buttonCurrent.setPrefSize(100, 20);

		Button buttonProjected = new Button("Projected");
		buttonProjected.setPrefSize(100, 20);
		hbox.getChildren().addAll(buttonCurrent, buttonProjected);

		return hbox;
	}

	private AnchorPane addAnchorPane(GridPane grid) {

		AnchorPane anchorpane = new AnchorPane();

		Button buttonSave = new Button("Save");
		Button buttonCancel = new Button("Cancel");

		HBox hb = new HBox();
		hb.setPadding(new Insets(0, 10, 10, 10));
		hb.setSpacing(10);
		hb.getChildren().addAll(buttonSave, buttonCancel);

		anchorpane.getChildren().addAll(grid, hb);
		// Anchor buttons to bottom right, anchor grid to top
		AnchorPane.setBottomAnchor(hb, 8.0);
		AnchorPane.setRightAnchor(hb, 5.0);
		AnchorPane.setTopAnchor(grid, 10.0);

		return anchorpane;
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);

		Text title = new Text("Data");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);

		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Sales"), new Hyperlink("Marketing"),
				new Hyperlink("Distribution"), new Hyperlink("Costs") };

		for (int i = 0; i < 4; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

		return vbox;
	}

	public void addStackPane(HBox hb) {
		StackPane stack = new StackPane();
		Rectangle helpIcon = new Rectangle(30.0, 25.0);
		helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.web("#4977A3")), new Stop(0.5, Color.web("#B0C6DA")),
						new Stop(1, Color.web("#9CB6CF")), }));
		helpIcon.setStroke(Color.web("#D0E6FA"));
		helpIcon.setArcHeight(3.5);
		helpIcon.setArcWidth(3.5);

		Text helpText = new Text("?");
		helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		helpText.setFill(Color.WHITE);
		helpText.setStroke(Color.web("#7080A0"));

		stack.getChildren().addAll(helpIcon, helpText);
		stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
		StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

		hb.getChildren().add(stack); // Add to HBox from Example 1-2
		HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
	}

	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		// Category in column 2, row 1
		Text category = new Text("Sales:");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 1, 0);

		// Title in column 3, row 1
		Text chartTitle = new Text("Current Year");
		chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(chartTitle, 2, 0);

		// Subtitle in columns 2-3, row 2
		Text chartSubtitle = new Text("Goods and Services");
		grid.add(chartSubtitle, 1, 1, 2, 1);

		// House icon in column 1, rows 1-2
		ImageView imageHouse = new ImageView(getImageByPath("house.png"));
		grid.add(imageHouse, 0, 0, 1, 2);

		// Left label in column 1 (bottom), row 3
		Text goodsPercent = new Text("Goods\n80%");
		GridPane.setValignment(goodsPercent, VPos.BOTTOM);
		grid.add(goodsPercent, 0, 2);

		// Chart in columns 2-3, row 3
		ImageView imageChart = new ImageView(getImageByPath("piechart.png"));
		grid.add(imageChart, 1, 2, 2, 1);

		// Right label in column 4 (top), row 3
		Text servicesPercent = new Text("Services\n20%");
		GridPane.setValignment(servicesPercent, VPos.TOP);
		grid.add(servicesPercent, 3, 2);

		return grid;
	}

	public FlowPane addFlowPane() {
		FlowPane flow = new FlowPane();
		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setPrefWrapLength(170); // preferred width allows for two columns
		flow.setStyle("-fx-background-color: DAE6F3;");

		ImageView pages[] = new ImageView[8];
		for (int i = 0; i < 8; i++) {
			pages[i] = new ImageView(getImageByPath("chart_" + (i + 1) + ".png"));
			flow.getChildren().add(pages[i]);
		}

		return flow;
	}

	public Image getImageByPath(String path) {
		File f = new File(pathToGrapthis + path);

		try {
			return new Image(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public EasyFrameFX(Stage stage) {
		// BorderPane border = new BorderPane();
		// HBox hbox = addHBox();
		// border.setTop(hbox);
		// border.setLeft(addVBox());
		// addStackPane(hbox); // Add stack to HBox in top region
		//
		// border.setCenter(addGridPane());
		// border.setRight(addFlowPane());
		//
		//
		//
		// Scene scene = new Scene(new Group());
		// stage.setTitle("Table View Sample");
		// stage.setWidth(450);
		// stage.setHeight(550);
		//
		// final Label label = new Label("Address Book");
		// label.setFont(new Font("Arial", 20));
		//
		// table.setEditable(true);
		//
		// Callback<TableColumn<Person, String>,
		// TableCell<Person, String>> cellFactory
		// = (TableColumn<Person, String> p) -> new EditingCell();
		//
		// TableColumn<Person, String> firstNameCol =
		// new TableColumn<>("First Name");
		// TableColumn<Person, String> lastNameCol =
		// new TableColumn<>("Last Name");
		// TableColumn<Person, String> emailCol =
		// new TableColumn<>("Email");
		//
		// firstNameCol.setMinWidth(100);
		// firstNameCol.setCellValueFactory(
		// new PropertyValueFactory<>("firstName"));
		// firstNameCol.setCellFactory(cellFactory);
		// firstNameCol.setOnEditCommit(
		// (CellEditEvent<Person, String> t) -> {
		// ((Person) t.getTableView().getItems().get(
		// t.getTablePosition().getRow())
		// ).setFirstName(t.getNewValue());
		// });
		//
		//
		// lastNameCol.setMinWidth(100);
		// lastNameCol.setCellValueFactory(
		// new PropertyValueFactory<>("lastName"));
		// lastNameCol.setCellFactory(cellFactory);
		// lastNameCol.setOnEditCommit(
		// (CellEditEvent<Person, String> t) -> {
		// ((Person) t.getTableView().getItems().get(
		// t.getTablePosition().getRow())
		// ).setLastName(t.getNewValue());
		// });
		//
		// emailCol.setMinWidth(200);
		// emailCol.setCellValueFactory(
		// new PropertyValueFactory<>("email"));
		// emailCol.setCellFactory(cellFactory);
		// emailCol.setOnEditCommit(
		// (CellEditEvent<Person, String> t) -> {
		// ((Person) t.getTableView().getItems().get(
		// t.getTablePosition().getRow())
		// ).setEmail(t.getNewValue());
		// });
		//
		// table.setItems(data);
		// table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
		//
		// final TextField addFirstName = new TextField();
		// addFirstName.setPromptText("First Name");
		// addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
		// final TextField addLastName = new TextField();
		// addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		// addLastName.setPromptText("Last Name");
		// final TextField addEmail = new TextField();
		// addEmail.setMaxWidth(emailCol.getPrefWidth());
		// addEmail.setPromptText("Email");
		//
		// final Button addButton = new Button("Add");
		// addButton.setOnAction((ActionEvent e) -> {
		// data.add(new Person(
		// addFirstName.getText(),
		// addLastName.getText(),
		// addEmail.getText()));
		// addFirstName.clear();
		// addLastName.clear();
		// addEmail.clear();
		// });
		//
		// hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
		// hb.setSpacing(3);
		//
		// final VBox vbox = new VBox();
		// vbox.setSpacing(5);
		// vbox.setPadding(new Insets(10, 0, 0, 10));
		//
		//
		// vbox.getStyleClass().addAll("visible-xs", "visible-sm");
		// table.getStyleClass().addAll("visible-xs", "visible-sm");
		// hb.getStyleClass().addAll("visible-xs", "visible-sm");
		//
		// vbox.getChildren().addAll(label, table, hb);
		//
		//
		//
		//
		// ((Group) scene.getRoot()).getChildren().addAll(vbox);
		//
		// stage.setScene(scene);
		// ResponsiveHandler.addResponsiveToWindow(stage);
		// stage.show();
		// FlatterFX.style();
		//
		//
		// list.setItems(items);
		// list.getStyleClass().addAll("visible-xs", "visible-sm");

		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();

		HBox hbox = addHBox();
		border.setTop(hbox);
		border.setLeft(addVBox());

		// Add a stack to the HBox in the top region
		addStackPane(hbox);

		// To see only the grid in the center, uncomment the following statement
		// comment out the setCenter() call farther down
		// border.setCenter(addGridPane());

		// Choose either a TilePane or FlowPane for right region and comment out the
		// one you aren't using
		border.setRight(addFlowPane());
		// border.setRight(addTilePane());

		// To see only the grid in the center, comment out the following statement
		// If both setCenter() calls are executed, the anchor pane from the second
		// call replaces the grid from the first call
		border.setCenter(addAnchorPane(addGridPane()));

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("Layout Sample");
		ResponsiveHandler.addResponsiveToWindow(stage);
		stage.show();
	}

	@Override
	public void openWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void minimizeWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maximizeWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWindowSize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWindowVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExternProgressStatus(EasyProgressStatus status) {
		// TODO Auto-generated method stub

	}

	@Override
	public EasyProgressStatus getOwnProgressStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EasyProgressStatus getExternProgressStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EasyFrameButton addButton(String text, Runnable function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EasyFrameButton addButton(EasyFrameButton button) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeButton(EasyFrameButton button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(JComponent attributeClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends JComponent> T addAttribute(T attributeClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPanel(JPanel panel) {
		// TODO Auto-generated method stub

	}

}
