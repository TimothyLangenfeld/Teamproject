import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author tabea
 * @version 1.0
 * 
 */

public class Teamproject extends Application {

	/** Layout componants */

	VBox BaseLayout;

	GridPane grid;
	HBox UpperPane;
	VBox infoBar;

	HBox textBar;

	MenuBar menubar;

	Label photoNameLabel;
	Label photoInfo;

	/** Arraylist of the Imageobjects */

	List<LoadedImage> currentImages = new ArrayList<>();

	/** add to grid auxiliary variable */

	File d;
	File[] fulllist;

	/** Image Anzeige Informationen */

	String name;
	String bemerkung;
	String contentType;

	double size;
	double width;
	double height;

	int spalte = 0;
	int zeile = 0;
	int imageCount = -1;
	int gridPosition = 0;

	/** Last chosen Image */

	ImageView lastImage;

	/** Logger */

	Logger logger = Logger.getLogger("main logger");

	/** main-class */

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/** logger-Configurations **/

		logger.info("Logger name is " + logger.getName());
		logger.info("WUPDI, the programm started alright!");

		FileHandler handler = new FileHandler("log.txt");
		logger.addHandler(handler);

		logger.setLevel(Level.ALL);

		/** Layout */

		BorderPane root = new BorderPane();

		BaseLayout = new VBox(5);

		UpperPane = new HBox(2);

		grid = new GridPane();

		textBar = new HBox(3);

		infoBar = new VBox(1);

		primaryStage.setTitle("Fotobuch");
		
		/** Menubar settings */

		menubar = new MenuBar();

		// Create menus
		Menu fileMenu = new Menu("Datei");
		Menu editMenu = new Menu("Bearbeiten");
		Menu helpMenu = new Menu("Hilfe");
		Menu loggerMenu = new Menu("Logger");

		/** Logger-checklist */

		RadioMenuItem severe = new RadioMenuItem("Severe");
		RadioMenuItem info = new RadioMenuItem("Info");
		RadioMenuItem fine = new RadioMenuItem("Fine");
		RadioMenuItem all = new RadioMenuItem("All");
		RadioMenuItem stop = new RadioMenuItem("stop logging");


		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.getToggles().add(severe);
		toggleGroup.getToggles().add(info);
		toggleGroup.getToggles().add(fine);
		toggleGroup.getToggles().add(all);
		toggleGroup.getToggles().add(stop);


		loggerMenu.getItems().add(severe);
		loggerMenu.getItems().add(info);
		loggerMenu.getItems().add(fine);
		loggerMenu.getItems().add(all);
		loggerMenu.getItems().add(stop);


