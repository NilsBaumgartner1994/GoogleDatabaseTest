package easyFrameServerGUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import org.kordamp.bootstrapfx.scene.layout.Panel;

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
	
	private Stage stage;

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
		
		
		
		Panel panel = new Panel("This is the title");
        panel.getStyleClass().add("panel-primary");
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        EasyFrameFXButtonBootstrap button = new EasyFrameFXButtonBootstrap("Hello BootstrapFX",null);
        
        Panel panel2 = new Panel("This is the title");
        panel2.getStyleClass().add("panel-primary"); 
        
        panel2.setBody(button);
        
        content.setCenter(panel2);
        
        
        panel.setBody(content);

        grid.add(content, 4, 2);
//        grid.add(panel2, 5, 2);

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
		this.stage = stage;
		BorderPane border = new BorderPane();

		HBox hbox = addHBox();
		border.setTop(hbox);
		border.setLeft(addVBox());
		addStackPane(hbox);
		border.setRight(addFlowPane());
		// border.setRight(addTilePane());

		border.setCenter(addAnchorPane(addGridPane()));

		Scene scene = new Scene(border);
		scene.getStylesheets().addAll("bootstrapfx.css", "org/kordamp/bootstrapfx/sampler.css",
				"org/kordamp/bootstrapfx/xml-highlighting.css");
        scene.getStylesheets().add("bootstrapfx.css");
		this.stage.setScene(scene);
		this.stage.setTitle("Layout Sample");
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

}
