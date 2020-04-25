public class Main{
	public static void main(String[] args) {
		System.out.println("Intellage © 2018 Isaac Caruso and Oliver Baldwin Edwards. All Rights Reserved.");
		GraphicalUserInterface gui = new GraphicalUserInterface();
		System.out.println("Kindly use the GUI to select both a jpg template image and a source folder containing jpg images to be mapped.");
		gui.openGUI();
		System.out.println("Images loading...");
		World world = new World();
		System.out.println("Image completed. It will appear on the screen shortly and a copy will be saved your selected source folder under the name, finalImage.jpg.");
		gui.displayImage();
	}
}
