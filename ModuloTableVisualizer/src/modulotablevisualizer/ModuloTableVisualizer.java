/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulotablevisualizer;

/**
 *
 * @author erikhauck
 */
/*
public class ModuloTableVisualizer {

    /**
     * @param args the command line arguments
     */
/*
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

*/

//This program reads in a series of integers and converts them into RGB values
//then displays the results in a new window, and allows the user to save as .png

//Limitations:
////Max modulo value = 1000
////unknown limitation on size of .png

//Author:
////Erik Hauck
////Dec. 6, 2014 (4 am)


import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.JList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class ModuloTableVisualizer extends Canvas{
	private static final long serialVersionUID = -2000191915665900448L;
	int[][] tableArray = new int[1000][1000];
    int[][] tableAdj = new int[1000][1000];
    Color[][] color = new Color[1000][1000];
    int lineCount = 0;
    int colCount = 0;
    int largest = 0;
    int scale = 1;
    int disp = 0;
    int randomizer = 1;
    int sleeper = 0;
    int numSquare = 1;
    int numReps = 1;
    int countFixer = 1;
    JFrame pixelFrame = new JFrame();
	PixelCanvas pixelCanvas = new PixelCanvas();
	JLabel lblDir = new JLabel("please select a directory containing .mod files");
	JLabel lblTableval = new JLabel("none");

    boolean doRandomizer = false;
    boolean firstRoll = true;
    Random rand = new Random();
    private JFrame frame;
	private JTextField textField;
	File[] listOfFiles;
	JScrollPane scrollPane = new JScrollPane();
	@SuppressWarnings("rawtypes")
	JList list = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuloTableVisualizer window = new ModuloTableVisualizer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ModuloTableVisualizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 816, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		mnNewMenu.add(mntmOpen);
		
		JMenuItem mntmSaveImage = new JMenuItem("Save Image As..");
		mntmSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCanvas(pixelCanvas);
			}
		});
		mnNewMenu.add(mntmSaveImage);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmClose);
		
		JButton btnOpenModuloTable = new JButton("Open Modulo Table");
		btnOpenModuloTable.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnOpenModuloTable.setIcon(new ImageIcon(ModuloTableVisualizer.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		btnOpenModuloTable.setBounds(43, 222, 178, 29);
		btnOpenModuloTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnOpenModuloTable);
		
		JButton btnSaveImage = new JButton("Save Image");
		btnSaveImage.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnSaveImage.setIcon(new ImageIcon(ModuloTableVisualizer.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		btnSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCanvas(pixelCanvas);
			}
		});
		btnSaveImage.setBounds(233, 222, 178, 29);
		frame.getContentPane().add(btnSaveImage);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 438, 216);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Pixel Settings", null, panel, null);
		panel.setLayout(null);
		
		JRadioButton rdbtnRandomizer = new JRadioButton("Randomize Colors");
		rdbtnRandomizer.setBounds(0, 1, 172, 33);
		panel.add(rdbtnRandomizer);
		
		textField = new JTextField();
		textField.setBounds(89, 46, 44, 28);
		panel.add(textField);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			      String textFieldValue = textField.getText();
			      try
			      {
			    	  numSquare = Integer.parseInt(textFieldValue);
			      }
			      catch(Exception name)
			      {
			    	  numSquare = 1;
			      }
			      if(numSquare == 0)numSquare++;
				  numReps = (int) Math.pow(2, numSquare);
				  ResizeAndRepaint();
			}
		});
		textField.setText("1");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Repetition");
		lblNewLabel.setBounds(10, 52, 64, 16);
		panel.add(lblNewLabel);
		JSlider slider = new JSlider();
		slider.setBounds(10, 112, 231, 52);
		panel.add(slider);
		slider.setMajorTickSpacing(5);
		slider.setBackground(Color.LIGHT_GRAY);
		slider.setPaintLabels(true);
		slider.setMaximum(20);
		slider.setValue(1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			    scale = slider.getValue();
			    if(scale == 0)scale = 1;
			    ResizeAndRepaint();
			}
		});
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		
		JLabel lblScale = new JLabel("Scale Multiplier");
		lblScale.setBounds(10, 86, 123, 33);
		panel.add(lblScale);
		rdbtnRandomizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(doRandomizer)doRandomizer = false;
				else 
					{
						doRandomizer = true;
					}
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Modulo Tables", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnSetDirectory = new JButton("Set Directory");
		btnSetDirectory.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			      File folder= chooser.getSelectedFile();
		          listOfFiles=folder.listFiles(new TextFileFilter());
		  		  list = new JList(listOfFiles);
		  		  if(list.getModel().getSize() > 0)		lblDir.setText("");
		  		  scrollPane.setViewportView(list);
			    }
			}
			
		});
		btnSetDirectory.setBounds(294, 5, 117, 29);
		panel_1.add(btnSetDirectory);

		lblDir.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblDir.setBounds(6, 9, 289, 16);
		panel_1.add(lblDir);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(-32, 27, 485, 29);
		panel_1.add(separator);
		
		scrollPane.setBounds(6, 37, 289, 127);
		scrollPane.setViewportView(list);

		panel_1.add(scrollPane);
		
		JButton btnOpenSelected = new JButton("Open Selected");
		btnOpenSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParseFile((File) list.getSelectedValue());
			}
		});
		btnOpenSelected.setBounds(294, 85, 117, 29);
		panel_1.add(btnOpenSelected);
		
		//create listeners to refresh image on click/spacebar
		pixelCanvas.addMouseListener(new MouseListener(){
        public void mouseClicked(MouseEvent e){
        	pixelCanvas.repaint();
        }
        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}
		});
		
		pixelCanvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if(e.getKeyCode()==KeyEvent.VK_SPACE){
		              pixelCanvas.repaint();
		         }
			}
		});
		pixelCanvas.setBounds(450, 10, 10, 10);
		frame.getContentPane().add(pixelCanvas);
		
		JLabel lblCurrentTable = new JLabel("Current Table: ");
		lblCurrentTable.setBounds(43, 257, 93, 16);
		frame.getContentPane().add(lblCurrentTable);
		
		lblTableval.setBounds(148, 257, 61, 16);
		frame.getContentPane().add(lblTableval);
		
	}
	//open and parse the file
	private void openFile()
	{
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ParseFile(file);
        } 	
	}
	public static void saveCanvas(Canvas canvas) {
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String ext="png";
		    File fileToSave = fileChooser.getSelectedFile();
		    BufferedImage image=new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);

			Graphics2D g2=(Graphics2D)image.getGraphics();
			canvas.paint(g2);
			try {
				ImageIO.write(image, ext, new File(fileToSave.getAbsolutePath()+"."+ext));
			} catch (Exception e) {
				System.out.println("error saving file");
			}
		}
	}
	public void ResizeAndRepaint(){
		countFixer = 1;
		if(lineCount > colCount)
		{
			//factor ratio to prevent black bars from non-square shape
			countFixer = lineCount/colCount;
		}
        pixelCanvas.setBounds(450,10,(colCount*scale*numSquare), numSquare*largest*scale);

	}
	public void ParseFile(File file)
	{
        try {
		//parse the file.
		largest = 0;
		lineCount = 0;
		for(int i = 0; i < 1000; i++)
		{
			for(int j = 0; j < 1000; j++)
			{
				tableArray[i][j] = 0;
				tableAdj[i][j] = 0;
			}
		}

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        boolean broken = false;
        while ((line = br.readLine()) != null) {
        	if ( line.trim().length() == 0 ) continue; 
			colCount = 0;
        	String[] crap = {"(", ")", ",", ";"};
        	for (String replace : crap) {
        	    line = line.replace(replace, " ").trim();
        	}
        	// This replaces any multiple spaces with a single space
        	while (line.contains("  ")) {
        	    line = line.replace("  ", " ");
        	}
        	String[] values = line.split(" ");
        	if(line.equals(""))continue;
        	//convert each line to integers
        	for (int index = 0; index < values.length; index++) {
        		try{
        	    tableArray[index][lineCount] = Integer.parseInt(values[index].trim());
        		}
        		catch (Exception e)
        		{
        			broken = true;
        	        JOptionPane.showMessageDialog(null, "please open a properly formatted .mod file", "" + "", JOptionPane.INFORMATION_MESSAGE);
        			break;
        		}
        	    if(tableArray[index][lineCount] > largest)largest = tableArray[index][lineCount];
        	}
        	if(broken)break;
    	    colCount = values.length;
        	lineCount++;
        }
			br.close();
		} catch (IOException e1) {
	        JOptionPane.showMessageDialog(null, "please open a properly formatted .mod file", "" + "", JOptionPane.INFORMATION_MESSAGE);

		}
        //tableLoc
        lblTableval.setText(Integer.toString(colCount+1));
        //convert to RGB array
        for(int i = 0; i < colCount; i++)
        {
        	for(int j = 0; j < largest; j++)
        	{
        		tableAdj[i][j] = (((tableArray[i][j]) * (16777215)) / (largest));
        	}
        }
        firstRoll = true;
        //draw to pixel frame
        pixelCanvas.setBounds(450,10,(colCount*scale*numSquare), numSquare*largest*scale);
        pixelCanvas.repaint();        
        
	}
	public void AssignColors()
	{
        for(int x = 0; x < colCount; x++) {
            for(int y = 0; y < largest; y++) {
            	color[x][y] = new Color(tableAdj[x][y]*randomizer%16777215);
            }
        }
	}
	
	public class PixelCanvas extends Canvas {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2039876739136671990L;

		@Override
	    public void paint(Graphics g) {
	       
			if(doRandomizer || firstRoll) 
			{
				firstRoll = false;
				randomizer = rand.nextInt();
				AssignColors();
			}
			
			
			//TODO: Fix black bars on saveCanvas() calls and fix skewing by one pixel in preview
			
			//draw the image once
//			if(numSquare == 1)
//			{
//				for(int x = 0; x < (colCount*scale); x=x+scale) {
//		            for(int y = 0; y < largest*scale; y=y+scale) {
//		                g.setColor(color[x/scale][y/scale]);
//						g.fillRect(x, y, x, y);
//		            }
//		        }
//			}
			//duplicate the image n^2 times
			//else
			//{
		        for(int x = 0; x < (colCount*scale); x+=scale) {
		            for(int y = 0; y < largest*scale; y+=scale) {
		                g.setColor(color[x/scale][y/scale]);
            			//repeat current pixel n^2 times
	            		for(int gr = 0; gr < numSquare; gr++){
	            			//fill each row
        					for(int gc = 0; gc < numSquare; gc++)
        					{
        						//fill each column
        						g.fillRect(x+gr*colCount*scale, y+gc*largest*scale, (x)/(scale*colCount)+scale, (y/(scale*largest))+scale);
        					}
	            		}       		
		            }   
		        }
			//}
	    }

	}
}
class TextFileFilter implements FileFilter {
    public boolean accept(File file) {
        String name=file.getName();
        return name.length()<28&&name.endsWith(".mod");
        }
    }
