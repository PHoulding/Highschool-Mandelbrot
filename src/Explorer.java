import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 2013 ICS3U Fractal Explorer Project Schedual:
 * 
 * Explorer 1: Shell (Due: Saturday May 11) Explorer 2: Plot (Due Tuesday May
 * 14) Explorer 3: Mandelbrot (Due: Thursday May 16) Explorer 4: Palette (Due:
 * Tuesday May 21) Explorer 5: Animation (Due: Monday May 27)
 * 
 * @author C. D'Arcy
 */

public class Explorer extends JFrame implements ActionListener {

	// Establish default application attributes
	static int frameWidth = 600;
	static int menuHeight = 21;
	static int frameHeight = frameWidth + menuHeight;
	static String frameTitleBase = "ICS3U Fractal Explorer";
	static String frameTitle = frameTitleBase;
	static String saveFormat = "gif";
	static boolean save = false;
	static boolean animate = true;
	static final char commentTag = '#';
	static String script;
	static String[] fields;

	static Palette palette;
	static Fractal fractal;
	static Plot plot;
	BufferStrategy strategy;

	public Explorer() {
		super(frameTitleBase);
		this.setSize(frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setJMenuBar(this.makeMenuBar());
		this.setLocationRelativeTo(null);
		plot = new Plot(this);
		showPlot();
	}

	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		addMenuItem(menu, "Open", 'O');
		menu.addSeparator();
		addMenuItem(menu, "Exit", 'X');
		menu = new JMenu("Help");
		menuBar.add(menu);
		addMenuItem(menu, "About", 'A');
		return menuBar;
	}

	private void addMenuItem(JMenu menu, String item, char shortcut) {
		JMenuItem mi = new JMenuItem(item, shortcut);
		mi.setActionCommand(item);
		menu.add(mi);
		mi.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if ("Open".equals(command))
			try {
				if (openPlot() == JFileChooser.APPROVE_OPTION) {
					this.setSize(frameWidth, frameHeight);
					this.setLocationRelativeTo(null);
					plot = new Plot(this, fractal, saveFormat, save, animate);
					frameTitle = frameTitleBase + ":" + fractal.getFilename();
					showPlot();
					fractal.setDelta(fractal.getDelta()*0.5);
				 	plot.repaint();
					showPlot();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		else if ("Exit".equals(command)) {
			dispose();
			System.exit(0);
		} else if ("About".equals(command)) {
			this.setVisible(false);
			frameWidth = 600;
			frameHeight = frameWidth + menuHeight;
			this.setLocationRelativeTo(null);
			plot.setWelcome(frameWidth);
			frameTitle = frameTitleBase + ": About";
			this.showPlot();
		}

	}

	private int openPlot() throws FileNotFoundException{
		String filePath = new File("").getAbsolutePath();
		JFileChooser chooser = new JFileChooser(filePath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Explorer", "exp");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()){
				String line = scanner.nextLine();
				if(line.charAt(0) != commentTag){
					fields = line.split("\t");
					String className = fields[0];
					if(className.equalsIgnoreCase("Frame")){
						frameWidth = Integer.parseInt(fields[1]);
						frameHeight = frameWidth+menuHeight;
						frameTitle = fields[2];
						saveFormat = fields[3];
						save = Boolean.parseBoolean(fields[4]);
						animate = Boolean.parseBoolean(fields[5]);
					}else if(className.equalsIgnoreCase("Fractal")){
						if("Mandelbrot".equalsIgnoreCase(fields[1])){
							fractal = new Mandelbrot(fields[2], fields[3], fields[4], fields[5], palette, file.getName());
						}
						if("Julia".equalsIgnoreCase(fields[1])){
							fractal = new Julia(fields[2], fields[3], fields[4], fields[5], palette, file.getName());
						}
					}else if("Palette".equalsIgnoreCase(className)){
						palette = new Palette(fields[1], fields[2], fields[3]);
					}
				}
			}
		}else
			System.out.println("Open cancelled.");
		
		return returnVal;
	}

	public void showPlot() {
		this.setTitle(frameTitle);
		this.setSize(frameWidth, frameHeight);
		this.setResizable(false);
		this.getContentPane().removeAll();
		this.getContentPane().add(plot);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Explorer();
	}
}