		severe.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logger.setLevel(Level.SEVERE);
				System.out.println(logger.getLevel());
			}
		});

		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logger.setLevel(Level.INFO);
				System.out.println(logger.getLevel());

			}
		});

		fine.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logger.setLevel(Level.FINE);

			}
		});

		all.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logger.setLevel(Level.ALL);

			}
		});

		
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logger.setLevel(Level.OFF);

			}
		});
		/**
		 * Menubar properties add MenuItems
		 */

		root.setTop(menubar);

		menubar.setStyle("-fx-background-color: #D2FDCC");

		MenuItem openDirectory = new MenuItem("Verzeichnis öffnen");
		MenuItem exitProgramm = new MenuItem("Beenden");
		MenuItem deletePicture = new MenuItem("Löschen");
		MenuItem helpItem = new MenuItem("Hilfe");

		fileMenu.getItems().addAll(openDirectory, exitProgramm);
		editMenu.getItems().add(deletePicture);
		helpMenu.getItems().add(helpItem);

		menubar.getMenus().addAll(fileMenu, editMenu, helpMenu, loggerMenu);

		/** Grid properties */

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(15);
		grid.setHgap(15);
		
		grid.setAlignment(Pos.CENTER);
		
		/**get Screen Width
		 * and set the grid size*/
		
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

		grid.setPrefSize(visualBounds.getWidth()-50, 500);

		/** Create a ScrollPane always show vertical scroll bar Horizontal scroll bar is
		 * only displayed when needed*/
		ScrollPane scrollPane = new ScrollPane();

		scrollPane.setContent(grid);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		/** Center Button */
		UpperPane.setAlignment(Pos.CENTER);

		/**
		 * add infobar information
		 */
		photoNameLabel = new Label();
		infoBar.getChildren().add(photoNameLabel);

		/** Textfield to change "bemerkung" */

		TextField textField = new TextField("Schreib was rein...");
		textField.setMaxWidth(400);
		textField.setMinWidth(250);

		Label textLabel = new Label("  Beschriftung: ");

		Button textButton = new Button("Enter");

		textBar.getChildren().addAll(textLabel, textField, textButton);


		/**
		 * Enter bemerkung withtext field reset photoInfo Label
		 */

		textButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {

				currentImages.get(gridPosition).setbemerkung(textField.getText());

				infoBar.getChildren().clear();

				photoNameLabel = new Label("Name: " + name);
				photoInfo = (new Label("Size: " + width + " x " + height + "  Filesize: " + Math.round(size / 1024)
						+ " kb" + "  MimeTypes: " + contentType + "  Bemerkung: "
						+ currentImages.get(gridPosition).getBemerkung()));

				infoBar.getChildren().addAll(photoNameLabel, photoInfo);

			}

		});

		/** Delete all Pictures with Menuitem */

		deletePicture.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				for (int i = 0; i < currentImages.size(); i++) {

					grid.getChildren().remove(currentImages.get(i).getImageView());
					infoBar.getChildren().clear();
					zeile = 0;
					spalte = 0;

				}
			}

		});

		/** open Help Window */

		helpItem.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				BorderPane helpBase = new BorderPane();
				helpBase.setStyle("-fx-background-color: #D2FDCC");

				Text helpLabel = new Text("Wir helfen!");
				Text helpText = new Text("Frag uns: Timonthy und Tabea");

				helpLabel.setStyle("-fx-font-size: 30px ");

				VBox helpLayout = new VBox(2);
				helpLayout.getChildren().addAll(helpLabel, helpText);

				helpBase.getChildren().add(helpLayout);
				helpBase.setCenter(helpText);

				Scene helpScene = new Scene(helpBase, 200, 200);

				// New window (Stage)
				Stage helpWindow = new Stage();
				helpWindow.setTitle("Hilfe");
				helpWindow.setScene(helpScene);

				// Set position of second window, related to primary window.
				helpWindow.setX(primaryStage.getX() + 200);
				helpWindow.setY(primaryStage.getY() + 100);

				helpWindow.setWidth(400);
				helpWindow.setHeight(400);

				helpWindow.show();

			}
		});
		

		/**
		 * FileChooser Directory Chooser only Image-files accepted
		 */

		FileChooser chooser = new FileChooser();
		DirectoryChooser dChooser = new DirectoryChooser();

		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

		/** Buttons for directory chooser */

		Button openButton = new Button("Bild auswählen");

		UpperPane.getChildren().add(openButton);

		openButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				chooser.setTitle("Wähle ein Bild aus");
				File f = chooser.showOpenDialog(primaryStage);
				try {
				if (f != null) {
					addImagetoGrid(f);

				}
				}
				catch (Exception e4) {
					logger.log(Level.SEVERE, "Images could not be found or Error occured while using FileChooser", e4);
				}

			}

		});

		/** open directory with menuitem */
		Button directoryButton = new Button("Bilderordner auswählen");

		UpperPane.getChildren().add(directoryButton);

		openDirectory.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				dChooser.setTitle("Wähle ein Bildordner aus");
				d = dChooser.showDialog(primaryStage);
				try {

				File[] dlist = d.listFiles();

					if (d != null) {

						for (int i = 0; i < dlist.length; i++) {
							addImagetoGrid(dlist[i]);
						}
					}

				} 
				catch (Exception e2) {
					logger.log(Level.SEVERE, "Images could not be found or Error occured while using DirectoryChooser", e2);
				}

			}

		});

		/** open directory with button */
		directoryButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				dChooser.setTitle("Wähle ein Bildordner aus");
				d = dChooser.showDialog(primaryStage);
				try {

					File[] dlist = d.listFiles();
					if (d != null) {

						for (int i = 0; i < dlist.length; i++) {
							addImagetoGrid(dlist[i]);
						}
					}

				} catch (Exception e3) {

					logger.log(Level.SEVERE, "Images could not be found or Error occured while using DirectoryChooser", e3);

				}
				
			}

		});

		exitProgramm.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				System.exit(0);
				logger.log(Level.FINE, "Propperly terminated Programm");

			}

		});

		/**
		 * build Scene PrimaryStage
		 */

		BaseLayout.getChildren().addAll(root, UpperPane, grid, infoBar, scrollPane, textBar);
		Scene scene1 = new Scene(BaseLayout, 500, 500);

		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	
	/** methode creates ImageObject, adds to Arraylist and Gridpane
	 *  set Node to center 
	 *  @param file
	 */

	private void addImagetoGrid(File f) {

		imageCount++;

		LoadedImage q = new LoadedImage(f, imageCount, spalte, zeile);

		currentImages.add(q);

		grid.add(q.getImageView(), q.getSpalte(), q.getZeile());
		
		GridPane.setHalignment(q.getImageView(), HPos.CENTER);

		
		/**
		 * Placement inside of the grid 3 columns and endless Rows
		 */

		spalte++;

		if (spalte > 2) {

			zeile++;
			spalte = 0;
		}

		(currentImages.get(imageCount).getImageView()).setOnMouseClicked((MouseEvent e) -> {

			/** determine the clicked Image position */

			Integer columPosition = (grid.getColumnIndex((Node) e.getTarget()));
			gridPosition = ((grid.getRowIndex((Node) e.getTarget())) * 3) + columPosition;

			/** reset Imagesize */

			if (lastImage != null) {
				lastImage.setFitHeight(200);
			}

			name = currentImages.get(gridPosition).getName();
			size = currentImages.get(gridPosition).getfileSize();
			width = currentImages.get(gridPosition).getwidth();
			height = currentImages.get(gridPosition).getheight();
			bemerkung = currentImages.get(gridPosition).getBemerkung();

			/** determine Mime-type */

			try {
				contentType = Files.probeContentType(currentImages.get(gridPosition).getFile().toPath());

			}

			catch (Exception e1) {
				logger.log(Level.SEVERE, "MIME COULD NOT BE FOUND", e1);
			}

			/** Information for the infobar */

			photoNameLabel = new Label("Name: " + name);
			photoInfo = (new Label("Size: " + width + " x " + height + "  Filesize: " + Math.round(size / 1024) + " kb"
					+ "  MimeTypes: " + contentType + "  Bemerkung: " + bemerkung));

			/** reset and add info to infobar */

			infoBar.getChildren().clear();
			infoBar.getChildren().addAll(photoNameLabel, photoInfo);

			currentImages.get(gridPosition).makebigger();

			/** save clicked Image */
			lastImage = (ImageView) e.getTarget();

		});

	}

}
