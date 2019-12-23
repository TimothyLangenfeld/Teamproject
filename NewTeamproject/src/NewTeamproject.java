import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;




public class NewTeamproject extends Application {
	
 	Image image;
   	GridPane grid;
   	HBox UpperPane;
   	VBox BaseLayout;
   	VBox infoBar;
   	
   	MenuBar menubar;
   	
   	ImageView imageV;
   	
   	List<Image> photoList = new ArrayList<>();
   	String photoString;
   	Label photoLabel;

   	
   	File d;
   	File[] fulllist;
   	
   	int spalte=0;
   	int zeile=0;

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
	        photoList.add(image);
	        
	        grid.add(imageV, spalte, zeile);
	        spalte++;

	        if(spalte>2){
	        	
	        	zeile++;
	        	spalte=0;
	        }
	        
	        imageV.setOnMouseClicked((MouseEvent e) -> {
	        	
	        	
	        	photoString = (((ImageView) e.getTarget()).getImage().impl_getUrl()) + "";
	            System.out.println(photoString); 
	            photoLabel = new Label(photoString);
	            infoBar.getChildren().clear();
	            infoBar.getChildren().add(photoLabel);	            
	           	            
	        });

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
        
        grid.setPrefSize(300, 500);
       
         
        // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
 
      
        scrollPane.setContent(grid);
 
        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
         infoBar  = new VBox(1);
         
     	BaseLayout.getChildren().addAll(root,UpperPane,grid,infoBar, scrollPane);
     	
     	
     	Scene scene1 = new Scene(BaseLayout, 300, 300);
     	
     	
     	//Bildraster
     	//Gitter erstellen
         grid.setPadding(new Insets(10, 10, 10, 10));
         grid.setVgap(10);
         grid.setHgap(10);
         
        UpperPane.setAlignment(Pos.CENTER);
        
        grid.setAlignment(Pos.CENTER);

        photoLabel = new Label();
        infoBar.getChildren().add(photoLabel);
        
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


