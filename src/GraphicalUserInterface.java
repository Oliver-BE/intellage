import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

//The outline for the GUI was created using WindowBuilder, an open source Eclipse add on from the Eclipse Foundation
public class GraphicalUserInterface extends JPanel
   implements ActionListener {
   JFileChooser chooser;
   String choosertitle; 
   static File selectedFolder, selectedBackgroundImage;
   JButton selectFolderButton, selectFileButton, btnGo;
   private final Action action = new SwingAction();
   static boolean goPressed, backgroundChosen, folderChosen;
   JLabel lblNewLabel, lblChooseASource;
   JFrame frame;
  
  //Much of the code in this constructor was automatically created by WindowBuilder
  public GraphicalUserInterface() {
    
    lblNewLabel = new JLabel("Intellage ");
    lblNewLabel.setFont(new Font("Big Caslon", Font.BOLD, 17));
    lblNewLabel.setBackground(new Color(0, 0, 0));
    
    lblChooseASource = new JLabel("Choose a Template Image");
    lblChooseASource.setFont(new Font("Big Caslon", Font.PLAIN, 13));
    lblChooseASource.setHorizontalAlignment(SwingConstants.RIGHT);
    
    btnGo = new JButton("GO");
    btnGo.addActionListener(this);
    btnGo.setFont(new Font("Big Caslon", Font.PLAIN, 13));
    
    selectFileButton = new JButton("...");
    selectFileButton.addActionListener(this);
    selectFileButton.setMaximumSize(new Dimension(40, 29));
    selectFileButton.setFont(new Font("Big Caslon", Font.PLAIN, 13));
    
    selectFolderButton = new JButton("...");
    selectFolderButton.addActionListener(this);
    selectFolderButton.setMaximumSize(new Dimension(40, 29));
    selectFolderButton.setFont(new Font("Big Caslon", Font.PLAIN, 13));
    
    JLabel lblChooseABackground = new JLabel("Choose a Source Folder");
    lblChooseABackground.setHorizontalAlignment(SwingConstants.RIGHT);
    lblChooseABackground.setFont(new Font("Big Caslon", Font.PLAIN, 13));
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
    	groupLayout.createParallelGroup(Alignment.LEADING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(16)
    					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
    						.addComponent(lblChooseASource)
    						.addComponent(lblChooseABackground)
    						.addComponent(lblNewLabel))
    					.addPreferredGap(ComponentPlacement.RELATED)
    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    						.addComponent(selectFolderButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
    						.addComponent(selectFileButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(55)
    					.addComponent(btnGo, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
    			.addContainerGap(229, Short.MAX_VALUE))
    );
    groupLayout.setVerticalGroup(
    	groupLayout.createParallelGroup(Alignment.LEADING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(18)
    			.addComponent(lblNewLabel)
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
    				.addComponent(lblChooseASource)
    				.addComponent(selectFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
    				.addComponent(selectFolderButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lblChooseABackground, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addComponent(btnGo)
    			.addGap(156))
    );
    setLayout(groupLayout);
   }
  
  // Where button clicking becomes an action
  public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
	//What happens if choose a source folder is pressed 
    if (src == selectFolderButton) {
    	chooser = new JFileChooser(); 
    	chooser.setCurrentDirectory(new java.io.File("."));
    	chooser.setDialogTitle(choosertitle);
    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	chooser.setAcceptAllFileFilterUsed(false); 
    	if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
    		System.out.println("Chosen folder is:" + chooser.getSelectedFile());
    		selectedFolder = chooser.getSelectedFile();
    		folderChosen = true;
    	}
    	else {
    		System.out.println("No Selection ");
    	}
    	
    }
    //What happens if select a background image is pressed
    else if(src == selectFileButton) {
    	chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"JPG Images", "jpg", "jpeg");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
        	System.out.println("Chosen template image is:" + chooser.getSelectedFile());
        	selectedBackgroundImage = chooser.getSelectedFile();
        	backgroundChosen = true;
          }
        else {
        	System.out.println("No Selection ");
        	}
        
	}
    //What happens if Go button is pressed
    else if(src == btnGo) {
    	if(!backgroundChosen || !folderChosen) {System.out.println("Please choose both a background image and a source folder.");}
    	else if (backgroundChosen && folderChosen) {goPressed = true;}
    }
  }
  //When called causes the GUI window to open and begins the initial GUI side of the program 
  public void openGUI() {
	  frame = new JFrame("");
		frame.addWindowListener(
   	 new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
        	}
      	}
    	);
  	frame.getContentPane().add(this,"Center");
  	frame.setSize(this.getPreferredSize());
  	frame.setTitle("Intellage");
  	goPressed = false;
  	backgroundChosen = false;
  	folderChosen = false;
  	frame.setVisible(true);
  	while(!goPressed) {System.out.print("");}
	this.setVisible(false);
  }
  
  //Displays the image "finalImage.jpg", located in the source folder, in the GUI window
  public void displayImage() {
	  frame.getContentPane().removeAll();
	  // Code for resizing and image adapted from: https://coderanch.com/t/331731/java/Resize-ImageIcon
	  ImageIcon image = new ImageIcon(selectedFolder + "/finalImage.jpg");
	  Image img = image.getImage();
	  Image newimg = img.getScaledInstance(700, 700, 0);
	  ImageIcon newIcon = new ImageIcon(newimg);
	  //End code borrowed from above source
	  JLabel label = new JLabel(newIcon);
      JScrollPane scrollPane = new JScrollPane(label);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      add(scrollPane, BorderLayout.CENTER);
      frame.setSize(800, 800);
      frame.getContentPane().add(label,"Center");
      frame.repaint();
      this.setVisible(true);
	  
  }
  //How to get height and width of a file adapted from https://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
  public Dimension getFinalDisplaySize(){
	  BufferedImage bimg = null;
	try {
		bimg = ImageIO.read(new File(selectedFolder + "/finalImage.jpg"));
	}
	catch (IOException e) {
		e.printStackTrace();
	}
      int width = bimg.getWidth();
      int height = bimg.getHeight();
      return new Dimension(width + 50, height + 50);
  }
  
  //Returns the PreferredDimensions (automatically added by window builder)
  public Dimension getPreferredSize(){
    return new Dimension(400, 200);
    }
	
  	//Something added by windowbuilder (Probably initializes actionPerformed?) 
  	private class SwingAction extends AbstractAction {
  		public SwingAction() {
  			putValue(NAME, "SwingAction");
  			putValue(SHORT_DESCRIPTION, "Some short description");
  		}
  		public void actionPerformed(ActionEvent e) {}
  	}
}
