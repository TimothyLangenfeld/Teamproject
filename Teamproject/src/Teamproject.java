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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**@author tabea
 * @version 1.0 */


public class Teamproject extends Application {
	
	/** Layoutbestandteile */
	
   	GridPane grid;
   	
   	HBox UpperPane;
   	VBox BaseLayout;
   	VBox infoBar;

   	HBox textBar;
   	
   	MenuBar menubar;
   	
   	Label photoNameLabel;
   	Label photoInfo;
   	
   	
   	/** Arraylist der Bildobjekte*/
   	
   	List<LoadedImage> currentImages = new ArrayList<>();

   	/**add to grid Hilfsvariablen*/
   	
	File d;
   	File[] fulllist;
   	
   	
   	/** Image Anzeige Informationen*/

	String name;
	String bemerkung;
	String contentType;  	   	

	double size;
	double width;
	double height;
   	
   	int spalte=0;
   	int zeile=0;
   	int imageCount = -1; 	
   	int gridPosition = 0;   	
   	
   	/** Letztes Ausgewähltes Bild */
   	
  	ImageView lastImage;
  	
   	/**Logger*/
  	
    private static Logger logger = Logger.getLogger("Calc"); 


	public static void main(String[] args) {
		launch(args);
	}
	
	private void addImagetoGrid(File f) {

			imageCount++;

	        LoadedImage q = new LoadedImage(f,  imageCount,  spalte,  zeile);
	        
	        q.imageV.setFitHeight(200);
			q.imageV.setPreserveRatio(true);
			
	        q.imageV.setImage(q.image);
	        
	        currentImages.add(q);
	        
	        grid.add(q.imageV, spalte, zeile);
	        
	        spalte++;

	        if(spalte>2){
	        	
	        	zeile++;
	        	spalte=0;
	        }
	        
	        	        
	        (currentImages.get(imageCount).imageV).setOnMouseClicked((MouseEvent e) -> {
	        	
	        	Integer integer = (grid.getColumnIndex((Node) e.getTarget()));
	        	gridPosition = ((grid.getRowIndex((Node) e.getTarget()))*3)+integer;
	        		        	
	        	if(lastImage!=null) {
	        		lastImage.setFitHeight(200);
	        	}
	        	
	        	
	        	selectedObject = currentImages.get(gridPosition);
	        	name = currentImages.get(gridPosition).name;
	        	size = currentImages.get(gridPosition).fileSize;
	        	width = currentImages.get(gridPosition).width;
	        	height = currentImages.get(gridPosition).height;
	        	bemerkung = currentImages.get(gridPosition).bemerkung;
	        	
	        		        	        				        	
				try {
					contentType = Files.probeContentType(currentImages.get(gridPosition).file.toPath());

				} 
				
				catch (Exception e1) {
					logger.log(Level.SEVERE, "MIME NOT FOUND", e1);				
				}
				
	            photoNameLabel = new Label("Name: "+ name );
	            photoInfo = (new Label( "Size: " + width + " x " + height + "  Filesize: "+ Math.round(size/ 1024) + " kb" +
	            "  MimeTypes: " + contentType + "  Bemerkung: " + bemerkung ));
	           
	            infoBar.getChildren().clear();
	            infoBar.getChildren().addAll(photoNameLabel,photoInfo);	

	            currentImages.get(gridPosition).imageV.setFitHeight(205);
	            
	            lastImage = (ImageView) e.getTarget();
	     
	           	            
	        });
	        


	    }
	    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		FileHandler handler = new FileHandler( "log.txt" ); 
		
		logger.info("WUPDI");

		logger.addHandler(handler); 
		
		 
		primaryStage.setTitle("Fotobuch");
		
		textBar = new HBox(3);
    	
    	//Menubar
    	
    	 menubar = new MenuBar();
         
         // Create menus
         Menu fileMenu = new Menu("Datei");
         Menu editMenu = new Menu("Bearbeiten");
         Menu helpMenu = new Menu("Hilfe");
         Menu loggerMenu = new Menu("Logger");
         
         RadioMenuItem severe = new RadioMenuItem("Severe");
         RadioMenuItem info = new RadioMenuItem("Info");
         RadioMenuItem fine = new RadioMenuItem("Fine");

         ToggleGroup toggleGroup = new ToggleGroup();
         toggleGroup.getToggles().add(severe);
         toggleGroup.getToggles().add(info);
         toggleGroup.getToggles().add(fine);

         loggerMenu.getItems().add(severe);
         loggerMenu.getItems().add(info);
         loggerMenu.getItems().add(fine);

