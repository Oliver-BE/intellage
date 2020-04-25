public class TemplateImage {
	static int width, height, imagePieces; 
	static IndividualImage backgroundImage;

	public static int avgTemplateValues[][]; 

	public TemplateImage() {
		this.imagePieces = FolderOfPhotos.numPhotos;
		this.avgTemplateValues = new int[imagePieces][3];
		this.backgroundImage = new IndividualImage(GraphicalUserInterface.selectedBackgroundImage);
		this.backgroundImage.setImageSize();

		width = backgroundImage.width; 
		height = backgroundImage.height; 

		fillAvgTemplateValues();
	}

	// a method to fill in the average H, S, and V values for each chunk of backgroundImage
	public void fillAvgTemplateValues() {
		//photoCounter keeps a record of the current location in the array wherein an average HSV value will be assigned
		int photoCounter = 0;
		//The length (number of images from source folder) that will be inserted into each row and each column 
		int length = (int)Math.sqrt(imagePieces);
		
		//Creates the grid of average HSV values depending on where the top left coordinate of a subsection is 
		for(int r = 0; r < length; r++) {
			for(int c = 0; c < length; c++) {
				int[] temp = new int[3];
				
				//If statement chain is present because if the row or column is 0, it assigns parameters manually to avoid dividing by 0
				if(r == 0 && c == 0) {
					temp = backgroundImage.getAverageHSV(0, 0, width/length, height/length);
				}
				else if(r == 0 && c != 0) {
					temp = backgroundImage.getAverageHSV(0, (int)((height * c) / length), width/length, height/length);
				}
				else if(r != 0 && c == 0) {
					temp = backgroundImage.getAverageHSV((int)((width * r) / length), 0, width/length, height/length);
				}
				else {
					temp = backgroundImage.getAverageHSV((int)((width * r) / length), (int)((height * c) / length), width / length, height / length);
				}
				
				//To get H value
				avgTemplateValues[photoCounter][0] = temp[0];
				//To get S value
				avgTemplateValues[photoCounter][1] = temp[1];
				//To get V value
				avgTemplateValues[photoCounter][2] = temp[2];
				photoCounter++;
			}
		}
	}
}