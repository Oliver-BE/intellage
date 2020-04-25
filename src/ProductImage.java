import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class ProductImage {

	//The individual image which will be used for drawing the images to the template image
	IndividualImage builder;
	
	//The number of photos in the folder of photos 
	int numPhotos;
	
	//The location of the selected folder
	File directory;

	public ProductImage() {
		this.directory = GraphicalUserInterface.selectedFolder;
		this.builder = new IndividualImage(this.directory + "/" + "0.jpg");
		this.numPhotos = FolderOfPhotos.numPhotos;
		buildImageUpdated("jpg", this.numPhotos, 100, 100);
		deleteTemporaryImages();
	}
	
	//Builds the final image using the renamed and resized images (Built using information about BufferedImage and Graphics2D from https://stackoverflow.com/questions/6575578/convert-a-graphics2d-to-an-image-or-bufferedimage)
	public void buildImageUpdated(String format, int numPhotos, int indWidth, int indHeight) {
				int [] reorderedPhotoNums = World.newPhotoLocations;
			try {
					BufferedImage original = ImageIO.read(builder.image);
					BufferedImage resized = new BufferedImage((indWidth * ((int) Math.sqrt(numPhotos))), (indHeight * ((int) Math.sqrt(numPhotos))), original.getType());
					Graphics2D g2 = resized.createGraphics();
					int photoNum = 0;
					for (int row = 0; row < Math.sqrt(numPhotos); row++) {
						for (int col = 0; col < Math.sqrt(numPhotos); col++) {
							if (photoNum != 0) {
								this.builder = new IndividualImage(this.directory + "/" + Integer.toString(reorderedPhotoNums[photoNum]) + ".jpg");
								original = ImageIO.read(builder.image);
							}
						photoNum++;
						g2.drawImage(original, row * indWidth, col * indHeight, indWidth, indHeight, null);
						}
					}
					g2.dispose();
					ImageIO.write(resized, format, new File(this.directory + "/finalImage.jpg"));
				}
				
				catch(IOException ex) {
					ex.printStackTrace();
				}
	}
	
	//This method deletes the images that were temporarily created for mapping to the template image
	public void deleteTemporaryImages() {
		for (int i = 0; i < numPhotos; i++) {
			Path tempImage = Paths.get(this.directory + "/" + Integer.toString(i) + ".jpg");
			try {
				Files.deleteIfExists(tempImage);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}