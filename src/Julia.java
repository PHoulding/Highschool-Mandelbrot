import java.awt.Color;
import java.awt.Graphics;

/**
 * This is the Final Examination for the ICS3U class. Inside, we are to create
 * the Julia set for our assigned points. My point was c=0.285+0.01i
 * 
 * @author Patrick Houlding
 * 
 */
public class Julia extends Fractal {

	private ComplexNumber c;
	private double xC;
	private double yC;

	/**
	 * The Julia constructor is basically the same as the Mandelbrot
	 * constructor.
	 * 
	 * @param xC
	 *            The x point in the center of the viewed screen
	 * @param yC
	 *            The y point in the center of the viewed screen
	 * @param delta
	 *            The delta that the screen will form around
	 * @param bailout
	 *            The amount of iterations you want to perform
	 * @param palette
	 *            The palette to be used
	 * @param fileName
	 *            The filename to save as.
	 */
	public Julia(String xC, String yC, String delta, String bailout,
			Palette palette, String fileName) {
		super(xC, yC, delta, bailout, palette, fileName);
	}

	/**
	 * The draw method draws the Julia set
	 */
	protected void draw(Graphics g) {
		for (int row = 0; row < dimension; row++)
			for (int col = 0; col < dimension; col++) {
				ComplexNumber c = new ComplexNumber(0.285, 0.01);
				int iterations = getEscapeTime(c);
				if (iterations > 255)
					iterations %= 255;
				if (isInSet()) {
					g.setColor(Color.BLACK);
				} else
					g.setColor(palette.getColor(iterations));
				g.drawLine(col, row, col, row);
			}
	}
/**
 * The setC method sets the c value to be used
 * @param col
 * 		The point on the y axis
 * @param row
 * 		The point on the x axis
 * @return
 * 		Returns the c value
 */
	public ComplexNumber setC(double col, double row) {
		return new ComplexNumber(this.mapColumnToReal(col),
				this.mapRowToImaginary(row));
	}
/**
 * The getEscapeTime method runs until it finds the bailout
 * @param c
 * 		The c value to check
 * @return
 * 		Returns the number of iterations
 */
	public int getEscapeTime(ComplexNumber c) {
		ComplexNumber z = new ComplexNumber();
		double x = 0;
		double y = 0;
		iterations = 0;
		while (x > -1.6 && x < 1.6) {
			x+=0.1;
			while (y > -1.6 && y < 1.6) {
				y+=0.1;
				z = new ComplexNumber(x, y);
				// System.out.println("here"+z);
				while (z.magnitude() < 2.0 && iterations < bailout) {
					z = z.square().add(c);
					// System.out.println("here");
					iterations++;
				}
				x = x + 0.1;
				y = y + 0.1;
			}
		}
		return iterations;
	}
/**
 * The isInSet method returns if the iterations are equal to the bailout
 * @return
 * 		IF TRUE: the point will be colored black
 * 		IF FALSE: the point will be colored according to the trigonometric palette
 */
	public boolean isInSet() {
		return iterations == bailout;
	}
}
