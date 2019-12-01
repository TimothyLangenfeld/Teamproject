import javafx.stage.DirectoryChooser;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;




public class Teamproject extends Application {
	
 	Image image;
   	GridPane grid;
   	HBox UpperPane;
   	VBox BaseLayout;
   	
   	MenuBar menubar;
   
   	
   	ImageView imageV;
   	
   	File d;
   	File[] fulllist;
   	
   	ImageView [] imageArray = new ImageView[100];
   	
   	Button openButton;
   	Button directoryButton;
   	
   	int spalte=0;
   	int zeile=0;
   	
   	int Bildanzahl=0;

	public static void main(String[] args) {
		launch(args);
	}
	
	private void addImagetoGrid(File f) {
	
		//Image erstelln!!		
			image = new Image("file:"+f.getPath());
			
			imageV = new ImageView();

			imageV.setFitHeight(200);
	        imageV.setPreserveRatio(true);
			
	        imageV.setImage(image);
	        
	        grid.add(imageV, spalte, zeile);
	        spalte++;

	        if(spalte>2){
	        	
	        	zeile++;
	        	spalte=0;
	        }
	        
	        	
	    }
	
	    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		primaryStage.setTitle("Fotobuch");
    	
    	//Menubar
    	
    	 menubar = new MenuBar();
         
         // Create menus
         Menu fileMenu = new Menu("Datei");
         Menu editMenu = new Menu("Bearbeiten");
         Menu helpMenu = new Menu("Hilfe");
         
         
         MenuItem openDirectory = new MenuItem("Verzeichnis öffnen");
         MenuItem exitProgramm = new MenuItem("Beenden");
         
         fileMenu.getItems().addAll(openDirectory, exitProgramm);
         
         menubar.getMenus().addAll(fileMenu, editMenu, helpMenu);

         BorderPane root = new BorderPane();
         root.setTop(menubar);
         
         BaseLayout = new VBox(5); 
     	
     	UpperPane = new HBox(2);
     	
         grid = new GridPane();
         
     	BaseLayout.getChildren().addAll(root,UpperPane,grid);
     	
     	
     	Scene scene1 = new Scene(BaseLayout, 300, 300);
     	
     	
     	//Bildraster
     	//Gitter erstellen
         grid.setPadding(new Insets(10, 10, 10, 10));
         grid.setVgap(10);
         grid.setHgap(10);
         
         
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
     	openButton = new Button("Bild auswählen");
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
        
        directoryButton = new Button ("Bilderordner auswählen");
        UpperPane.getChildren().add(directoryButton);
        
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
        
        exitProgramm.setOnAction(
            	new EventHandler<ActionEvent>() {
            		
            		public void handle(ActionEvent e) {
            		        		
            			System.exit(0);
                	}
            		
            	});
        
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            e.getX();
            e.getY();
           
        });
         
         primaryStage.setScene(scene1);
         primaryStage.show();
     }

 }


