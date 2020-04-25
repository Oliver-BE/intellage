import java.io.File;
import java.lang.String;
import java.util.Arrays;

// a class that contains an array of IndividualImages from the user-inputted folder
public class FolderOfPhotos {

	// array of inputted photos
	IndividualImage photoArray[];
	
	// path to selected folder
	File directory;

	// number of photos in selected folder
	static int numPhotos;

	// boolean to make sure we only check each photo once
	boolean [] alreadyMatchedSections; 
	
	// array to hold avg H, S, and V values for each photo
	int avgValues[][];

	// boolean to check that the numPhotos is a perfect square
	boolean perfectSquare; 

	
	public FolderOfPhotos() {
		
		// the folder initially has zero photos
		numPhotos = 0;
		
		// get the folder directory from the GUI
		this.directory = GraphicalUserInterface.selectedFolder;	

		numPhotos = initializeNumPhotos();
	
		// set the length of the array based on our new numPhotos  
		photoArray = new IndividualImage[numPhotos];
		
		// go back through all of the images in the inputted folder and fill our photoArray with them
		fillPhotoArray();
			 
		// boolean array to keep track of which photos in the array were already checked
		alreadyMatchedSections = new boolean [numPhotos];
		Arrays.fill(alreadyMatchedSections, false);
		
		// for each individual image in the photo array resize and rename it 
		resizePhotoArray();
		
		// fill in average H, S, and V values for each resized image
		fillAvgHSVValues();
		
	}
		
	/* Method to get the number of photos in the selected folder.
	Used https://www.daniweb.com/programming/software-development/threads/341929/how-to-read-images-from-a-folder#
	to learn how to list all files in a directory within a for loop. */
	public int initializeNumPhotos() {
		
		for(final File imgFile : directory.listFiles()) {
			
			/* Used EboMike's answer from here: https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
			to check to make sure the only files we count are ones that are jpg or jpegs. */
			String extension = "";
			String tempFile = imgFile.getName();

			int i = tempFile.lastIndexOf('.');
			if (i > 0) {
    			extension = tempFile.substring(i+1);
			}
			
			if(extension.equals("jpeg") || extension.equals("jpg")) {
				// if the file is a jpg then increment numPhotos
  				numPhotos++;
  			}		
		}

		// make sure that numPhotos is a perfect square
		perfectSquare = isSquare(numPhotos);
		
		// while we don't have a perfect square decrement numPhotos until we do
		while(!perfectSquare) {
			numPhotos--;
			perfectSquare = isSquare(numPhotos);
		}

		return numPhotos;
	}

	// method that goes back through all of the images in the inputted folder and fills our photoArray with them
	public void fillPhotoArray() {
		int counter = 0; 
		for(final File imgFile : directory.listFiles()) {
			// this accounts for the user not inputting a perfect square for numPhotos initially
  			if(counter >= numPhotos) {
  				break; 
  			}
  			else{
  			String extension = "";
			String tempFile = imgFile.getName();
			
			int i = tempFile.lastIndexOf('.');
			if (i > 0) {
    			extension = tempFile.substring(i+1);
			}

			if(extension.equals("jpeg") || extension.equals("jpg")) {
  				photoArray[counter] = new IndividualImage(imgFile);
  				counter++;
  			}			
		
			}
		}		 
	}

	// method to resize each photo in the inputted folder of photos
	public void resizePhotoArray() {
		for(int i = 0; i < numPhotos; i++) {
			this.photoArray[i].resizeImage(new File(GraphicalUserInterface.selectedFolder + "/" + Integer.toString(i) + ".jpg"), 100, 100, "jpg");
		}
	}

	/* method to fill the average HSV values for each photo in the user-inputted folder
	in our avgValues 2D array */
	public void fillAvgHSVValues() {
		// create array to hold HSV values for each resized image
  		this.avgValues = new int[numPhotos][3];

  		// width and height of each resized image
  		int width = 100; 
		int height = 100; 

		// length of the final photo grid
		int length = (int)Math.sqrt(numPhotos);

		int photoCounter = 0;
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				
				// set the image size for each photo in photoArray
				photoArray[photoCounter].setImageSize();

				// get the average HSV values for each photo in photoArray
				int[] temp = new int[3];	
				temp = photoArray[photoCounter].getAverageHSV();
			
				// store the H, S, and V values in their respective locations in avgValues 2D array				
				avgValues[photoCounter][0] = temp[0];	
				avgValues[photoCounter][1] = temp[1];
				avgValues[photoCounter][2] = temp[2];
					
				photoCounter++;
			}
		}
	}

	// method to check if an integer is a perfect square
	// got this whole method from https://algorithms.tutorialhorizon.com/check-whether-the-given-number-is-a-perfect-square/
	public static boolean isSquare(int n){
        if(n==0 || n==1)
            return true;
        for (int i = 0; i <n/2 ; i++) {
            int x = i*i;
            if(x==n)
                return true;
            else if (n<x)
                return false;
            else
                continue;
        }
        return false;
    }

	/* Method to reorder the folder of photos into the order that will match
	up with the selected template image based on matching H, S, and V values. 
	Will return the integer location in the template image where the current
	photo should go. */
	public int orderImageReturnNewLocation(int photoInGrid) { 
		int bestMatch, lowestDiff, currentDiff;
		lowestDiff = 10000;
		bestMatch = 0;
		
		for (int i = 0; i < TemplateImage.avgTemplateValues.length; i++) {
			currentDiff = compareImages(i, photoInGrid);
			
			if ((currentDiff < lowestDiff) && (!alreadyMatchedSections[i])) {  
				bestMatch = i;
				lowestDiff = currentDiff;
			}
		}
	
		alreadyMatchedSections[bestMatch] = true;
	
		return bestMatch;
	}
		
	
	/* Method to compare the H, S, and V values from the template image and inputted folder of photos.
	Computes and returns how far away the H, S, and V values at the passed in location are from each other. */
	public int compareImages(int locationInTemplatePhoto, int newPhoto) {
		int hComp, sComp, vComp;
		hComp = Math.abs(TemplateImage.avgTemplateValues[locationInTemplatePhoto][0] - this.avgValues[newPhoto][0]);
		sComp = Math.abs(TemplateImage.avgTemplateValues[locationInTemplatePhoto][1] - this.avgValues[newPhoto][1]);
		vComp = Math.abs(TemplateImage.avgTemplateValues[locationInTemplatePhoto][2] - this.avgValues[newPhoto][2]);
		int totalDiff = (hComp / 3) + sComp + vComp;
		return totalDiff;
		
	}
}