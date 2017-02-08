import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Fractal {

	protected double xCenter, yCenter;
	protected static double delta;
	protected int bailout;
	protected Palette palette;
	protected String fileName;
	protected double xMax, yMax, xMin, yMin;
	protected int dimension;
	protected int iterations;
	private JFrame frame;
	private Mandelbrot mandelbrot;
	private ComplexNumber c;
	

	public Fractal(String xC, String yC, String delta, String bailout,
			Palette palette, String fileName) {
		this.xCenter = Double.parseDouble(xC);
		this.yCenter = Double.parseDouble(yC);
		this.delta = Double.parseDouble(delta);
		this.bailout = Integer.parseInt(bailout);
		this.palette = palette;
		this.fileName = fileName;
		double xCenter = Double.parseDouble(xC);
		double yCenter = Double.parseDouble(yC);
		setMinMax();
		
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	protected void draw(Graphics g){
	}

	protected double mapColumnToReal(double col) {
		double c = col;
		return ((double)c / dimension * (xMax - xMin) + xMin);
	}

	protected double mapRowToImaginary(double row) {
		return yMax -(double) row / dimension * (yMax - yMin);
	}

	protected int mapRealToColumn(double x) {
		return (int) ((int) ((x - xMin) * dimension) / (xMax - xMin));
	}

	protected int mapImaginaryToRow(double y) {
		return (int) (-((int) (y-yMin)*dimension)/(xMax-xMin));
	}

	public String getFilename() {
		return fileName.substring(0, fileName.length()-4);
	}
	public static double getDelta(){
		return delta;
	}
	public double setDelta(double newDelta){
		delta = newDelta;
		setMinMax();
		return delta;
	}
	public void setMinMax(){
		this.xMin = xCenter-this.delta;
		this.xMax = xCenter+this.delta;
		this.yMin = yCenter-this.delta;
		this.yMax = yCenter+this.delta;
	}

}
