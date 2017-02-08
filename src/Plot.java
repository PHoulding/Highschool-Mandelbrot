import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plot extends JPanel {

	private JFrame frame;
	private Fractal fractal;
	private String saveFormat;
	private boolean save;

	private int fileNumber = 0;
	private int dimension;
	private double zoomFactor = 0.1;

	private BufferedImage bi;
	private boolean animate;

	public Plot(JFrame frame) {
		super();
		this.frame = frame;
		this.dimension = frame.getWidth();
		bi = this.getAbout();
	}

	public Plot(Explorer frame, Fractal fractal, String saveFormat,
			boolean save, boolean animate) {
		this.frame = frame;
		this.fractal = fractal;
		this.saveFormat = saveFormat;
		this.save = save;
		this.dimension = frame.getWidth();
		fractal.setDimension(dimension);
		bi = this.getImage();
		if(animate)
			animate();
	}

	

	public Plot(JFrame frame, Fractal fractal, String saveFormat, boolean save) {
		this.frame = frame;
		this.fractal = fractal;
		this.saveFormat = saveFormat;
		this.save = save;
		this.dimension = frame.getWidth();
		fractal.setDimension(dimension);
		bi = this.getImage();
	}
	public void setWelcome(int frameWidth) {
		// TODO Auto-generated method stub

	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(bi, 0, 0, this);
		if (save)
			saveImage(bi);
	}

	private void saveImage(BufferedImage bi2) {
		// TODO Auto-generated method stub
	}

	private BufferedImage getImage() {
		BufferedImage buff = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = buff.createGraphics();
		fractal.draw(g2D);
		return buff;
	}

	public BufferedImage getAbout() {
		BufferedImage buff = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = buff.createGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2D.setBackground(new Color(240, 240, 240));
		g2D.clearRect(0, 0, dimension, dimension);

		g2D.setColor(Color.blue);

		// java.awt.GraphicsEnvironment ge =
		// java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
		// Font[] fonts = ge.getAllFonts();
		// for(Font f: fonts){
		// System.out.println(f.getFontName());
		// }

		Rectangle rect = new Rectangle(0, 0, dimension, dimension);
		// Point2D start = new Point2D.Float(0, 0);
		// Point2D end = new Point2D.Float(dimension,dimension);
		// float[] dist = {0.0f, 0.2f, 0.8f, 1.0f};
		// Color[] colors = {Color.black, Color.DARK_GRAY, Color.white,
		// Color.black};
		// LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
		// colors);
		// g2D.setPaint(p);
		// g2D.fill(rect);

		g2D.clearRect(0, 0, dimension, dimension);

		Point2D center = new Point2D.Float(600, 600);
		float radius = 25;
		Point2D focus = new Point2D.Float(40, 40);
		float[] distR = { 0.0f, 0.2f, 0.8f, 1.0f };
		Color[] colorsR = { Color.green, Color.DARK_GRAY, Color.blue,
				Color.DARK_GRAY };
		RadialGradientPaint rgp = new RadialGradientPaint(center, radius,
				focus, distR, colorsR, CycleMethod.REPEAT);
		g2D.setPaint(rgp);
		g2D.fill(rect);

		Font h1 = new Font("Arial", Font.BOLD, 36);
		Font h2 = new Font("Verdana", Font.PLAIN, 18);
		Font h3 = new Font("Arial", Font.BOLD, 14);

		g2D.setColor(Color.white);
		g2D.setFont(h1);
		String title = "Fractal Explorer";
		FontMetrics fm = g2D.getFontMetrics();
		int strWidth = fm.stringWidth(title);
		g2D.drawString(title, (dimension - strWidth) / 2, (dimension / 2) - 100);

		g2D.setColor(Color.white);
		g2D.setFont(h2);
		String yearCourse = "2012-2013 RSGC ACES";
		g2D.drawString(yearCourse, (dimension - strWidth) / 2 + 30, 160);

		String name = "Patrick Houlding";
		g2D.drawString(name, (dimension - strWidth) / 2 + 60, 230);

		g2D.setFont(h3);
		String line1 = "Plot scripts have a '.exp' extension";
		String line2 = "Select File | Open to display a plot";
		String line3 = "Displays still or animated plots";
		g2D.drawString(line1, (dimension / 2) - 110, 400);
		g2D.drawString(line2, (dimension / 2) - 110, 420);
		g2D.drawString(line3, (dimension / 2) - 105, 440);

		return buff;
	}
	
	private void animate(){
		/*
		 * Points to use:
		 * 0.01358457	0.65562668 (Camel Valley)
		 * -0.47305281	0.53933875 (random swirls)
		 * -1.97717796	0.000 or 0.00009277	(Needle point - Requires very small delta number for nice patterns)
		 */
		
			frame.createBufferStrategy(2);
			BufferStrategy strategy = frame.getBufferStrategy();
			double delta = Fractal.getDelta();
			while (delta>1E-16) {
				bi = getImage();
				Graphics g = strategy.getDrawGraphics();
				paintComponent(g);
				strategy.show();
				g.dispose();
				//Change the delta for a different zoom ratio (The higher, the slower)
				delta *= 0.01;
				fractal.setDelta(delta);
			}

		}
	}