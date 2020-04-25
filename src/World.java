public class World {

	// Final product image / output
	ProductImage product;

	// Inputed folder of photos
	FolderOfPhotos folder; 
	
	// Selected background image
	TemplateImage template;
	
	static int[] newPhotoLocations;
	
	//Calls everything involved in comparing images and creating the product image
	public World() {
		this.folder = new FolderOfPhotos();
		System.out.println("Background images loaded, template image is now loading...");
		this.template = new TemplateImage();
		System.out.print("Template image loaded, photos are now being mapped");
		newPhotoLocations = new int[TemplateImage.avgTemplateValues.length];
		for (int i = 0; i < newPhotoLocations.length; i++) {
			newPhotoLocations[folder.orderImageReturnNewLocation(i)] = i;
			if ((i % (newPhotoLocations.length / 10)) == 0) {
				System.out.print(".");
			}
		}
		System.out.println();
		System.out.println("New image is being assembled...");
		this.product = new ProductImage(); 
	}
}
