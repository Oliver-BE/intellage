import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.ColorModel;
import java.lang.Object;

public class IndividualImage {

	public int xPos, yPos, height, width, numPixels;
	public File image;
	ColorModel getValues;
	
	//Multiple constructors allow for an instance of individual image to be created either with a string or file inputed containing the image's location
	public IndividualImage(String fileLocation) {
		
		this.image = new File(fileLocation);
		this.setImageSize();			
	}

	public IndividualImage(File filePath) {

		this.image = filePath;
		this.setImageSize();

	}
	
	//Given an initial x and y location and a desired width and height of a region, this method will return the average HSV value for the region of this individual image instance
	public int[] getAverageHSV(int initialX, int initialY, int tempWidth, int tempHeight) {
		int totalH = 0;
		int totalS = 0;
		int totalV = 0;
		numPixels = tempWidth * tempHeight;
		int [][] tempHSV = this.getHSVArray(initialX, initialY, tempWidth, tempHeight);
		
		for (int i = 0; i < this.numPixels; i ++) {
			totalH = totalH + tempHSV[i][0];
			totalS = totalS + tempHSV[i][1];
			totalV = totalV + tempHSV[i][2];
		}
		int[] avgHSV = new int[3];
		avgHSV[0] = totalH / this.numPixels;
		avgHSV[1] = totalS / this.numPixels;
		avgHSV[2] = totalV / this.numPixels;
		
		return avgHSV;
	}
	
	//This method will return the average HSV value for the entire individual image instance
	public int[] getAverageHSV() {
		int totalH = 0;
		int totalS = 0;
		int totalV = 0;
		numPixels = this.width * this.height;
		int [][] tempHSV = this.getHSVArray();
		
		for (int i = 0; i < this.numPixels; i ++) {
			totalH = totalH + tempHSV[i][0];
			totalS = totalS + tempHSV[i][1];
			totalV = totalV + tempHSV[i][2];
		}
		int[] avgHSV = new int[3];
		avgHSV[0] = totalH / this.numPixels;
		avgHSV[1] = totalS / this.numPixels;
		avgHSV[2] = totalV / this.numPixels;
		
		return avgHSV;
	}
	
	//Creates an array of HSV values for each pixel in a region of this instance of individual image given starting coordinates and a width and height
	public int[][] getHSVArray(int initialX, int initialY, int tempWidth, int tempHeight){
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(this.image);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		numPixels = tempWidth * tempHeight;
		int[][] HSVValues = new int[this.numPixels][3];
		int currentPixel = 0;
		xPos = initialX;
		yPos = initialY;
		while (xPos < initialX + tempWidth) {
			while (yPos < initialY + tempHeight) {
				HSVValues[currentPixel] = this.getHSV(tempImage.getRGB(xPos, yPos));
				currentPixel++;
				yPos++;
			}
			yPos = initialY;
			xPos++;
		}
		return HSVValues;
	}
	
	//Creates an array of HSV values for each pixel in the entire image associated with this instance of individual image
	public int[][] getHSVArray(){
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(this.image);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		numPixels = this.width * this.height;
		int[][] HSVValues = new int[this.numPixels][];
		HSVValues[0] = new int[3];
		int currentPixel = 0;
		int xPos = 0;
		int yPos = 0;
		int initialY = yPos;
		int initialX = xPos;
		while (xPos < initialX + this.width) {
			while (yPos < initialY + this.height) {
				HSVValues[currentPixel] = this.getHSV(tempImage.getRGB(xPos, yPos));
				currentPixel++;
				yPos++;
			}
			yPos = initialY;
			xPos++;
		}
		return HSVValues;
	}
	
	//Takes in an integer rgbValue and returns an integer array of length 3 containing an HSV value.
	//Adapted from a Python script on https://www.geeksforgeeks.org/program-change-rgb-color-model-hsv-color-model/
	public int[] getHSV(int rgbValue){
		int[] hsv = new int[3];
		double h = 0;
		double s = 0;
		double v = 0;
		Color specificPixel = new Color(rgbValue);
		double r = specificPixel.getRed();
		double g = specificPixel.getGreen();
		double b = specificPixel.getBlue();
		r = r / 255.0;
		g = g / 255.0;
		b = b / 255.0;
		double cMax = Math.max(Math.max(r, g), b);
		double cMin = Math.min(Math.min(r, g), b);
		double diff = cMax - cMin;
		if (cMax == cMin) {
			h = 0;
		}
		else if (cMax == r) {
			h = (60 * ((g - b) / diff) + 360) % 360;
		}
		else if (cMax == g) {
			h = (60 * ((b - r) / diff) + 120) % 360;
		}
		else if (cMax == b) {
			h = (60 * ((r - g) / diff) + 240) % 360;
		}
		if (cMax == 0) {
			s = 0;
		}
		else {
			s = (diff / cMax) * 100;
		}
		v = cMax * 100;
		
		hsv[0] = (int) h;
		hsv[1] = (int) s;
		hsv[2] = (int) v;
		
		return hsv;
	}
	
	
	//How to get height and width of a file adapted from https://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
	public void setImageSize(){
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(this.image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	      this.width = bimg.getWidth();
	      this.height = bimg.getHeight();
	}
	
	//Resizes an image given the File, the desired width and height after and the format of the image.
	//Adapted from: https://www.youtube.com/watch?v=YM02Q3s9pbY
	public void resizeImage(File resizedImage, int widthAfter, int heightAfter, String format) {
		try {
			BufferedImage original = ImageIO.read(this.image);
			BufferedImage resized = new BufferedImage(widthAfter, heightAfter, original.getType());
			Graphics2D g2 = resized.createGraphics();
			g2.drawImage(original, 0, 0, widthAfter, heightAfter, null);
			g2.dispose();
			ImageIO.write(resized, format, resizedImage);
			width = widthAfter;
			height = heightAfter;
			this.image = resizedImage;
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}