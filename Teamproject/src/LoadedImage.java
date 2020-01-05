
import java.io.File;
import java.nio.file.Path;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoadedImage{
	
	String name;
	String path;
	File file;
	double fileSize;
	Image image;
	protected ImageView imageV;
	String title;
	String bemerkung="";
	double width;
	double height;
	int imageCount;
	int zeile;
	int spalte;
	
	
	Image[] gitterimages;
	
	
	
	public void handle(MouseEvent event) {
		System.out.println("lol");
	}
	
	/** Konstruktor zum erstellen von Projekt Attributen
	 * 
	 * @param f
	 * @param imageCount
	 * @param spalte
	 * @param zeile
	 */
	
	LoadedImage(File f, int imageCount, int spalte, int zeile){
		this.name = f.getName();
		this.path = f.getPath();
		this.file = f;
		this.image = new Image("file:"+file.getPath());
		this.imageCount = imageCount;
		this.fileSize = f.length();
		this.imageV  = new ImageView(this.image);
		this.zeile = zeile;
		this.spalte = spalte;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		
	}
	
	/** Setter-Methode um Bemerkung zu ändern */
	
	public void setbemerkung(String bemerkung){
		this.bemerkung = bemerkung;
	}
	
	
	public ImageView getImageView(){
		return imageV;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getSpalte(){
		return spalte;
	}
	
	public int getZeile(){
		return zeile;
	}




}


	

	
	


