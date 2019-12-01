
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
// import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ErstesJavaFXProjekt extends Application {
	List<Image> fotoAlbum = new ArrayList<>();
	
    public static void main(String[] args) {
        launch(args);
    }
    Desktop desktop = Desktop.getDesktop();
    VBox vBox = new VBox();
	public int zeile=0;
	public int spalte=0;
    
 /*   public void start2(Stage secondaryStage) {
    	secondaryStage.setTitle("QiQi");
    	HBox box = new HBox();
    	secondaryStage.setScene(new Scene(box, 300, 250)); 
    }
*/
    @Override
    public void start(Stage primaryStage) {
    	GridPane root = new GridPane();
    	root.setPadding(new Insets(10, 10, 10, 10));
    	root.setVgap(8);
    	root.setHgap(9);
    	// root.setAlignment(Pos.CENTER);
    	//Desktop
    	//Der Filechooser wird direkt am Anfang eingebaut

    	FileChooser fileChooser = new FileChooser();
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	//Die Anzahl der verwendeten Fenster werden hier angegeben mit Titel und Rangordnung
        primaryStage.setTitle("Test");
   
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Qi");
        HBox box2 = new HBox();
    	secondaryStage.setScene(new Scene(box2, 300, 250)); 
        
        Stage thirdStage = new Stage();
        thirdStage.setTitle("Fotobuch");    	
        
    	//Instanzen der Klasse Button wird erzeugt
        Button btn = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        Button btn4 = new Button();
        /*
        //Farbschema kann heirdurch parametisiert werden
        //Button 1 wird konfiguriert
        Color green = new Color(0,1,0,0.8);
        BackgroundFill background_fill = new BackgroundFill(green,  
               CornerRadii.EMPTY, Insets.EMPTY); 
        Background background = new Background(background_fill);
        btn.setBackground(background);
        //Button 1 wird konfiguriert
        Color red = new Color(1,0,0,0.8);
        BackgroundFill background_fill2 = new BackgroundFill(red,  
        		CornerRadii.EMPTY, Insets.EMPTY); 
        Background background2 = new Background(background_fill2);
        btn2.setBackground(background2);
        //Button 3 wird konfiguriert
        BackgroundFill background_fill3 = new BackgroundFill(Color.BLUE,  
        		CornerRadii.EMPTY, Insets.EMPTY); 
        Background background3 = new Background(background_fill3);
        btn3.setBackground(background3);
        btn3.setText("Stay Free to Play");
        //Button 4 wird konfiguriert
        BackgroundFill background_fill4 = new BackgroundFill(Color.BLUE,  
        		CornerRadii.EMPTY, Insets.EMPTY); 
        Background background4 = new Background(background_fill4);
        btn4.setText("Refund");
        btn4.setBackground(background4);
        */
        //Buttons bekommen noch einen Text der vom User gelesen werden kann
        btn.setText("GIF");
        btn2.setText("Select Picture");
        btn3.setText("Select Multiple Pictures");
        btn4.setText("Fotobuch");
        //Was genau passiert wenn der Button gedrückt wird wird an dieser Stelle bestimmt
        //Lambda ausdrücke vereinfachen den Aufwand und liefern das gleiche Ergebnis, siehe btn und btn2!

        btn2.setOnAction(new EventHandler<ActionEvent>() { 	 
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(primaryStage);
                Image image = new Image(file.toURI().toString());
                if (file != null) {
                    try {
                    	ImageView imageView = new ImageView(image);
                    	imageView.setFitWidth(100);
                    	imageView.setFitHeight(100);
                        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
                                null), "png",file);
                        root.add(imageView,zeile,spalte);
                        zeile++;
                        if(zeile > 5) {
                        	  spalte++;
                        	  zeile = 0;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(
                            ErstesJavaFXProjekt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });   
        btn3.setOnAction(new EventHandler<ActionEvent>() { // ActionHandler ab sofort mit btn1.setOnAction(e->{...});
            @Override
            public void handle(final ActionEvent e) {
                File file = directoryChooser.showDialog(primaryStage);
                Image image = new Image(file.toURI().toString());
                if (file != null) {
                    try {
                    	ImageView imageView = new ImageView(image);
                        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
                                null), "png",file);
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(
                            ErstesJavaFXProjekt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });   
        
        //directory chooser File.listFile();
        //Filter
        /*
        Image image1 = new Image("file:C:\\Users\\Timot\\eclipse-workspace\\Spielerei\\src\\Nur Bilder\\Index.jpg");
        Image image2 = new Image("file:C:\\Users\\Timot\\eclipse-workspace\\Spielerei\\src\\Nur Bilder\\Index1.jpg");
        Image image3 = new Image("file:C:\\Users\\Timot\\eclipse-workspace\\Spielerei\\src\\Nur Bilder\\Index3.jpg");
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        
      
        GridPane root = new GridPane();
        root.add(image2,0,0);
        root.add(imageView2,1,0);
        root.add(imageView3,2,0);
        root.setAlignment(Pos.CENTER);
        thirdStage.setScene(new Scene(root, 1000,300));
        */
        
        btn4.setOnAction(e->{ 
        	thirdStage.show();
        });
       //Die Anordnung der Knöpfe innerhalb des ersten Fensters werden an dieser Stelle festgelegt
       // VBox box = new VBox();  //VerticalBox reiht alles automatisch in einer vertikalen Zeile
       HBox box = new HBox(); // HorizontalBox reiht alles horizontal auf
       box.getChildren().add(btn);
       box.getChildren().add(btn2);
       box.getChildren().add(btn3);
       box.getChildren().add(btn4);
       box.setAlignment(Pos.CENTER);	// die Box wird in der Mitte des Bildschirms zentriert
       
      //  Grid pane bietet eine Gitternetz an um an bestimmten Stellen Elemente anzeigen zu lassen
      //  GridPane root = new GridPane();
      //  root.add(btn,0,0);
      //  root.add(btn2,1,0);
      //  root.setAlignment(Pos.CENTER); //root ist der Startwert für die jeweiligen Elemente, hier mittige Ausrichtung
       //Damit man auch das Fenster sieht un es nicht nur im Hintergrund versauert wird die Fenstergröße noch angegeben und die Ausgabe angefordert
       primaryStage.setScene(new Scene(box, 300, 250)); 
        primaryStage.show();
        thirdStage.setScene(new Scene(root, 1000,300));
    }
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                ErstesJavaFXProjekt.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}
