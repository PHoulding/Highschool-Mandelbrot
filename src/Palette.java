import java.awt.Color;
import java.util.Random;

public class Palette {

	private String type;
	private String name;
	private Color[] palette;
	private int size;

	public Palette(String type, String name, String size) {
		this.type = type;
		this.name = name;
		this.size = Integer.parseInt(size);
		palette = new Color[this.size];
		palette[0] = Color.BLACK;
		
		
		if ("Random".equalsIgnoreCase(type))
			this.makeRandomPalette();
		else if ("Trigonometric".equalsIgnoreCase(type))
			this.makeTrigonometricPalette();
		else if ("Grayscale".equalsIgnoreCase(type))
			this.makeGrayscalePalette();
		else if ("Mono".equalsIgnoreCase(type))
			this.makeMonochromePalette();
	}

	private void makeMonochromePalette() {
		palette[0] = Color.black;
		for (int i = 1; i < this.size; i++) {
			palette[i] = Color.white;
		}
	}

	private void makeGrayscalePalette(){
		palette[0] = Color.BLACK;
		for(int i = 0; i<this.size; i++)
			palette[i]= new Color (i,i,i);
		
	}

	private void makeRandomPalette() {
		palette[0] = Color.BLACK;
		Random random = new Random();
		for (int i = 0; i < this.size; i++)
			palette[i] = new Color(random.nextFloat(), random.nextFloat(),
					random.nextFloat());
	}

	private void makeTrigonometricPalette() {
		palette[0] = Color.BLACK;
		Random random = new Random();
		
		//Red Colour
		float rc = (float) (random.nextFloat()*2*Math.PI);
		//Green Colour
		float gc = (float) ((float)random.nextFloat()*(2*Math.PI));
		//Blue Colour
		float bc = (float) ((float)random.nextFloat()*(2*Math.PI));
		
		System.out.printf("rc: %f\n",rc);
		System.out.printf("bc: %f\n",bc);
		System.out.printf("gc: %f\n",gc);
		
		int periods = random.nextInt(3)+1;
		float br = (float) (2*Math.PI*periods/this.size);
		periods = random.nextInt(3)+1;
		float bg = (float) (2*Math.PI*periods/this.size);
		periods = random.nextInt(3)+1;
		float bb = (float) (2*Math.PI*periods/this.size);
		
		System.out.printf("br: %f\n",br);
		System.out.printf("bb: %f\n",bb);
		System.out.printf("bg: %f\n",bg);
		
		for (int i = 1; i < this.size; i++) {
			
	//		float red = (float) (0.5*Math.sin(br*i+rc)+0.5f);
	//		float blue = (float) (0.5*Math.sin(bb*i+bc)+0.5f);
	//		float green = (float) (0.5*Math.sin(bg*i*gc)+0.5f);
		//							Cool Palettes
		//	Nice Blue
//				float red = (float) (0.5*Math.sin(0.049087387*i+4.267437)+0.5f);
//				float blue = (float) (0.5*Math.sin(0.07363108*i+4.5276055)+0.5f);
//				float green = (float) (0.5*Math.sin(0.024543693*i*2.320294)+0.5f);
		//	Deep purple
//				float red = (float) (0.5*Math.sin(0.024543693*i+3.4565024)+0.5f);
//				float blue = (float) (0.5*Math.sin(0.049087387*i+4.977087)+0.5f);
//				float green = (float) (0.5*Math.sin(0.024543693*i*1.1538812)+0.5f);
		//	Kinda close to wikipedia palette
				float red = (float) (0.5*Math.sin(0.049087387*i+4.656115)+0.5f);
				float blue = (float) (0.5*Math.sin(0.049087387*i+5.3258123)+0.5f);
				float green = (float) (0.5*Math.sin(0.07363108*i*1.0750321)+0.5f);
			
			palette[i] = new Color(red,blue,green);
			palette[0] = Color.BLACK;
		}
	}

	public Color getColor(int i) {
		return palette[i];
	}
}
