
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
	ImageView imageV;
	String title;
	String bemerkung=null;
	int imageCount;
	int zeile;
	int spalte;
	
	
	Image[] gitterimages;
	
	
	
	public void handle(MouseEvent event) {
		System.out.println("lol");
	}
	
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
		
	}
	
	LoadedImage(String bemerkung){
		this.bemerkung = bemerkung;
	}
	


}


	

	
	


