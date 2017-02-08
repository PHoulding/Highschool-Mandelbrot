import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mandelbrot extends Fractal {

	public Mandelbrot(String xCenter, String yCenter, String delta,
			String bailout, Palette palette) {
		super(xCenter, yCenter, delta, bailout, palette, null);
	}

	public Mandelbrot(String xC, String yC, String delta, String bailout,
			Palette palette, String fileName) {
		super(xC, yC, delta, bailout, palette, fileName);
	}

	protected void draw(Graphics g) {
	//	System.out.println(dimension);
		for (int row = 0; row < dimension; row++)
			for (int col = 0; col < dimension; col++) {
				ComplexNumber c = this.setC(col, row);
			//	System.out.println(c);
				int iterations = getEscapeTime(c);
				if (iterations>255)
					iterations %= 255;
				if (isInSet()) {
					g.setColor(Color.BLACK);
				} else
					g.setColor(palette.getColor(iterations));
				g.drawLine(col, row, col, row);
			}
	}
	
	public ComplexNumber setC(int col, int row) {
		return new ComplexNumber(this.mapColumnToReal(col),
				this.mapRowToImaginary(row));
	}

	public int getEscapeTime(ComplexNumber c) {
		ComplexNumber z = new ComplexNumber();
		iterations=0;
		while (z.magnitude() < 2.0 && iterations < bailout) {
			z = z.square().add(c);
			iterations++;
		}
		return iterations;
	}

	public boolean isInSet() {
		return iterations == bailout;
	}
}