         severe.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
               	 handler.setLevel( Level.SEVERE ); 

        	    }
        	   });
         
         info.setOnAction(new EventHandler<ActionEvent>() {
     	    @Override public void handle(ActionEvent e) {
            	 handler.setLevel( Level.INFO ); 

     	    }
     	   });
         
         fine.setOnAction(new EventHandler<ActionEvent>() {
     	    @Override public void handle(ActionEvent e) {
            	 handler.setLevel( Level.FINE ); 

     	    }
     	   });
         
         logger.info("Logger name: " + logger.getName()); 

         
         
         MenuItem openDirectory = new MenuItem("Verzeichnis öffnen");
         MenuItem exitProgramm = new MenuItem("Beenden");
         MenuItem deletePicture = new MenuItem("Löschen");
         MenuItem helpItem = new MenuItem("Hilfe");

         
         fileMenu.getItems().addAll(openDirectory, exitProgramm);
         editMenu.getItems().add(deletePicture);
         helpMenu.getItems().add(helpItem);

         
         
         menubar.getMenus().addAll(fileMenu, editMenu, helpMenu, loggerMenu);

         BorderPane root = new BorderPane();
         root.setTop(menubar);
         
         BaseLayout = new VBox(5); 
     	
     	UpperPane = new HBox(2);
     	
        grid = new GridPane();
        
        grid.setPrefSize(300, 500);
       
         
        // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
 
      
        scrollPane.setContent(grid);
 
        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
         infoBar  = new VBox(1);
         
     	BaseLayout.getChildren().addAll(root,UpperPane,grid,infoBar, scrollPane, textBar);
     	
     	
     	Scene scene1 = new Scene(BaseLayout, 300, 300);
     	
     	
     	//Bildraster
     	//Gitter erstellen
         grid.setPadding(new Insets(10, 10, 10, 10));
         grid.setVgap(10);
         grid.setHgap(10);
         
        UpperPane.setAlignment(Pos.CENTER);
        
        grid.setAlignment(Pos.CENTER);

        photoNameLabel = new Label();
        infoBar.getChildren().add(photoNameLabel);
        
        
        //TEXT FIELD
        TextField textField = new TextField("Schreib was rein...");
        textField.setMaxWidth(400);
        textField.setMinWidth(250);
        
        Label textLabel = new Label("  Beschriftung: ");
        
        Button textButton = new Button("Enter");

        
        textBar.getChildren().addAll(textLabel,textField,textButton);
        
       //FileChooser
     	FileChooser chooser = new FileChooser();
     	
     	//Directory Chooser
     	DirectoryChooser dChooser = new DirectoryChooser();

     	
     	//nur JPG-Dateien Zulassen
     	chooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("All Images", "*.*"),
                 new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                 new FileChooser.ExtensionFilter("PNG", "*.png")
             );
     	
     	//Knopf zum Öffnen
        Button openButton = new Button("Bild auswählen");

		UpperPane.getChildren().add(openButton);
        
        openButton.setOnAction(
        	new EventHandler<ActionEvent>() {
        		
        		public void handle(ActionEvent e) {
        		        		
            	chooser.setTitle("Wähle ein Bild aus");
            	File f = chooser.showOpenDialog(primaryStage);
            	
            	if(f != null) {
            		addImagetoGrid(f);
          
            	}
        		
        	}

        });
        
        textButton.setOnMouseClicked(
            	new EventHandler<MouseEvent>() {
            		
            		public void handle(MouseEvent e) {
            			
            			bemerkung = textField.getText();
            			
            			System.out.println(bemerkung);
            			
            			 photoNameLabel = new Label("Name: "+ name );
         	            photoInfo = (new Label( "Size: " + width + " x " + height + "  Filesize: "+ Math.round(size/ 1024) + " kb" +
         	            "  MimeTypes: " + contentType + "  Bemerkung: " + bemerkung ));
         	           
         	            infoBar.getChildren().clear();
         	            infoBar.getChildren().addAll(photoNameLabel,photoInfo);	
            			
            		}

                });
        
        deletePicture.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            			
            			for (int i=0; i<currentImages.size(); i++) {
            		
                			grid.getChildren().remove(currentImages.get(i).imageV);
             	            infoBar.getChildren().clear();
             	            zeile=0;
             	            spalte=0;


            				 } 
            			}
            		    
            	});
        
        helpItem.setOnAction(
            	new EventHandler<ActionEvent>() {

            		public void handle(ActionEvent e) {   
            			
            			BorderPane helpBase = new BorderPane();
            			helpBase.setStyle("-fx-background-color: #FFE29E");
            			
            			Label helpLabel = new Label("Wir helfen immer!");
            			
            			helpLabel.setStyle("-fx-font-size: 22px");
      
            			 HBox helpLayout = new HBox(2);
            			 helpLayout.getChildren().add(helpLabel);
            			 
            			 helpBase.setCenter(helpLabel);
            			 helpBase.getChildren().add(helpLayout);
            			 
                         Scene helpScene = new Scene(helpBase, 400, 400);
          
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
        
        
        Button directoryButton = new Button ("Bilderordner auswählen");

        UpperPane.getChildren().add(directoryButton);

        
        openDirectory.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            		        		
                	dChooser.setTitle("Wähle ein Bildordner aus");
                	d = dChooser.showDialog(primaryStage);
                	File[] dlist = d.listFiles();
                	
                	if(d != null) {
                		
                		for (int i=0; i<dlist.length;i++) {
                    		addImagetoGrid(dlist[i]);
                		}
                	}
            		
            	}

            });
        
        directoryButton.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            		        		
                	dChooser.setTitle("Wähle ein Bildordner aus");
                	d = dChooser.showDialog(primaryStage);
                	File[] dlist = d.listFiles();
                	
                	if(d != null) {
                		
                		for (int i=0; i<dlist.length;i++) {
                    		addImagetoGrid(dlist[i]);
                		}
                	}
            		
            	}

            });
        
        textButton.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            			
            			currentImages.get(gridPosition).setbemerkung(textField.getText());
            		        	
            			
                		}
                	});
        
      
        
        exitProgramm.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            		        		
            			System.exit(0);
                	}
            		
            	});
        
        
         
         primaryStage.setScene(scene1);
         primaryStage.show();
     }

 }

