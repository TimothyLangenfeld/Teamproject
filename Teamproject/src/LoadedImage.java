
import java.io.File;
import java.nio.file.Path;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** class implements an image with information that can be displayed
 * 
 * @author tabea
 * @version 1.0
 *
 */

public class LoadedImage{
	
	private String name;
	private String path;
	private File file;
	private double fileSize;
	private Image image;
	private  ImageView imageV;
	private String title;
	private String bemerkung="";
	private double width;
	private double height;
	private int imageCount;
	private int zeile;
	private int spalte;
	
	
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
		
		this.imageVSettigs();
		
		
	}
	
	/** Setter-Method to change comment
	 * @param comment
	 * */
	
	public void setbemerkung(String comment){
		this.bemerkung = comment;
	}
	
	public String getBemerkung(){
		return bemerkung;
	}
	
	
	public ImageView getImageView(){
		return imageV;
	}
	
	public String getName(){
		return name;
	}
	
	public double getfileSize(){
		return fileSize;
	}
	
	public File getFile(){
		return file;
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
	
	public double getwidth(){
		return width;
	}
	
	public double getheight(){
		return height;
	}
	
	public void imageVSettigs(){
		
		this.imageV.setFitHeight(200);
		this.imageV.setPreserveRatio(true);
	}

	/** makes the determined ImageView bigger*/
	
	public void makebigger() {
		this.imageV.setFitHeight(205);
	}
	

}


	

	
	


